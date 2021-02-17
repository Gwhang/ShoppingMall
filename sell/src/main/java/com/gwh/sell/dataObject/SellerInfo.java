package com.gwh.sell.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户信息表
 */
@Entity
@DynamicUpdate
@Data
public class SellerInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;


}
