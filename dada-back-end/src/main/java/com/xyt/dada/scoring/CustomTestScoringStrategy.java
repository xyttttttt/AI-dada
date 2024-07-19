package com.xyt.dada.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xyt.dada.model.dto.question.QuestionContentDTO;
import com.xyt.dada.model.entity.App;
import com.xyt.dada.model.entity.Question;
import com.xyt.dada.model.entity.ScoringResult;
import com.xyt.dada.model.entity.UserAnswer;
import com.xyt.dada.model.vo.QuestionVO;
import com.xyt.dada.service.AppService;
import com.xyt.dada.service.QuestionService;
import com.xyt.dada.service.ScoringResultService;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

    @Override
    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        //1. 根据id查询到题目和题目结果信息
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, app.getId())
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, app.getId())
        );
        //2. 统计用户每个选择对应的属性个数，如I=10，E=5，N=3，S=2，T=1，F=1，J=1，P=1
        Map<String,Integer> optionCount = new HashMap<>();
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContentDTOList = questionVO.getQuestionContent();
        //遍历题目列表
        for (QuestionContentDTO questionContentDTO : questionContentDTOList){
            //遍历题目选项
            for (String choiceItem : choice){
                //遍历题目中的选项
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()){
                    if (option.getKey().equals(choiceItem)){
                        //如果用户选择的答案和题目选项相同，则将属性个数+1
                        String result = option.getResult();
                        if (optionCount.containsKey(result)){
                            optionCount.put(result,optionCount.get(result)+1);
                        }else {
                            optionCount.put(result,1);
                        }
                    }
                }
            }
        }
        //3. 遍历每种结果，计算出哪个结果的得分更高
        int maxScore = 0;
        ScoringResult maxScoringResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList){
            //[I,J,S,T]
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(),String.class);
            int score = resultProp.stream().mapToInt(prop -> optionCount.getOrDefault(prop, 0)).sum();
            //如果得分更高，则更新最大得分和最大得分结果
            if (score > maxScore){
                maxScore = score;
                maxScoringResult = scoringResult;
            }
        }
        //4. 构造返回值,填充答案对象的属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        userAnswer.setAppId(app.getId());
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choice));
        userAnswer.setResultId(maxScoringResult.getId());
        return userAnswer;
    }
}
