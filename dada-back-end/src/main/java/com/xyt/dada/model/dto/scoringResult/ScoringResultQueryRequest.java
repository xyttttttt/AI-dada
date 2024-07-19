package com.xyt.dada.model.dto.scoringResult;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xyt.dada.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询得分结果请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoringResultQueryRequest extends PageRequest implements Serializable {

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
    private String resultProp;

    /**
     * 结果得分范围,如80分,表示80及以上命中这个结果
     */
    private Integer resultScoreRange;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
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