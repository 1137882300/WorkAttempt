package com.zhong.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.zhong.entity.MultiLanguageString;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 15:09
 */
public class Main {


    private final AtomicReference<Map<String, Object>> cacheMap = new AtomicReference<>(new HashMap<>());


    public static void main(String[] args) {

        List<Category> list = Lists.newArrayList();

        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setRootId((long) (i + 1));
            Map<Locale, String> languageStringMap = new HashMap<>();
            languageStringMap.put(Locale.ENGLISH, "zz"+i);
            CategoryEntity entity = CategoryEntity.builder().categoryBackendId((long) (i + 1))
                    .categoryName(MultiLanguageString.of(languageStringMap))
                    .platform("bbmall"+i).outerCategoryId("1"+i).categoryCode("1"+i)
                    .parentId((long) (i + 1)).iconUrl("utl"+i).status(1).isDeleted(0)
                    .createTime(System.currentTimeMillis()).updateTime(System.currentTimeMillis())
                    .creatorId((long) (i + 1)).modifiedId((long) (i + 1)).features("string"+i).level(1).version((long) (i + 1))
                    .children(null).build();
            category.setCategoryTree(entity);
            list.add(category);
        }


        //生成上传的对象
        String jsonString = JSON.toJSONString(list);
        System.out.println(jsonString);

        //上传OSS
        //拉取OSS

        //解析json数组
        List<Category> array = JSON.parseArray(jsonString, Category.class);
        System.out.println(array);
        //写入缓存

        //原子性-start
        AtomicReference<Category> atomicReference = new AtomicReference<>();
        LocalCache.clear();
        for (int i = 0; i < array.size(); i++) {
            Category category = array.get(i);
            atomicReference.set(category);
            LocalCache.put(atomicReference.get().getRootId().toString(), atomicReference.get().getCategoryTree());
        }
        //原子性-end


        System.out.println(LocalCache.size());

//




    }

//    AtomicReference<Category> atomicReference = new AtomicReference<>();
//            atomicReference.set(category);
//    AtomicReference<LocalCache> reference = new AtomicReference<>();
//            LocalCache.put(category.getRootId().toString(), category.getCategoryTree());
//    AtomicReferenceFieldUpdater<String, Object> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(String.class, Object.class, "name");
//            atomicReferenceFieldUpdater.compareAndSet("nihao", "ok", "compareAndSet");

}
