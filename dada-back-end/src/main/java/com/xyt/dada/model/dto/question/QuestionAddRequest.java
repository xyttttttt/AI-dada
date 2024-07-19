package com.xyt.dada.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建题目请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class QuestionAddRequest implements Serializable {


    /**
     * 题目内容(json格式)
     */
    private List<QuestionContentDTO> questionContent;

    /**
     * 应用 id
     */
    private Long appId;


    private static final long serialVersionUID = 1L;
}