package com.zhong.ding.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:12
 */
@Slf4j
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    private final List<T> rows = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        rows.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("read {} rows %n", rows.size());
    }

    public List<T> getRows() {
        return rows;
    }
}