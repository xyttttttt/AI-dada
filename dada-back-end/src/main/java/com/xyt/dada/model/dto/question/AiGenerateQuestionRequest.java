package com.xyt.dada.model.dto.question;


import lombok.Data;

import java.io.Serializable;

/**
 * AI生成题目请求
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    private Long appId;

    /**
     * 题目数
     */
    private int questionNumber = 10;

    /**
     * 选项数
     */
    private int optionNumber = 4;


    private static final long serialVersionUID = 1L;
}
