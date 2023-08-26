package com.tjetc.domain;

import lombok.Data;

/**
 * @belongProject: 1204-dubbo
 * @belongPackage: com.tjetc.utils
 * @author: xujirong
 * @dscription: 商品表
 * @date: 2023-08-25 16:46
 * @version: 1.0
 */
@Data
public class Product {

    /**商品名称*/
    private int id;
    /**商品名称*/
    private String name;
    /**商品名称*/
    private String ChName;
    /**购买商品数量*/
    private int buyNum;
    /**商品单价,元/斤*/
    private String price;

}
