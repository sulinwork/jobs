package com.sulin.jobweb.bean;

import lombok.Data;

/**
 * @author sulin
 * @create 2019-08-28 14:32
 */
@Data
public class Api<T> {
    private Integer code;
    private String msg;
    private T data;

    /**
     * success
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Api<T> ok(T data) {
        Api<T> tApi = new Api<>();
        tApi.setCode(200);
        tApi.setMsg("success");
        tApi.setData(data);
        return tApi;
    }

    public static Api error(Integer code, String msg) {
        Api api = new Api();
        api.setCode(code);
        api.setMsg(msg);
        return api;
    }

}
