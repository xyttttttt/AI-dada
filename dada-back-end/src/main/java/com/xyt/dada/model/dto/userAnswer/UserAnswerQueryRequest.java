package com.xyt.dada.model.dto.userAnswer;


import com.xyt.dada.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询用户答案请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAnswerQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 应用类型(0-得分类，1-测评类)
     */
    private Integer appType;

    /**
     * 评分策略(0-自定义，1-AI)
     */
    private Integer scoringStrategy;

    /**
     * 用户答案(JSON数组)，如[A,B,C,D]
     */
    private String choices;

    /**
     * 评分结果id
     */
    private Long resultId;

    /**
     * 结果名称，如物流师
     */
    private String resultName;

    /**
     * 结果描述
     */
    private String resultDesc;

    /**
     * 结果图片
     */
    private String resultPicture;

    /**
     * 得分
     */
    private Integer resultScore;

    /**
     * 用户 id
     */
    private Long userId;


    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}