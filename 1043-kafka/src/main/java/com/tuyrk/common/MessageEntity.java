package com.tuyrk.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 消息实体
 *
 * @author tuyrk
 */
@Data
@EqualsAndHashCode
public class MessageEntity implements Serializable {
    private String title;
    private String body;
}
