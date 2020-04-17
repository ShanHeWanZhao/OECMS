package com.trd.oecms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件工具
 * @author tanruidong
 * @date 2020-04-15 16:16
 */
public class FileUtils {

    /**
     *  使用 UUID 制造一个包含当前时间的不重复文件名<p/>
     *  例如：65126d474fe846c5abb05af533d3c272_2020-04-16
     * @return
     */
    public static String makeNonRepeatName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        return UUID.randomUUID().toString().replace("-", "")+"_"+date;
    }

    /**
     * 获取文件后缀名
     * @param fileName 完整的文件名
     * @return
     * @thorw StringIndexOutOfBoundsException 不存在后缀名
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建一个与原文扩展名相同，不重复的文件名
     * @param fileName
     * @return
     */
    public static String makeNonRepeatFullName(String fileName){
        return makeNonRepeatName()+getFileSuffix(fileName);
    }
}
