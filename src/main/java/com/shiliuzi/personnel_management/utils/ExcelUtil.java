package com.shiliuzi.personnel_management.utils;

import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @description: 导出excel工具类
 * @author: chun
 **/

public class ExcelUtil {
    /**
     * 根据模板导出数据
     *
     * @param fileName
     * @param sourcePath resource/template文件夹下路径
     * @param beanParams
     * @param response
     */
    public static void downLoadExcel(String fileName, String sourcePath, Map<String, Object> beanParams, HttpServletResponse response) {
        try {
            OutputStream os = getOutputStream(fileName, response);
            //读取模板
            InputStream is = ExcelUtil.class.getClassLoader().getResourceAsStream("excelTemplate/" + sourcePath);
            XLSTransformer transformer = new XLSTransformer();
            //向模板中写入内容
            Workbook workbook = transformer.transformXLS(is, beanParams);
            //写入成功后转化为输出流
            workbook.write(os);
        } catch (Exception e) {
            throw new AppException(AppExceptionCodeMsg.EXCEL_DOWNLOAD_ERROR);
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     *
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName,
                                                HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new AppException(AppExceptionCodeMsg.EXCEL_IMPORT_FORMAT_ERROR);
        }
    }

}
