package com.trd.oecms.utils;

import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件工具
 * @author tanruidong
 * @date 2020-04-15 16:16
 */
@Component
public class FileUtils {

    private static DocumentConverter documentConverter;

    @Autowired
    public void setDocumentConverter(DocumentConverter documentConverter) {
        FileUtils.documentConverter = documentConverter;
    }

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
     * 保存上传的文件
     * @param file 文件
     * @param savePath 保存路径
     * @return 存储到数据库中的文件名
     * @throws Exception
     */
    public static String saveFile(MultipartFile file, String savePath) throws Exception{
        System.out.println(documentConverter);
        String fileName = FileUtils.makeNonRepeatName();
        String pdfName = fileName+".pdf";
        String newFileName = fileName+FileUtils.getFileSuffix(file.getOriginalFilename());
        File wordFile = new File(savePath + newFileName);
        // 保存word文件
        file.transferTo(wordFile);
        // 将word文件转为pdf保存
        documentConverter.convert(wordFile).to(new File(savePath+pdfName)).execute();
        return pdfName;
    }
}
