package com.gwh.sell.service;

import com.gwh.sell.dataObject.ProductCategory;

import java.util.List;

/**
 * 类目
 */
public interface CategoryService {

    /**
     * 查询一条数据
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有类目数据
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类型集合查询类目数据
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 更新 保存
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);



}
