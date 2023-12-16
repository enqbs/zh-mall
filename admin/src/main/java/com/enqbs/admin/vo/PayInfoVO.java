package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PayInfoVO implements Serializable {

    private Long id;

    private Long orderNo;

    private Integer userId;

    private String nickName;

    private String photo;

    private BigDecimal payAmount;

    private Integer status;

    private Integer deleteStatus;

    private Date createTime;

    private Date paymentTime;

    private Date updateTime;

    private PayPlatformVO payPlatform;

    @Override
    public String toString() {
        return "PayInfoVO{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", payAmount=" + payAmount +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", updateTime=" + updateTime +
                ", payPlatform=" + payPlatform +
                '}';
    }

}
