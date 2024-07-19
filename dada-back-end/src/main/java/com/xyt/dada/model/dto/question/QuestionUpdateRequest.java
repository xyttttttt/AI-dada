package com.xyt.dada.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新题目请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容(json格式)
     */
    private List<QuestionContentDTO> questionContent;

    private static final long serialVersionUID = 1L;
}