package com.tuyrk.common;

import lombok.Data;

/**
 * @author tuyrk
 */
@Data
public class Response {
    private int code;
    private String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
