package com.gwh.sell.controller;

import com.gwh.sell.dao.ProductInfoDao;
import com.gwh.sell.dataObject.ProductCategory;
import com.gwh.sell.dataObject.ProductInfo;
import com.gwh.sell.service.CategoryService;
import com.gwh.sell.service.ProductInfoService;
import com.gwh.sell.utils.ResultVoUtil;
import com.gwh.sell.vo.ProductInfoVo;
import com.gwh.sell.vo.ProductVo;
import com.gwh.sell.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    Logger logger= LoggerFactory.getLogger(BuyerProductController.class);

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     * Cacheable 第一次查询到结果以后 会保存到redis中 第二次查询的时候 将不走次方法
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVo list(){
        // 1.查询所有上架商品
           List<ProductInfo> productInfoList = this.productInfoService.findUpAll();
        // 2.查询所有类目
         List<Integer> categoryTypes=productInfoList.stream().distinct().map(p -> p.getCategoryType()).collect(Collectors.toList());
         List<ProductCategory> productCategoryList=this.categoryService.findByCategoryTypeIn(categoryTypes);
        // 3.拼接返回消息
        List<ProductVo> data=new ArrayList<ProductVo>();
        productCategoryList.stream().forEach(p -> {
            ProductVo productVo=new ProductVo();
            productVo.setCategoryName(p.getCategoryName());
            productVo.setCategoryType(p.getCategoryType());
            List<ProductInfoVo> productInfoVoList = new ArrayList<ProductInfoVo>();
            productInfoList.stream().forEach( f ->{
                if (p.getCategoryType().equals(f.getCategoryType())){
                    ProductInfoVo productVo1=new ProductInfoVo();
                    BeanUtils.copyProperties(f,productVo1);
                    productInfoVoList.add(productVo1);
                }
            });
            productVo.setData(productInfoVoList);
            data.add(productVo);
        });
        return ResultVoUtil.success(data);
    }


}
