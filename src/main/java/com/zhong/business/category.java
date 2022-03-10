package com.zhong.business;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/3/1 13:39
 */
public class category {



    public CategoryModel queryByCategoryId(Long categoryId) {

        String data = "";

        List<CategoryModel> categoryModelList = JSONObject.parseArray(data, CategoryModel.class);
        Map<String, CategoryModel> categoryModelMap = categoryModelList.stream().collect(Collectors.toMap(CategoryModel::getRootId, Function.identity(), (s, e) -> s));

        if (MapUtils.isEmpty(categoryModelMap)){
            return null;
        }
        for (Map.Entry<String, CategoryModel> entry : categoryModelMap.entrySet()){
            String rootId = entry.getKey();
            CategoryModel categoryModel = entry.getValue();
            if (String.valueOf(categoryId).equals(rootId)){
                return categoryModel;
            }
            List<CategoryModel> childrenList = categoryModel.getChildrenList();
            if (CollectionUtils.isNotEmpty(childrenList)){
                this.loopChildrenList(childrenList, categoryId);
            }
        }
        return null;
    }





    private CategoryModel loopChildrenList(List<CategoryModel> childrenList, Long categoryId){
        if (CollectionUtils.isNotEmpty(childrenList)){
            for (CategoryModel categoryModel : childrenList) {
                Long categoryBackendId = categoryModel.getCategoryBackendId();
                if (categoryId.equals(categoryBackendId)){
                    return categoryModel;
                }
                if (CollectionUtils.isNotEmpty(categoryModel.getChildrenList())){
                    this.loopChildrenList(categoryModel.getChildrenList(), categoryId);
                }
            }
        }
        return null;
    }



}
