package com.xyt.dada.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author 薛宇彤
 * @from 1604899092
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}