package com.gwh.sell.service.impl;

import com.gwh.sell.dao.ProductInfoDao;
import com.gwh.sell.dataObject.ProductInfo;
import com.gwh.sell.dto.CartDTO;
import com.gwh.sell.enums.ProductStatusEnum;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.service.ProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 商品详情业务层
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    Logger logger= LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    @Autowired
    private ProductInfoDao productInfoDao;

    /**
     * 查询单条商品信息
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        logger.info("[商品明细查询] 传输的参数为:{}",productId);
        return this.productInfoDao.getOne(productId);
    }

    /**
     * 查询上架商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return this.productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 分页查询所有商品
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return this.productInfoDao.findAll(pageable);
    }

    /**
     * 保存 更新
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return this.productInfoDao.save(productInfo);
    }

    /**
     * 加库存
     * @param cartDTOList
     */
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.stream().forEach(p -> {
            ProductInfo productInfo=this.findOne(p.getProductId());
            if (productInfo.equals(null)){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + p.getProductQuantity();
            productInfo.setProductStock(result);
            this.productInfoDao.save(productInfo);
        });

    }

    /**
     * 减库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.stream().forEach(p -> {
            ProductInfo productInfo = this.findOne(p.getProductId());
            if (productInfo.equals(null)){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
           Integer result = productInfo.getProductStock() - p.getProductQuantity();
            if (result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            this.productInfoDao.save(productInfo);
        });
    }

    /**
     * 上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        //查询商品信息
        Optional<ProductInfo> productInfo = this.productInfoDao.findById(productId);
        if(!productInfo.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.get().getProductStatus()== 0){
            throw  new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.get().setProductStatus(ProductStatusEnum.UP.getCode());
        this.productInfoDao.save(productInfo.get());

        return productInfo.get();
    }

    /**
     * 下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        //查询商品信息
        Optional<ProductInfo> productInfo = this.productInfoDao.findById(productId);
        if(!productInfo.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.get().getProductStatus()== 1){
            throw  new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.get().setProductStatus(ProductStatusEnum.DOWN.getCode());
        this.productInfoDao.save(productInfo.get());
        return productInfo.get();
    }
}
