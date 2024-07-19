package com.xyt.dada.scoring;

import com.xyt.dada.common.ErrorCode;
import com.xyt.dada.exception.BusinessException;
import com.xyt.dada.model.entity.App;
import com.xyt.dada.model.entity.UserAnswer;
import com.xyt.dada.model.enums.AppScoringStrategyEnum;
import com.xyt.dada.model.enums.AppTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Deprecated
public class ScoringStrategyContent {


    @Autowired
    private CustomScoreScoringStrategy customScoreScoringStrategy;

    @Autowired
    private CustomTestScoringStrategy customTestScoringStrategy;

    /**
     * 获取评分策略
     */
    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        AppTypeEnum appTypeEnum = AppTypeEnum.getEnumByValue(String.valueOf(app.getAppType()));
        AppScoringStrategyEnum scoringStrategyEnum = AppScoringStrategyEnum.getEnumByValue(String.valueOf(app.getScoringStrategy()));
        if (appTypeEnum == null || scoringStrategyEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }
        // 根据应用类型和评分策略获取对应的评分策略
        switch (appTypeEnum) {
            case SCORE:
                switch (scoringStrategyEnum) {
                    case CUSTOM:
                        return customScoreScoringStrategy.doScore(choice, app);
                    case AI:
                        break;
                }
                break;
            case TEST:
                switch (scoringStrategyEnum) {
                    case CUSTOM:
                        return customTestScoringStrategy.doScore(choice, app);
                    case AI:
                        break;
                }
                break;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
    }
}
