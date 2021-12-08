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


    public static <T> CommonResult<T> success(){
        return new CommonResult<>("success",null,null);
    }
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>("success",null,data);
    }
    public static <T> CommonResult<T> success(String message,T data){
        return new CommonResult<>("success",message,data);
    }

    public static <T> CommonResult<T> failure(){
        return new CommonResult<>("failure",null,null);
    }
    public static <T> CommonResult<T> failure(String message){
        return new CommonResult<>("failure",message,null);
    }
    public static <T> CommonResult<T> failure(String message,T data){
        return new CommonResult<>("failure",message,data);
    }
}
