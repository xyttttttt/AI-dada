package com.xyt.dada.model.dto.userAnswer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建用户答案请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class UserAnswerAddRequest implements Serializable {


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