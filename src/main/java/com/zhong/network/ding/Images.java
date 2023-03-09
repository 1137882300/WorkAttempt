package com.zhong.network.ding;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.network.HttpService;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @date 2022/11/23 13:31
 */
public class Images {

    public static void main(String[] args) {

        List<String> sqlSpu = Lists.newArrayList();
        List<String> sqlCspu = Lists.newArrayList();
        List<String> sqlSku = Lists.newArrayList();

        String ss = "https://static.xyb2b.com/images/8f20b689a27151f66fb0100a04747c2b.png, https://static.xyb2b.com/images/4e9b340a5050813b979efb8426922217.png, https://static.xyb2b.com/images/31017abb14fe6bba555bacb3ea3eb265.png, https://static.xyb2b.com/images/2bbe48f96c86c6241283f088806a9fa3.png, https://static.xyb2b.com/images/0a78d300d12f9cac98dc45cc4e28f86b.png, https://static.xyb2b.com/images/5af48bc72e144d90798090e06a03bc17.png, https://static.xyb2b.com/images/b0dcda5f88fa3c532ecb99d4f92e88bb.png, https://static.xyb2b.com/images/3ad364dd36d749756406094d1409424b.png, https://static.xyb2b.com/images/a095d3277ac379118b1e00d5173c97e0.png, https://static.xyb2b.com/images/1727177380a6d815e6a6998d405ae7ed.png, https://static.xyb2b.com/images/5b8648dc81f3dc97fd957e553787ba87.png, https://static.xyb2b.com/images/83f5f31c93dee1aa91f9fa6de8f03ddc.png, https://static.xyb2b.com/images/5acf6fde4f30f43ae16fabc10960b9bf.png, https://static.xyb2b.com/images/90bd8e1f98f3c75f152a661675831907.png, https://static.xyb2b.com/images/fad6547bc5fcb86fe6a7fb74a6921f4c.png, https://static.xyb2b.com/images/57a43abf0aae1ee295bd75e506629de1.png, https://static.xyb2b.com/images/4150a1ad521c32d962ce583fb1f4bdb1.png, https://static.xyb2b.com/images/a4690df630c47d4e203ddb093028671b.png, https://static.xyb2b.com/images/cc2d2946d238f94b1b575ac8d07c7d80.png, https://static.xyb2b.com/images/1acabdf57f9ee1285f389c08029de193.png";
        List<String> list = Arrays.asList(ss.split(", "));
        String path = "https://shield.xyb2b.com/fake/api/compressimage?imageUrl=";
        list.forEach(x -> {
            String url = path + x;
            JSONObject post = HttpService.post(url, new JSONObject());
            String oldImg = post.getString("imageUrl");
            String newImageUrl = post.getString("newImageUrl");
            sqlSpu.add(
                    "update t_spu_image set version=version+1, update_time=1669183120000, image_url='" + newImageUrl + "' where image_url='" + oldImg + "' ;"
            );
            sqlCspu.add(
                    "update t_cspu set version=version+1, system_update_time=1669183120000, image_url='" + newImageUrl + "' where image_url='" + oldImg + "' ;"
            );
            sqlSku.add(
                    "update t_sku_copy set version=version+1, system_update_time=1669183120000, image_url='" + newImageUrl + "' where image_url='" + oldImg + "' ;"
            );
        });

        FileUtil.writeUtf8Lines(sqlSpu, new File("sqlSpu.sql"));
        FileUtil.writeUtf8Lines(sqlCspu, new File("sqlCspu.sql"));
        FileUtil.writeUtf8Lines(sqlSku, new File("sqlSku.sql"));

        //String path1 = "https://shield.xyb2b.com/fake/api/compressimage?imageUrl=https://static.xyb2b.com/images/8f20b689a27151f66fb0100a04747c2b.png";
        //JSONObject post = HttpService.post(path1, new JSONObject());
        //System.out.println(post);
        //{"imageUrl":"https://static.xyb2b.com/images/8f20b689a27151f66fb0100a04747c2b.png","isNew":"true","newImageUrl":"https://static.xyb2b.com/images/4dfc534f8638e6da3f5b68b56bda790c.png"}

    }

}
