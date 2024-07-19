package com.xyt.dada.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目答案封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 用户答案
     */
    private String userAnswer;
}
