package com.gwh.sell.dao;

import com.gwh.sell.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类目 dao
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    // 根据类目编号查询数据
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
