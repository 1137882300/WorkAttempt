package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.Utils.DateUtil;
import com.zhong.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.sequence.EditScript;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

//select id,parent_id,division_name,division_level,creator_id,modified_id,is_deleted,version,tenant_platform,create_time,update_time,has_children
//from t_uic_division_info where id_deleted = 0

    @Test
    public void areaSql() throws IOException {
        String path = "C:\\Users\\EDZ\\Downloads\\力宝.csv";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();
        String line = null;

        List<DivisionInfoPO> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            DivisionInfoPO divisionInfoPO = new DivisionInfoPO();
            divisionInfoPO.setId(Long.valueOf(split[0]));
            divisionInfoPO.setParentId(Long.valueOf(split[1]));
            divisionInfoPO.setDivisionName(split[2]);
            divisionInfoPO.setDivisionLevel(Integer.valueOf(split[3]));
            if (StringUtils.isNotBlank(split[4])) {
                divisionInfoPO.setCreatorId(Long.valueOf(split[4]));
            }
            if (StringUtils.isNotBlank(split[5])) {
                divisionInfoPO.setModifierId(Long.valueOf(split[5]));
            }
            divisionInfoPO.setIsDeleted(Long.valueOf(split[6]));
            divisionInfoPO.setVersion(0L);
            divisionInfoPO.setTenantPlatform("product_GLO");
            if (StringUtils.isNotBlank(split[9])) {
//                divisionInfoPO.setCreateTime(DateUtil.toEpochMilliZone8(split[9].substring(0, split[9].length() - 2)));//date转时间戳
                divisionInfoPO.setCreateTime(DateUtil.toEpochMilliZone8(split[9]));
            } else {
                divisionInfoPO.setCreateTime(1652450251000L);
            }
            if (StringUtils.isNotBlank(split[10])) {
//                divisionInfoPO.setUpdateTime(DateUtil.toEpochMilliZone8(split[10].substring(0, split[10].length() - 2)));
                divisionInfoPO.setCreateTime(DateUtil.toEpochMilliZone8(split[10]));
            }
            divisionInfoPO.setHasChildren(Integer.valueOf(split[11]));
            list.add(divisionInfoPO);
        }
        reader.close();

        StringBuilder sb = new StringBuilder();
        StringBuilder languageSb = new StringBuilder();

        Long lanId = 2000000010L;
        //todo 要去掉第一条国家的数据
        if (CollectionUtils.isNotEmpty(list)) {
            for (DivisionInfoPO divisionInfoPO : list) {
                sb.append("insert into t_area_info ( id, parent_id, country_id, area_level, platform, creator_id, modified_id, create_time, update_time, is_deleted, version, has_children ) ");
                sb.append("values( ");
                sb.append(divisionInfoPO.getId()).append(",");
                if (divisionInfoPO.getParentId() == 1) {
                    sb.append(0L).append(",");
                } else {
                    sb.append(divisionInfoPO.getParentId()).append(",");
                }
                sb.append(2000000021L).append(",");//国家id
                sb.append(divisionInfoPO.getDivisionLevel()).append(",");
                sb.append("\"").append("product_GLO").append("\"").append(",");
                sb.append(divisionInfoPO.getCreatorId()).append(",");
                sb.append("-22").append(",");
                sb.append(divisionInfoPO.getCreateTime()).append(",");
                sb.append(divisionInfoPO.getUpdateTime()).append(",");
                sb.append(0).append(",");
                sb.append(0L).append(",");
                sb.append(divisionInfoPO.getHasChildren());
                sb.append(" );");
                sb.append("\n");

                languageSb.append("insert into t_area_info_language ( id, area_info_id, area_name, preferred_language, creator_id, modified_id, create_time, update_time, is_deleted, version )");
                languageSb.append("values( ");
                languageSb.append(lanId).append(",");//用最小的id往前走
                languageSb.append(divisionInfoPO.getId()).append(",");
                languageSb.append("\"").append(divisionInfoPO.getDivisionName()).append("\"").append(",");
                languageSb.append("\"").append("in_ID").append("\"").append(",");
                languageSb.append(divisionInfoPO.getCreatorId()).append(",");
                languageSb.append("-22").append(",");
                languageSb.append(divisionInfoPO.getCreateTime()).append(",");
                languageSb.append(divisionInfoPO.getUpdateTime()).append(",");
                languageSb.append(0).append(",");
                languageSb.append(0L);
                languageSb.append(" );");
                languageSb.append("\n");
                lanId--;
            }
        }

        FileWriter writer = new FileWriter("region.text");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();

        FileWriter writer2 = new FileWriter("regionLan.text");
        BufferedWriter bufferedWriter2 = new BufferedWriter(writer2);
        bufferedWriter2.write(languageSb.toString());
        bufferedWriter2.close();

    }


    @Test
    public void dateToLong() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2021-11-03 14:03:21", dateTimeFormatter);
        long l = parse.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        System.out.println(l);

    }

}
