

CREATE TABLE `t_cspu`  (
                           `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品skuid',
                           `sku_code` varchar(100)  NULL DEFAULT NULL COMMENT 'sku编码',
                           `item_id` bigint(20) NOT NULL COMMENT '产品id',
                           `cspu_id` bigint(20) NOT NULL COMMENT '产品sku id',
                           `unit_id` bigint(20) NULL DEFAULT NULL COMMENT '计量单位 id',
                           `international_code` varchar(255)  NULL DEFAULT NULL COMMENT '国际条码',
                           `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                           `image_url` text  NULL COMMENT 'sku主图图片地址',
                           `sku_status` tinyint(4) NULL DEFAULT NULL COMMENT '产品状态：正常0，禁用1；',
                           `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间',
                           `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架操作人id',
                           `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                           `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                           `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                           `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                           `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                           `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                           `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                           `features` text  NULL COMMENT '预留拓展字段',
                           `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架时间',
                           `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架操作人id',
                           PRIMARY KEY (`cspu_id`) USING BTREE,
                           INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'cspu表';





CREATE TABLE `t_cspu_language`  (
                                    `sku_lan_id` bigint(20) NOT NULL COMMENT '多语言id',
                                    `sku_id` bigint(20) NOT NULL COMMENT 'cspu id',
                                    `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                    `price_extend` text  NULL COMMENT '阶梯批发价数组json格式、币种等信息阶段数量等等、建议采购价',
                                    `sale_property_extend` text  NULL COMMENT '销售属性拓展信息',
                                    `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                    `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                    `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                    `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                    `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                    `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                    `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                    `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                    `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                    PRIMARY KEY (`sku_lan_id`) USING BTREE,
                                    INDEX `index_itemid`(`item_id`) USING BTREE,
                                    INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'cspu语言表';





CREATE TABLE `t_item_base_info_copy`  (
                                          `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                          `platform` varchar(128)  NULL DEFAULT NULL COMMENT '平台',
                                          `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
                                          `category_backend_id` bigint(20) NULL DEFAULT NULL COMMENT '后台类目id',
                                          `spu_code` varchar(255)  NULL DEFAULT NULL COMMENT 'spu编码',
                                          `spu_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
                                          `country_id` bigint(20) NULL DEFAULT NULL COMMENT '国别',
                                          `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                          `is_mixed` tinyint(4) NULL DEFAULT NULL COMMENT '是否支持混批:不支持0 支持1',
                                          `status` tinyint(4) NULL DEFAULT 0 COMMENT '产品状态：正常0，禁用1；商品副本状态：作废1，审核中2，审核失败3，审核成功4；商品正本状态：正常0，作废1',
                                          `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                          `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '首次上架时间',
                                          `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                                          `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                          `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                          `version` bigint(20) NULL DEFAULT NULL COMMENT '当前数据行版本',
                                          `features` text  NULL COMMENT '预留拓展字段',
                                          `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '首次上架操作人id',
                                          `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                          `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                          `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间戳',
                                          `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架人id',
                                          PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item副本基础表';





CREATE TABLE `t_item_base_info_copy_language`  (
                                                   `item_lan_id` bigint(20) NOT NULL COMMENT '主键id',
                                                   `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                                   `title` text  NULL COMMENT '商品标题',
                                                   `sub_title` text  NULL COMMENT '商品副标题',
                                                   `property_info` text  NULL COMMENT '商品属性信息',
                                                   `sale_property_extend` text  NULL COMMENT '商品销售属性拓展JSON结构',
                                                   `item_type` varchar(128)  NULL DEFAULT NULL COMMENT '商品型号',
                                                   `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                                                   `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                                   `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                                                   `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                                   `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                                   `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                                   `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                                   `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                                                   `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                                   `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                                   PRIMARY KEY (`item_lan_id`) USING BTREE,
                                                   INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item副本基础语言表';





CREATE TABLE `t_item_base_master`  (
                                       `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                       `platform` varchar(128)  NULL DEFAULT NULL COMMENT '平台',
                                       `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
                                       `category_backend_id` bigint(20) NULL DEFAULT NULL COMMENT '后台类目id',
                                       `spu_code` varchar(255)  NULL DEFAULT NULL COMMENT 'spu编码',
                                       `spu_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
                                       `country_id` bigint(20) NULL DEFAULT NULL COMMENT '国别',
                                       `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                       `is_mixed` tinyint(4) NULL DEFAULT NULL COMMENT '是否支持混批:不支持0 支持1',
                                       `status` tinyint(4) NULL DEFAULT 0 COMMENT '产品状态：正常0，禁用1；商品副本状态：作废1，审核中2，审核失败3，审核成功4；商品正本状态：正常0，作废1',
                                       `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                       `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '首次上架时间',
                                       `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                                       `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                       `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                       `version` bigint(20) NULL DEFAULT NULL COMMENT '当前数据行版本',
                                       `features` text  NULL COMMENT '预留拓展字段',
                                       `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '首次上架操作人id',
                                       `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                       `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                       `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间戳',
                                       `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架人id',
                                       PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item主本基础表';





CREATE TABLE `t_item_base_master_language`  (
                                                `item_lan_id` bigint(20) NOT NULL COMMENT '主键id',
                                                `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                                `title` text  NULL COMMENT '商品标题',
                                                `sub_title` text  NULL COMMENT '商品副标题',
                                                `property_info` text  NULL COMMENT '商品属性信息',
                                                `sale_property_extend` text  NULL COMMENT '商品销售属性拓展JSON结构',
                                                `item_type` varchar(128)  NULL DEFAULT NULL COMMENT '商品型号',
                                                `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                                                `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                                `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                                                `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                                `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                                `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                                `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                                `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                                                `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                                `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                                PRIMARY KEY (`item_lan_id`) USING BTREE,
                                                INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item主本基础语言表';





CREATE TABLE `t_item_desc_copy`  (
                                     `item_desc_id` bigint(20) NOT NULL COMMENT '商品详情表id',
                                     `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                     `item_desc` text  NULL COMMENT '商品详情描述',
                                     `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                     `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                                     `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                     `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                     `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                     `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                     `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                     `features` text  NULL COMMENT '预留后期的拓展字段',
                                     `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                     `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                     PRIMARY KEY (`item_desc_id`) USING BTREE,
                                     INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item副本详情表';





CREATE TABLE `t_item_desc_master`  (
                                       `item_desc_id` bigint(20) NOT NULL COMMENT '商品详情表id',
                                       `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                       `item_desc` text  NULL COMMENT '商品详情描述',
                                       `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                       `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                                       `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                       `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                       `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                       `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                       `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                       `features` text  NULL COMMENT '预留后期的拓展字段',
                                       `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                       `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                       PRIMARY KEY (`item_desc_id`) USING BTREE,
                                       INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item主本详情表';





CREATE TABLE `t_item_extend_copy`  (
                                       `item_ext_id` bigint(20) NOT NULL COMMENT '商品拓展表id',
                                       `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
                                       `extend_key` int(11) NULL DEFAULT NULL COMMENT '拓展类型',
                                       `extend_info` longtext  NULL COMMENT '具体拓展信息json结构',
                                       `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                       `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                       `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                       `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                       `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                       `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                       `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'skuid',
                                       `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                       `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                       PRIMARY KEY (`item_ext_id`) USING BTREE,
                                       UNIQUE INDEX `t_item_extend_copy_item_id_IDX`(`item_id`, `sku_id`, `extend_key`) USING BTREE,
                                       INDEX `index_itemid`(`item_id`) USING BTREE,
                                       INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'item副本拓展表';





CREATE TABLE `t_item_extend_master`  (
                                         `item_ext_id` bigint(20) NOT NULL COMMENT '商品拓展表id',
                                         `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
                                         `extend_key` int(11) NULL DEFAULT NULL COMMENT '拓展类型',
                                         `extend_info` longtext  NULL COMMENT '具体拓展信息json结构',
                                         `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                         `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                         `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                         `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                         `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                         `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                         `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'skuid',
                                         `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                         `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                         PRIMARY KEY (`item_ext_id`) USING BTREE,
                                         UNIQUE INDEX `t_item_extend_master_item_id_IDX`(`item_id`, `sku_id`, `extend_key`) USING BTREE,
                                         INDEX `index_itemid`(`item_id`) USING BTREE,
                                         INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'item主本拓展表';





CREATE TABLE `t_item_image_copy`  (
                                      `item_image_id` bigint(20) NOT NULL COMMENT '商品图片id',
                                      `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                      `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
                                      `image_url` varchar(200)  NULL DEFAULT NULL COMMENT '图片地址',
                                      `is_master` tinyint(4) NULL DEFAULT NULL COMMENT '是否主图',
                                      `image_order` int(11) NULL DEFAULT NULL COMMENT '图片顺序',
                                      `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                      `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                      `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                      `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                      `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                      `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                      `type` int(3) NULL DEFAULT NULL COMMENT '图片类型',
                                      PRIMARY KEY (`item_image_id`) USING BTREE,
                                      INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item副本图片表';





CREATE TABLE `t_item_image_master`  (
                                        `item_image_id` bigint(20) NOT NULL COMMENT '商品图片id',
                                        `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                        `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
                                        `image_url` varchar(200)  NULL DEFAULT NULL COMMENT '图片地址',
                                        `is_master` tinyint(4) NULL DEFAULT NULL COMMENT '是否主图',
                                        `image_order` int(11) NULL DEFAULT NULL COMMENT '图片顺序',
                                        `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                        `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                        `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                        `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                        `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                        `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                        `type` int(3) NULL DEFAULT NULL COMMENT '图片类型',
                                        PRIMARY KEY (`item_image_id`) USING BTREE,
                                        INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'item主本图片表';





CREATE TABLE `t_sku_copy`  (
                               `sku_id` bigint(20) NOT NULL COMMENT '商品skuid',
                               `sku_code` varchar(100)  NULL DEFAULT NULL COMMENT 'sku编码',
                               `item_id` bigint(20) NOT NULL COMMENT '商品id',
                               `cspu_id` bigint(20) NULL DEFAULT NULL COMMENT '产品sku id',
                               `international_code` varchar(255)  NULL DEFAULT NULL COMMENT '国际条码',
                               `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                               `image_url` text  NULL COMMENT 'sku主图图片地址',
                               `sku_status` tinyint(4) NULL DEFAULT NULL COMMENT '产品状态：正常0，禁用1；商品状态：已下架0，已上架1，作废2',
                               `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间',
                               `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架操作人id',
                               `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                               `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                               `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                               `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                               `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                               `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                               `features` text  NULL COMMENT '预留拓展字段',
                               `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架时间',
                               `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架操作人id',
                               `unit_id` bigint(20) NULL DEFAULT NULL COMMENT '单位id',
                               PRIMARY KEY (`sku_id`) USING BTREE,
                               INDEX `index_itemid`(`item_id`) USING BTREE,
                               INDEX `index_skucode`(`sku_code`) USING BTREE
) ENGINE = InnoDB comment 'sku副本表';





CREATE TABLE `t_sku_copy_language`  (
                                        `sku_lan_id` bigint(20) NOT NULL COMMENT 'sku多语言id',
                                        `sku_id` bigint(20) NOT NULL COMMENT 'skuid',
                                        `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                        `price_extend` text  NULL COMMENT '阶梯批发价数组json格式、币种等信息阶段数量等等、建议采购价',
                                        `sale_property_extend` text  NULL COMMENT '销售属性拓展信息',
                                        `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                        `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                        `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                        `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                        `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                        `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                        `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                        `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                        `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                        `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                        PRIMARY KEY (`sku_lan_id`) USING BTREE,
                                        INDEX `index_itemid`(`item_id`) USING BTREE,
                                        INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'sku副本语言表';





CREATE TABLE `t_sku_master`  (
                                 `sku_id` bigint(20) NOT NULL COMMENT '商品skuid',
                                 `sku_code` varchar(100)  NULL DEFAULT NULL COMMENT 'sku编码',
                                 `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                 `cspu_id` bigint(20) NULL DEFAULT NULL COMMENT '产品sku id',
                                 `international_code` varchar(255)  NULL DEFAULT NULL COMMENT '国际条码',
                                 `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                 `image_url` text  NULL COMMENT 'sku主图图片地址',
                                 `sku_status` tinyint(4) NULL DEFAULT NULL COMMENT '产品状态：正常0，禁用1；商品状态：已下架0，已上架1，作废2',
                                 `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间',
                                 `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架操作人id',
                                 `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                 `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                 `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                 `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                 `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                                 `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                 `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                 `features` text  NULL COMMENT '预留拓展字段',
                                 `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架时间',
                                 `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '第一次上架操作人id',
                                 `unit_id` bigint(20) NULL DEFAULT NULL COMMENT '单位id',
                                 PRIMARY KEY (`sku_id`) USING BTREE,
                                 INDEX `index_itemid`(`item_id`) USING BTREE,
                                 INDEX `index_skucode`(`sku_code`) USING BTREE
) ENGINE = InnoDB comment 'sku主本表';





CREATE TABLE `t_sku_master_language`  (
                                          `sku_lan_id` bigint(20) NOT NULL COMMENT 'sku多语言id',
                                          `sku_id` bigint(20) NOT NULL COMMENT 'skuid',
                                          `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                          `price_extend` text  NULL COMMENT '阶梯批发价数组json格式、币种等信息阶段数量等等、建议采购价',
                                          `sale_property_extend` text  NULL COMMENT '销售属性拓展信息',
                                          `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                          `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                          `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                          `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                          `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                          `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                          `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                          `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                          `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                          `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                          PRIMARY KEY (`sku_lan_id`) USING BTREE,
                                          INDEX `index_itemid`(`item_id`) USING BTREE,
                                          INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'sku主本语言表';





CREATE TABLE `t_spu_base`  (
                               `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
                               `platform` varchar(128)  NULL DEFAULT NULL COMMENT '平台',
                               `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
                               `category_backend_id` bigint(20) NULL DEFAULT NULL COMMENT '后台类目id',
                               `spu_code` varchar(255)  NULL DEFAULT NULL COMMENT 'spu编码',
                               `spu_id` bigint(20) NOT NULL COMMENT '产品id',
                               `country_id` bigint(20) NULL DEFAULT NULL COMMENT '国别',
                               `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                               `type` tinyint(4) NULL DEFAULT 0 COMMENT '产品类型：标准产品0，渠道产品1',
                               `is_mixed` tinyint(4) NULL DEFAULT NULL COMMENT '是否支持混批:不支持0 支持1',
                               `status` tinyint(4) NULL DEFAULT 0 COMMENT '产品状态：正常0，待入库1；',
                               `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                               `first_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '首次上架时间',
                               `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                               `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                               `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                               `version` bigint(20) NULL DEFAULT NULL COMMENT '当前数据行版本',
                               `features` text  NULL COMMENT '预留拓展字段',
                               `first_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '首次上架操作人id',
                               `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                               `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                               `last_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架时间戳',
                               `last_sale_user_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次上架人id',
                               PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB comment 'spu基础表';





CREATE TABLE `t_spu_base_language`  (
                                        `item_lan_id` bigint(20) NOT NULL COMMENT '主键id',
                                        `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                        `title` text  NULL COMMENT '商品标题',
                                        `sub_title` text  NULL COMMENT '商品副标题',
                                        `property_info` text  NULL COMMENT '商品属性信息',
                                        `sale_property_extend` text  NULL COMMENT '商品销售属性拓展JSON结构',
                                        `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                                        `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                        `create_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                                        `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                        `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                        `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                        `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                        `version` bigint(20) NULL DEFAULT 1 COMMENT '当前数据行版本',
                                        `item_type` varchar(128)  NULL DEFAULT NULL COMMENT '商品型号',
                                        `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                        `features` text  NULL COMMENT '扩展结构存储 json 结构',
                                        PRIMARY KEY (`item_lan_id`) USING BTREE,
                                        INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'spu基础语言表';





CREATE TABLE `t_spu_desc`  (
                               `item_desc_id` bigint(20) NOT NULL COMMENT '商品详情表id',
                               `item_id` bigint(20) NOT NULL COMMENT '商品id',
                               `item_desc` text  NULL COMMENT '商品详情描述',
                               `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                               `preferred_language` varchar(100)  NULL DEFAULT NULL COMMENT '语言地区:zh_CN 中国大陆',
                               `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                               `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                               `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                               `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                               `features` text  NULL COMMENT '预留后期的拓展字段',
                               `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                               `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                               PRIMARY KEY (`item_desc_id`) USING BTREE,
                               INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'spu_desc表';





CREATE TABLE `t_spu_extend`  (
                                 `item_ext_id` bigint(20) NOT NULL COMMENT '商品拓展表id',
                                 `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
                                 `extend_key` int(11) NULL DEFAULT NULL COMMENT '拓展类型',
                                 `extend_info` text  NULL COMMENT '具体拓展信息json结构',
                                 `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                 `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                 `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                 `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                 `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                 `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                 `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'cspuid',
                                 `preferred_language` varchar(64)  NULL DEFAULT NULL COMMENT '语言地区：zh_CN 中国大陆',
                                 `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                 PRIMARY KEY (`item_ext_id`) USING BTREE,
                                 INDEX `index_itemid`(`item_id`) USING BTREE,
                                 INDEX `index_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB comment 'spu拓展表';





CREATE TABLE `t_spu_image`  (
                                `item_image_id` bigint(20) NOT NULL COMMENT '商品图片id',
                                `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
                                `image_url` varchar(200)  NULL DEFAULT NULL COMMENT '图片地址',
                                `is_master` tinyint(4) NULL DEFAULT NULL COMMENT '是否主图',
                                `image_order` int(11) NULL DEFAULT NULL COMMENT '图片顺序',
                                `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除:否0 是1',
                                `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` bigint(20) NULL DEFAULT NULL COMMENT '用户更新时间',
                                `version` bigint(20) NULL DEFAULT 0 COMMENT '当前数据行版本',
                                `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                `type` int(3) NULL DEFAULT NULL COMMENT '图片类型',
                                PRIMARY KEY (`item_image_id`) USING BTREE,
                                INDEX `index_itemid`(`item_id`) USING BTREE
) ENGINE = InnoDB comment 'spu图片表';
