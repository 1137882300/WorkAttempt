package com.zhong.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: juzi
 * @date: 2023/7/31
 * @desc: 比较原始的方式，建议用easyExcel
 */
public class ExcelParser {

    public static <T> List<T> parseExcel(String filePath, Class<T> clazz) {
        List<T> objects = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                objects.addAll(parseSheet(sheet, clazz));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }

    private static <T> List<T> parseSheet(Sheet sheet, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> objects = new ArrayList<>();

        Row headerRow = sheet.getRow(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            T object = clazz.newInstance();

            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                Field field = getFieldByHeader(clazz, headerRow.getCell(j).getStringCellValue());
                if (field != null && cell != null) {
                    field.setAccessible(true);
                    field.set(object, getCellValue(cell));
                }
            }

            objects.add(object);
        }

        return objects;
    }

    private static Field getFieldByHeader(Class<?> clazz, String header) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class) && field.getAnnotation(ExcelColumn.class).value().equalsIgnoreCase(header)) {
                return field;
            }
        }
        return null;
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }

    public void doParse() {
        List<ExcelEntity> objects = ExcelParser.parseExcel("F:\\工作记录\\content2.xlsx", ExcelEntity.class);
        System.out.println(objects.get(5));

    }

    public static void main(String[] args) {
        new ExcelParser().doParse();
    }
}