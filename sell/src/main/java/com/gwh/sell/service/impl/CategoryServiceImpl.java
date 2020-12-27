package com.gwh.sell.service.impl;

import com.gwh.sell.dao.ProductCategoryDao;
import com.gwh.sell.dataObject.ProductCategory;
import com.gwh.sell.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目业务层
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ProductCategoryDao productCategoryDao;


    /**
     * 查询单条数据
     * @param categoryId
     * @return
     */
    @Override
    public ProductCategory findOne(Integer categoryId) {
        logger.info("[类目查询] 查询单条数据传输的参数为：{}",categoryId);
        return this.productCategoryDao.findById(categoryId).get();
    }

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<ProductCategory> findAll() {
        return this.productCategoryDao.findAll();
    }

    /**
     * 根据类目编号查询
     * @param categoryTypeList
     * @return
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        logger.info("[类目查询] 根据类目编号查询数据 传输的参数为:{}",categoryTypeList);
        return this.productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        logger.info("[类目保存更新] 传输的参数为{}",productCategory.toString());
       return  this.productCategoryDao.save(productCategory);
    }
}
