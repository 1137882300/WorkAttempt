package com.zhong.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @date 2022/11/24 19:33
 */
public enum TableMapping {


    T_ITEM_BASE_MASTER("T_ITEM_BASE_INFO_COPY"),
    T_ITEM_BASE_MASTER_LANGUAGE("T_ITEM_BASE_INFO_COPY_LANGUAGE"),
    T_ITEM_DESC_MASTER("T_ITEM_DESC_COPY"),
    T_ITEM_EXTEND_MASTER("T_ITEM_EXTEND_COPY"),
    T_ITEM_IMAGE_MASTER("T_ITEM_IMAGE_COPY"),
    T_SKU_MASTER("T_SKU_COPY"),
    T_SKU_MASTER_LANGUAGE("T_SKU_COPY_LANGUAGE"),

    ;

    private final String copy;

    TableMapping(String copy) {
        this.copy = copy;
    }

    public static TableMapping getMaster(String copy) {
        return Arrays.stream(TableMapping.values())
                .filter(x -> StringUtils.equalsIgnoreCase(copy, x.copy))
                .findFirst().orElse(null);
    }


    public static void main(String[] args) {
        String sql = "select item_id,spu_id,brand_id,category_backend_id,spu_code,country_id,shop_id,is_mixed,status,is_deleted,first_sale_time,create_time,system_update_time,update_time,version,features,first_sale_user_id,creator_id,modified_id,last_sale_time,last_sale_user_id, platform " +
                "from T_ITEM_BASE_INFO_COPY ";
        //where is_deleted=0
        String table = changeTable(sql);
        System.out.println(table);
    }

    private static String changeTable(String sql) {
        if (true) {
            String copy = "";
            copy = StringUtils.substringBetween(sql, "from", "where");
            if (StringUtils.isBlank(copy)) {
                copy = StringUtils.substringAfter(sql, "from");
            }
            TableMapping master = TableMapping.getMaster(copy.trim());
            if (Objects.isNull(master)) {
                return sql;
            }
            return StringUtils.replace(sql, copy.trim(), master.name());
        }
        return sql;
    }
}
