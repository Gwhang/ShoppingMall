package com.gwh.sell.service.impl;

import com.gwh.sell.dataObject.ProductCategory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 类目实现类 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest extends TestCase {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void testFindOne() {
        ProductCategory productCategory=this.categoryService.findOne(1);
        Assert.assertNotEquals(new Integer(0),productCategory.getCategoryId());

    }
    @Test
    public void testFindAll() {
        List<ProductCategory> productCategoryList = this.categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }
    @Test
    public void testFindByCategoryTypeIn() {
        Integer[] types={1,2};
        List<Integer> typeList=Arrays.asList(types);
        List<ProductCategory> productCategoryList = this.categoryService.findByCategoryTypeIn(typeList);
        Assert.assertNotEquals(0,productCategoryList.size());
    }
    @Test
    public void testSave() {
        ProductCategory productCategory= new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(1);
        ProductCategory productCategory1 = this.categoryService.save(productCategory);
        Assert.assertNotNull(productCategory1);
    }
}