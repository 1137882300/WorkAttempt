package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.ItemImg;
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

    /** 查item-tmp的图片
     * 			SELECT t.item_tmp_id, k.is_master,  k.image_url,  k.image_order,k.type FROM t_item_image_copy as k
     * 			INNER JOIN
     * 			(
     *     			select v.item_id as item_tmp_id FROM t_item_base_info_copy as v
     *     			INNER JOIN
     *     			(
     *     							select brand_id from t_brand_language where brand_name in
     *     							('SKG','地对空','暴雪','D8','几光','莱仕达','imilab') and preferred_language = 'zh_CN' group by brand_id
     *     			)as x
     *     			on v.brand_id = x.brand_id
     *     			where (v.features -> '$.bbmallGloItemSpuOwner' = 'true'
     * 					and v.features -> '$.bbmallGloItemTemplate' = 'true')
     *     			and v.is_deleted = 0
     *     			and v.`status` = 0
     * 			)as t
     * 			on k.item_id = t.item_tmp_id
     * 			WHERE k.is_deleted = 0
     *
     *  查tmp关联的spu
     *    SELECT a.`master` as spu_id, a.`slave` as item_tmp_id FROM t_global_relation as a
     *     INNER JOIN
     * 		(
     * 			SELECT distinct(t.item_tmp_id) FROM t_item_image_copy as k
     * 			INNER JOIN
     * 			(
     *     			select v.item_id as item_tmp_id FROM t_item_base_info_copy as v
     *     			INNER JOIN
     *     			(
     *     							select brand_id from t_brand_language where brand_name in
     *     							('SKG','地对空','暴雪','D8','几光','莱仕达','imilab') and preferred_language = 'zh_CN' group by brand_id
     *     			)as x
     *     			on v.brand_id = x.brand_id
     *     			where (v.features -> '$.bbmallGloItemSpuOwner' = 'true'
     * 					and v.features -> '$.bbmallGloItemTemplate' = 'true')
     *     			and v.is_deleted = 0
     *     			and v.`status` = 0
     * 			)as t
     * 			on k.item_id = t.item_tmp_id
     * 			WHERE k.is_deleted = 0
     * 		) as c
     *     on a.`slave` = c.item_tmp_id
     * 		where a.type = 'SPU_ITEMTMP'
     * 		and a.is_deleted = 0
     */

    /**
     * -- 复制：商品图片 (要用拼接sql方式)
     * -- 商品模板 -> spu
     */
    @Test
    public void copyImg() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\item-tmp-img.csv";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        String line = null;
        List<ItemImg> itemImgList = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            ItemImg itemImg = ItemImg.builder()
                    .itemId(Long.valueOf(split[0]))
                    .isMaster(Integer.valueOf(split[1]))
                    .imageUrl(split[2])
                    .imageOrder(StringUtils.isBlank(split[3]) ? null : Integer.valueOf(split[3]))
                    .type(Integer.valueOf(split[4]))
                    .build();
            itemImgList.add(itemImg);
        }

        String pathImg = "C:\\Users\\EDZ\\Downloads\\spu-tmp-relation.csv";
        BufferedReader reader2 = new BufferedReader(new FileReader(pathImg));
        reader2.readLine();
        String line2 = null;
        List<Relation> relationList = Lists.newLinkedList();//SPU_ITEMTMP
        while ((line2 = reader2.readLine()) != null) {
            String[] split = line2.split(",");
            Relation relation = Relation.builder()
                    .master(Long.valueOf(split[0]))//spuID
                    .slave(Long.valueOf(split[1]))//ITEM_TMP_ID
                    .build();
            relationList.add(relation);
        }

        //一个item 多张图片
        Map<Long, List<ItemImg>> group = itemImgList.stream().collect(Collectors.groupingBy(ItemImg::getItemId));
        Long item_image_id = 2000002260L;
        StringBuilder sb = new StringBuilder();
        for (Relation relation : relationList) {
            if (group.containsKey(relation.getSlave())) {
                sb.append("insert into t_spu_image ( item_image_id, item_id, shop_id, image_url, is_master, image_order, is_deleted, create_time, version, creator_id, type ) values ");
                int i = 0;
                for (ItemImg itemImg : group.get(relation.getSlave())) {
                    i++;
                    sb.append(" ( ");
                    sb.append(item_image_id).append(",");
                    sb.append(relation.getMaster()).append(",");
                    sb.append(-1).append(",");
                    sb.append("'").append(itemImg.getImageUrl()).append("'").append(",");
                    sb.append(itemImg.getIsMaster()).append(",");
                    sb.append(itemImg.getImageOrder()).append(",");
                    sb.append(0).append(",");
                    sb.append("1651816080000").append(",");
                    sb.append(1).append(",");
                    sb.append("-1").append(",");
                    sb.append(itemImg.getType());
                    //最后一次，不加 ,
                    if (group.get(relation.getSlave()).size() == i) {
                        sb.append(" )");
                    } else {
                        sb.append(" ),");
                    }
                    item_image_id--;
                }
                sb.append(";");
                sb.append("\n");
            }
        }


        System.out.println("item_image_id: " + item_image_id);//2000001710

        FileWriter writer = new FileWriter("spu-img.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();

    }


    /**
     * 商品模板的同步图片
     */
    @Test
    public void img2() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\imageNew.csv";
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

        String pathImg = "C:\\Users\\EDZ\\Downloads\\模板relation.csv";
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

        System.out.println("item_image_id: " + item_image_id);//3000020618

        FileWriter writer = new FileWriter("mu-ban.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }

    /**
     * 渠道的图片
     */
    @Test
    public void storeImage() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\imageNew.csv";
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

        String pathImg = "C:\\Users\\EDZ\\Downloads\\商品relation.csv";
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

        Long item_image_id = 3000020617L;
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

        System.out.println("item_image_id: " + item_image_id);//3000019216

        FileWriter writer = new FileWriter("item（copy+master）.text");
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
