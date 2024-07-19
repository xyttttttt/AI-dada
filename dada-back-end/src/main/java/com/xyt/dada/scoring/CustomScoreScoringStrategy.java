package com.xyt.dada.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xyt.dada.model.dto.question.QuestionContentDTO;
import com.xyt.dada.model.entity.App;
import com.xyt.dada.model.entity.Question;
import com.xyt.dada.model.entity.ScoringResult;
import com.xyt.dada.model.entity.UserAnswer;
import com.xyt.dada.model.enums.AppScoringStrategyEnum;
import com.xyt.dada.model.vo.QuestionVO;
import com.xyt.dada.service.QuestionService;
import com.xyt.dada.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 自定义评分策略  --得分类应用
 */
@ScoringStrategyConfig(appType = 0, scoringStrategy = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

    @Override
    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        //1. 根据题目id查询到题目和题目结果信息
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, app.getId())
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, app.getId())
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );
        //2. 统计用户总得分
        int totalScore = 0;
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContentDTOList = questionVO.getQuestionContent();
        //遍历题目
        for (QuestionContentDTO questionContentDTO : questionContentDTOList){
            //遍历题目选项
            for (String choiceItem : choice){
                //遍历题目中的选项
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()){
                    if (option.getKey().equals(choiceItem)){
                        //如果用户选择的答案和题目选项相同，则将属性个数+1
                        int score = Optional.of(option.getScore()).orElse(0);
                        totalScore += score;
                    }
                }
            }
        }
        //3. 遍历得分结果，找到第一个用户分数大于得分范围的结果，作为最终结果
        ScoringResult maxScoringResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList){
            if (totalScore >= scoringResult.getResultScoreRange()){
                maxScoringResult = scoringResult;
                break;
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
        userAnswer.setResultScore(totalScore);
        return userAnswer;
    }
}
