package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MessageQueueLog implements Serializable {

    private Long messageId;

    private String exchange;

    private String routingKey;

    private String queue;

    private String content;

    private Integer delay;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "MessageQueueLog{" +
                "messageId=" + messageId +
                ", exchange='" + exchange + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", queue='" + queue + '\'' +
                ", content='" + content + '\'' +
                ", delay=" + delay +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
