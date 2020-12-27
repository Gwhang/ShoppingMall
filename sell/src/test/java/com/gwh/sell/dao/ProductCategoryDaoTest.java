package com.gwh.sell.dao;

import com.gwh.sell.dataObject.ProductCategory;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 类目测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest extends TestCase {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 查询方法
     */
    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryDao.findById(1).get();
        System.out.println(productCategory);
    }

    /**
     * 新增
     */
    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("秒杀商品");
        productCategory.setCategoryType(3);
        this.productCategoryDao.save(productCategory);
    }

    /**
     * 更新
     */
    @Test
    public void updateTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("秒杀商品111");
        productCategory.setCategoryType(3);
       ProductCategory result = this.productCategoryDao.save(productCategory);
        Assert.assertNotNull(result);// 断言 结果不为null

    }

    /**
     * 根据类目编号查询
     */
    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> all = this.productCategoryDao.findAll();
        List<Integer> list = all.stream().map(ProductCategory::getCategoryType).collect(Collectors.toList());

        List<ProductCategory> creategoryTypes = this.productCategoryDao.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0,creategoryTypes.size());

    }



}