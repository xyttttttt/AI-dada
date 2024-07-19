package com.xyt.dada.scoring;

import com.xyt.dada.common.ErrorCode;
import com.xyt.dada.exception.BusinessException;
import com.xyt.dada.model.entity.App;
import com.xyt.dada.model.entity.UserAnswer;
import com.xyt.dada.model.enums.AppScoringStrategyEnum;
import com.xyt.dada.model.enums.AppTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评分策略执行器
 */
@Service
public class ScoringStrategyExecutor {


    @Resource
    private List<ScoringStrategy> scoringStrategyList;


    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        AppTypeEnum appTypeEnum = AppTypeEnum.getEnumByValue(String.valueOf(app.getAppType()));
        AppScoringStrategyEnum scoringStrategyEnum = AppScoringStrategyEnum.getEnumByValue(String.valueOf(app.getScoringStrategy()));
        if (appTypeEnum == null || scoringStrategyEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }

        for (ScoringStrategy scoringStrategy : scoringStrategyList){
            if (scoringStrategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)) {
                ScoringStrategyConfig strategyConfig = scoringStrategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                if (strategyConfig.appType() == app.getAppType() && strategyConfig.scoringStrategy() == app.getScoringStrategy()){
                    return scoringStrategy.doScore(choice, app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "未找到匹配的策略");
    }
}
