package com.zhong.ding.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.junit.Assert;

import java.io.*;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:13
 */
@Slf4j
public final class ExcelUtil {
    /**
     * 从Excel中读取文件，读取的文件是一个DTO类，该类必须继承BaseRowModel
     * 具体实例参考 ： MemberMarketDto.java
     * 参考：https://github.com/alibaba/easyexcel
     * 字符流必须支持标记，FileInputStream 不支持标记，可以使用BufferedInputStream 代替
     * BufferedInputStream bis = new BufferedInputStream(new FileInputStream(...));
     */
    public static <T extends BaseRowModel> List<T> readExcel(final InputStream inputStream, final Class<? extends BaseRowModel> clazz, Integer sheetNo) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        ExcelListener<T> listener = new ExcelListener<>();
        // 这里因为EasyExcel-1.1.1版本的bug，所以需要选用下面这个标记已经过期的版本
        ExcelReader reader = new ExcelReader(inputStream, valueOf(inputStream), null, listener);
        reader.read(new Sheet(sheetNo, 1, clazz));

        return listener.getRows();
    }

    public static <T extends BaseRowModel> List<T> readExcelByHeadLine(final InputStream inputStream, final Class<? extends BaseRowModel> clazz, Integer sheetNo, int headLine) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        ExcelListener<T> listener = new ExcelListener<>();
        // 这里因为EasyExcel-1.1.1版本的bug，所以需要选用下面这个标记已经过期的版本
        ExcelReader reader = new ExcelReader(inputStream, valueOf(inputStream), null, listener);
        reader.read(new Sheet(sheetNo, headLine, clazz));

        return listener.getRows();
    }


    public static void writeExcel(final File file, List<? extends BaseRowModel> list) {
        Assert.assertNotSame("list 不能为空", 0, list.size());
        try (OutputStream out = new FileOutputStream(file)) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet,  有模型映射关系
            Class<? extends BaseRowModel> t = list.get(0).getClass();
            Sheet sheet = new Sheet(1, 0, t);
            writer.write(list, sheet);
            writer.finish();
        } catch (IOException e) {
            log.warn("fail to write to excel file: file[{}]", file.getName(), e);
        }
    }


    /**
     * 根据输入流，判断为xls还是xlsx，该方法原本存在于easyexcel 1.1.0 的ExcelTypeEnum中。
     */
    public static ExcelTypeEnum valueOf(InputStream inputStream) {
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (FileMagic.OLE2.equals(fileMagic)) {
                return ExcelTypeEnum.XLS;
            }
            if (FileMagic.OOXML.equals(fileMagic)) {
                return ExcelTypeEnum.XLSX;
            }
            throw new IllegalArgumentException("excelTypeEnum can not null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}