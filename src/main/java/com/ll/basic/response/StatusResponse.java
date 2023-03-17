package com.ll.basic.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatusResponse {

    private String resultCode;
    private String msg;

    public StatusResponse(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
