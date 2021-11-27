package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    //success / failure
    private String code;
    private String message;
    private T data;

    public CommonResult(String code, String message){
        this(code,message,null);
    }
    public CommonResult(String code, T data){
        this(code,"",data);
    }
}
