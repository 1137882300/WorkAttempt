-- t_cspu
-- t_cspu_language
-- t_item_base_info_copy
-- t_item_base_info_copy_language
-- t_item_base_master
-- t_item_base_master_language
-- t_item_desc_copy
-- t_item_desc_master
-- t_item_extend_copy
-- t_item_extend_master
-- t_item_image_copy
-- t_item_image_master
-- t_shop_unique
-- t_global_relation
-- t_stock
-- t_stock_exists
-- t_stock_flow
-- t_stock_sales_volume
-- t_stock_sales_volume_flow
-- t_channel_stock
-- t_channel_mapping
-- t_bbmall_cn_user_mapping
-- t_brand_language
-- t_brand
-- t_brand_trademark
-- t_brand_type_language
-- t_brand_type
-- t_category_backend_brand_relation
-- t_category_backend_language
-- t_category_backend
-- t_property_language
-- t_property
-- t_goods_unit
-- t_goods_unit_language
-- t_country_locale
-- t_country_locale_language
-- t_category_relation
-- t_property_value
-- t_property_value_language
-- t_currency
-- t_currency_language
-- t_area_info
-- t_area_info_language
-- t_record_push
-- t_category_property_relation
-- t_property_relation
-- t_sku_copy
-- t_sku_copy_language
-- t_sku_master
-- t_sku_master_language
-- t_spu_base
-- t_spu_base_language
-- t_spu_desc
-- t_spu_extend
-- t_spu_image










    `category_backend_id`,               bigint(20) NOT NULL,
    `platform`,              varchar(100) CHARACTER SET utf8 DEFAULT NULL,
    `outer_category_id`,             varchar(100) CHARACTER SET utf8 DEFAULT NULL,
    `parent_id`,                 bigint(20) DEFAULT NULL,
    `icon_url`,               varchar(256) CHARACTER SET utf8 DEFAULT NULL,
    `is_leaf`,                   tinyint(1) DEFAULT NULL,
                                 text CHARACTER SET utf8,
    `category_code`,                 varchar(10) DEFAULT NULL COMMENT '类目编码',
    `level`,                 tinyint(1) DEFAULT NULL COMMENT '层级',
    `is_deleted`,            tinyint(1) DEFAULT NULL,
    `create_time`,           bigint(20) DEFAULT NULL,
    `update_time`,           bigint(20) DEFAULT NULL,
    `creator_id`,            bigint(20) DEFAULT NULL,
    `modified_id`,           bigint(20) DEFAULT NULL,
    `version`,               bigint(20) DEFAULT NULL,
    `status`,                tinyint(4) DEFAULT NULL,





