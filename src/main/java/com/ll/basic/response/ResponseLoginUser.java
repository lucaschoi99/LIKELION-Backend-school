package com.ll.basic.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseLoginUser {

    private String resultCode;
    private String msg;

    public ResponseLoginUser(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
