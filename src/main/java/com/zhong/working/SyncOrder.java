package com.zhong.working;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import com.zhong.Utils.FileUtils;
import com.zhong.network.HttpService;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author: juzi
 * @date: 2023/5/4
 * @desc:
 */
public class SyncOrder {

    public static void main(String[] args) {

        String path = "F:\\工作记录\\订单.text";
        File file = new File(path);
        List<String> list = FileUtil.readUtf8Lines(file);
        //反转
        Collections.reverse(list);
        //顺序请求
        List<String> result = Lists.newArrayList();

//        List<String> ss = Lists.newArrayList(list.get(0));
//        System.out.println(ss);

        list.forEach(x -> {
            JSONObject jsonObject = JSONObject.parseObject(x);
            JSONObject post = HttpService.post("https://fxhz.wgly.hangzhou.gov.cn/api/order/back/ticket/save", jsonObject);
            result.add(post.toJSONString() + "---------> " + x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        //写文件
        FileUtil.writeUtf8Lines(result, new File("结果.text"));
    }
}
