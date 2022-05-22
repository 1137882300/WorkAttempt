package com.zhong;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.zhong.cache.AreaModel;
import com.zhong.cache.UnitModel;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


/**
 * @date 2022/4/8 14:00
 */
public class GsonTest {


    @Test
    public void parse() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

        Gson gson = new Gson();
        Type type = new TypeToken<List<AreaModel>>() {
        }.getType();
        List<AreaModel> areaModels = gson.fromJson(content, type);
        System.out.println(areaModels.get(0).getAreaName());
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + areaModels.size());
//consume time: 953::::147177
    }


    @Test
    public void parse2() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("jacksonTest.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

        Gson gson = new Gson();
        Type type = new TypeToken<List<UnitModel>>() {
        }.getType();
        List<UnitModel> areaModels = gson.fromJson(content, type);
        System.out.println(areaModels.get(0).getUnitName().getAllLanguageString());
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + areaModels.size());
//consume time: 953::::147177
    }


}
