package com.trd.oecms.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;

/**
 * controller的json返回结果
 * @author tanruidong
 * @date 2020-04-01 14:49
 */
@Data
@Accessors(chain = true)
public class JsonResult {
    /**
     * 是否成功状态码：
     *      0 —> 成功
     *      1 —> 失败
     */
    private int code;
    private String msg;
    private long count;
    private Object data;

    public static JsonResult error(String msg){
        JsonResult result = new JsonResult();
        result.code = 1;
        result.msg = msg;
        result.data = Collections.emptyMap();
        return result;
    }

    public static JsonResult ok(){
        JsonResult result = new JsonResult();
        result.code = 0;
        result.msg = "success";
        result.data = Collections.emptyMap();
        return result;
    }

    public static JsonResult ok(Object data){
        JsonResult result = new JsonResult();
        result.code = 0;
        result.msg = "success";
        result.data = data;
        return result;
    }

}
