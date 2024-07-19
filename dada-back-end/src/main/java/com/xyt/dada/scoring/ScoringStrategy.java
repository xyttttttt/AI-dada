package com.xyt.dada.scoring;

import com.xyt.dada.model.entity.App;
import com.xyt.dada.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略
 */
public interface ScoringStrategy {

    /**
     * 评分
     * @param choice
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choice, App app) throws Exception;
}
