package com.xyt.dada.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新用户答案请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class UserAnswerUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用 id
     */
    private Long appId;


    /**
     * 用户答案(JSON数组)，如[A,B,C,D]
     */
    private List<String> choices;

    private static final long serialVersionUID = 1L;
}