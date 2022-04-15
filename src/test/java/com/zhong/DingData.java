package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.Relation;
import com.zhong.entity.SpuImg;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2022/4/14 19:41
 */
public class DingData {
    /**
     * 商品模板的同步图片
     */
    @Test
    public void img2() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\image2.csv";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        String line = null;

        List<SpuImg> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            SpuImg spuImg = SpuImg.builder()
                    .spuId(Long.valueOf(split[0]))
                    .isMaster(Integer.valueOf(split[1]))
                    .imageUrl(split[2])
                    .imageOrder(StringUtils.isBlank(split[3]) ? null : Integer.valueOf(split[3]))
                    .type(Integer.valueOf(split[4]))
                    .build();
            list.add(spuImg);
        }

        String pathImg = "C:\\Users\\EDZ\\Downloads\\relation2.csv";
        BufferedReader reader2 = new BufferedReader(new FileReader(pathImg));
        reader2.readLine();
        String line2 = null;
        List<Relation> relationList = Lists.newLinkedList();//SPU_ITEMTMP
        while ((line2 = reader2.readLine()) != null) {
            String[] split = line2.split(",");
            Relation relation = Relation.builder()
                    .master(Long.valueOf(split[0]))//spuID
                    .slave(Long.valueOf(split[1]))//itemID
                    .shopId(Long.valueOf(split[2].replace("\"", "")))
                    .build();

            relationList.add(relation);
        }
        Map<Long, List<SpuImg>> group = list.stream().collect(Collectors.groupingBy(SpuImg::getSpuId));
        Long item_image_id = 3000023072L;
        StringBuilder sb = new StringBuilder();
        for (Relation relation : relationList) {
            if (group.containsKey(relation.getMaster())) {
                List<SpuImg> spuImgs = group.get(relation.getMaster());
                for (SpuImg spuImg : spuImgs) {
                    sb.append("insert into t_item_image_copy ( item_image_id, item_id, image_url, is_master, shop_id, image_order, is_deleted, create_time, version, creator_id, type ) ");
                    sb.append("values( ");
                    sb.append(item_image_id).append(",");
                    sb.append(relation.getSlave()).append(",");
                    sb.append("'").append(spuImg.getImageUrl()).append("'").append(",");
                    sb.append(spuImg.getIsMaster()).append(",");
                    sb.append(relation.getShopId()).append(",");
                    sb.append(spuImg.getImageOrder()).append(",");
                    sb.append(0).append(",");
                    sb.append(1649926410000L).append(",");
                    sb.append(1L).append(",");
                    sb.append("-1").append(",");
                    sb.append(spuImg.getType());
                    sb.append(" )");
                    item_image_id--;
                    sb.append(";");
                    sb.append("\n");
                }
            } else {
                System.out.println("else: " + relation);
            }
        }

        System.out.println("item_image_id: " + item_image_id);//3000021062

        FileWriter writer = new FileWriter("Insert1.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }

    /**
     * 渠道的图片
     */
    @Test
    public void storeImage() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\image2.csv";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        String line = null;

        List<SpuImg> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            SpuImg spuImg = SpuImg.builder()
                    .spuId(Long.valueOf(split[0]))
                    .isMaster(Integer.valueOf(split[1]))
                    .imageUrl(split[2])
                    .imageOrder(StringUtils.isBlank(split[3]) ? null : Integer.valueOf(split[3]))
                    .type(Integer.valueOf(split[4]))
                    .build();
            list.add(spuImg);
        }
        Map<Long, List<SpuImg>> group = list.stream().collect(Collectors.groupingBy(SpuImg::getSpuId));

        String pathImg = "C:\\Users\\EDZ\\Downloads\\relation3.csv";
        BufferedReader reader2 = new BufferedReader(new FileReader(pathImg));
        reader2.readLine();
        String line2 = null;
        List<Relation> relationList = Lists.newLinkedList();//
        while ((line2 = reader2.readLine()) != null) {
            String[] split = line2.split(",");
            Relation relation = Relation.builder()
                    .master(Long.valueOf(split[0]))//spuID
                    .slave(Long.valueOf(split[1]))//itemID
                    .shopId(Long.valueOf(split[2].replace("\"", "")))
                    .build();
            relationList.add(relation);
        }

        Long item_image_id = 3000021062L;
        StringBuilder sb = new StringBuilder();
        for (Relation relation : relationList) {
            if (group.containsKey(relation.getMaster())) {
                List<SpuImg> spuImgs = group.get(relation.getMaster());
                for (SpuImg spuImg : spuImgs) {
                    //copy表
                    sb.append("insert into t_item_image_copy ( item_image_id, item_id, image_url, is_master, shop_id, image_order, is_deleted, create_time, version, creator_id, type ) ");
                    sb.append("values( ");
                    sb.append(item_image_id).append(",");
                    sb.append(relation.getSlave()).append(",");
                    sb.append("'").append(spuImg.getImageUrl()).append("'").append(",");
                    sb.append(spuImg.getIsMaster()).append(",");
                    sb.append(relation.getShopId()).append(",");
                    sb.append(spuImg.getImageOrder()).append(",");
                    sb.append(0).append(",");
                    sb.append(1649926410000L).append(",");
                    sb.append(1L).append(",");
                    sb.append("-1").append(",");
                    sb.append(spuImg.getType());
                    sb.append(" )");
                    sb.append(";");
                    sb.append("\n");

                    //master表
                    sb.append("insert into t_item_image_master ( item_image_id, item_id, image_url, is_master, shop_id, image_order, is_deleted, create_time, version, creator_id, type ) ");
                    sb.append("values( ");
                    sb.append(item_image_id).append(",");
                    sb.append(relation.getSlave()).append(",");
                    sb.append("'").append(spuImg.getImageUrl()).append("'").append(",");
                    sb.append(spuImg.getIsMaster()).append(",");
                    sb.append(relation.getShopId()).append(",");
                    sb.append(spuImg.getImageOrder()).append(",");
                    sb.append(0).append(",");
                    sb.append(1649926410000L).append(",");
                    sb.append(1L).append(",");
                    sb.append("-1").append(",");
                    sb.append(spuImg.getType());
                    sb.append(" )");
                    sb.append(";");
                    sb.append("\n");

                    item_image_id--;
                }
            } else {
                System.out.println("else: " + relation);
            }
        }

        System.out.println("item_image_id: " + item_image_id);//3000019917

        FileWriter writer = new FileWriter("Insert（copy+master）.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }


    @Test
    public void img() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\spu-img2.csv";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        String line = null;

        List<SpuImg> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            SpuImg spuImg = SpuImg.builder()
                    .spuId(Long.valueOf(split[0]))
                    .isMaster(Integer.valueOf(split[1]))
                    .imageUrl(split[2])
                    .build();
            list.add(spuImg);
        }

        String pathImg = "C:\\Users\\EDZ\\Downloads\\relation2.csv";
        BufferedReader reader2 = new BufferedReader(new FileReader(pathImg));
        reader2.readLine();
        String line2 = null;
        List<Relation> relationList = Lists.newLinkedList();
        while ((line2 = reader2.readLine()) != null) {
            String[] split = line2.split(",");
            Relation relation = Relation.builder()
                    .master(Long.valueOf(split[0]))
                    .slave(Long.valueOf(split[1]))
                    .build();
            relationList.add(relation);
        }
        //SPU_ITEMTMP
        Map<Long, Long> map = relationList.stream().collect(Collectors.toMap(Relation::getMaster, Relation::getSlave, (s, e) -> e));

        StringBuilder sb = new StringBuilder();

        Long item_image_id = 3000026317L;
        for (SpuImg spuImg : list) {
            if (map.containsKey(spuImg.getSpuId())) {
                Long itemId = map.get(spuImg.getSpuId());
                sb.append("insert into t_item_image_copy ( item_image_id, item_id, image_url, is_master, is_deleted, create_time, version, creator_id, type ) ");
                sb.append("values( ");
                sb.append(item_image_id).append(",");
                sb.append(itemId).append(",");
                sb.append(spuImg.getImageUrl()).append(",");
                sb.append(spuImg.getIsMaster()).append(",");
                sb.append(0).append(",");
                sb.append(1649926410000L).append(",");
                sb.append(1L).append(",");
                sb.append("-1").append(",");
                sb.append(1);
                sb.append(" )");
                item_image_id--;
                sb.append("\n");
            }
        }

//        System.out.println(sb);
        FileWriter writer = new FileWriter("Insert-img.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }


}
