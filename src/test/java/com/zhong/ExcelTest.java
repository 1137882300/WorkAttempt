package com.zhong;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.excel.Entity;
import com.zhong.excel.ExcelUtil;
import com.zhong.excel.WriteEntity;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/19 16:22
 */
public class ExcelTest {

    @Test
    public void sql(){

        StringBuffer buffer = new StringBuffer();

        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Documents\\删除数据\\brand.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,1);

            List<String> collect = list.stream().map(Entity::getColumn1).filter(Objects::nonNull).collect(Collectors.toList());
            collect.forEach(c -> {
                buffer.append("\n"+ "update t_brand set is_deleted = 1 where brand_id = "+ c + "; ");
            });

            System.out.println(buffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sql2(){
        StringBuffer buffer = new StringBuffer();

        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Documents\\删除数据\\brandLanguage.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,1);

            List<String> collect = list.stream().map(Entity::getColumn1).filter(Objects::nonNull).collect(Collectors.toList());
            collect.forEach(c -> {
                buffer.append("\n"+ "update t_brand_language set is_deleted = 1 where brand_id = "+ c + "; ");
            });

            System.out.println(buffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void SE(){
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Downloads\\lazada_sg.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,2);

            List<String> collect = list.stream().map(Entity::getColumn5).filter(Objects::nonNull).collect(Collectors.toList());
            System.out.println(collect.size());
            System.out.println(collect);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void r(){

    String s = Locale.US.toString();
    System.out.println(s);//en_US
}





    @Test
    public void MY(){
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Downloads\\lazada_my.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,1);

            Long areaId = 1L;
            Long areaLanId = 1L;
            //马来西亚国家的 id
            Long countryMyId = 11L;

            Map<String, Long> parentMap = new HashMap<>();

            for (Entity entity : list) {
                String column1 = entity.getColumn1();
                String column2 = entity.getColumn2();
                if (column1 == null){
                    continue;
                }

                parentMap.put(column1, areaId);

                areaId++;
                areaLanId++;
            }

            System.out.println("areaId: "+ areaId);
            System.out.println("areaLanId: "+ areaLanId);


            System.out.println("parentMap : "+ parentMap);
            System.out.println("parentMap.size = "+ parentMap.size());

            List<String> collect = list.stream().map(Entity::getColumn1).filter(Objects::nonNull).collect(Collectors.toList());
            System.out.println("collect.size() = "+ collect.size());


            Map<String, List<Entity>> listMap = list.stream().collect(Collectors.groupingBy(Entity::getColumn3));
            for (String s : collect) {
                if (listMap.containsKey(s)){
                    List<Entity> entityList = listMap.get(s);
                    System.out.println("entityList = " + entityList);
                    for (Entity entity : entityList) {
                        String column4 = entity.getColumn4();
                        Long parentId = parentMap.get(s);

                        areaId++;
                        areaLanId++;
                    }
                }
            }

            System.out.println("areaId: "+ areaId);
            System.out.println("areaLanId: "+ areaLanId);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入excel
     */
    @Test
    public void writeExcel(){
        List<WriteEntity> list = Lists.newArrayList();
        WriteEntity writeEntity = new WriteEntity();
        writeEntity.setName("hi");
        writeEntity.setCode("888");
        writeEntity.setMoney(8888888888888L);
        list.add(writeEntity);
        WriteEntity writeEntity2 = new WriteEntity();
        writeEntity2.setName("hello");
        writeEntity2.setCode("666");
        writeEntity2.setMoney(999999999999L);
        list.add(writeEntity2);

        File file = new File("C:\\Users\\EDZ\\Documents\\测试目录\\writeInto.xlsx");

        //按行写入，根据实体类
        ExcelUtil.writeExcel( file, list);


    }




}
