package com.xyt.dada.model.dto.scoringResult;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 更新得分结果请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class ScoringResultUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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
     * 结果属性集合JSON，如[I,S,T,J]
     */
    private List<String> resultProp;

    /**
     * 结果得分范围,如80分,表示80及以上命中这个结果
     */
    private Integer resultScoreRange;

    private static final long serialVersionUID = 1L;
}