
-- DDL:

CREATE TABLE `t_stock` (
                           `stock_id` bigint(20) NOT NULL COMMENT '库存纪录id',
                           `item_id` bigint(20) NOT NULL COMMENT '商品id',
                           `sku_id` bigint(20) NOT NULL COMMENT '商品最小库存单元id',
                           `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
                           `stock_num` bigint(20) NOT NULL COMMENT '剩余库存数量(包含锁定数量)',
                           `locked_stock_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '锁定库存数量,任何时候不得大于剩余库存数量',
                           `create_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                           `update_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                           `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                           `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                           `features` text  COMMENT '预留后期的拓展字段,json格式',
                           PRIMARY KEY (`stock_id`) USING BTREE,
                           UNIQUE KEY `idx_stock` (`sku_id`) USING BTREE,
                           KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB COMMENT '基础库存表';


CREATE TABLE `t_stock_sales_volume` (
                                        `sales_volume_id` bigint(20) NOT NULL COMMENT '库存销量纪录id',
                                        `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                        `sku_id` bigint(20) NOT NULL COMMENT '商品最小库存单元id',
                                        `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
                                        `sales_volume_type` tinyint(4) DEFAULT '0' COMMENT '销量类型:总销量0 年销量1 月销量2 日销量3 30天销量4,90天销量5',
                                        `record_time` int(11) DEFAULT NULL COMMENT '类型为三个月销量时,表示从record_time往前推 三个月的销量',
                                        `sales_volume` bigint(20) NOT NULL COMMENT '销售数量',
                                        `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                        `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间,unix时间戳,单位,毫秒',
                                        `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                        `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                        `features` text  COMMENT '预留后期的拓展字段,json格式',
                                        PRIMARY KEY (`sales_volume_id`) USING BTREE,
                                        UNIQUE KEY `idx_t_stock_sales_volume_UN` (`sku_id`,`record_time`,`sales_volume_type`) USING BTREE,
                                        KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB COMMENT '销量表';


CREATE TABLE `t_stock_flow` (
                                `stock_flow_id` bigint(20) NOT NULL comment '主键id',
                                `item_id` bigint(20) NOT NULL COMMENT '商品id',
                                `sku_id` bigint(20) NOT NULL COMMENT '商品最小库存单元id',
                                `shop_id` bigint(20) DEFAULT 0 COMMENT '店铺id',
                                `flow_type` tinyint(4) NOT NULL COMMENT '流水类型:锁定库存0 释放库存1 销售出库2 调整入库3 调整出库4',
                                `channel_mapping_id` bigint(20) DEFAULT 0 comment '渠道映射id',
                                `adjust_num` bigint(20) DEFAULT NULL COMMENT '调整库存数量 补货:+ 出货:-',
                                `adjusted_num` bigint(20) DEFAULT NULL COMMENT '调整后库存数量(包含锁定数量)',
                                `remark` varchar(100) DEFAULT NULL COMMENT '操作备注',
                                `operator_id` bigint(20) NOT NULL COMMENT '操作人',
                                `create_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                `update_time` bigint(20) NOT NULL COMMENT '更新时间,unix时间戳,单位,毫秒',
                                `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                `delete_time` bigint(20) DEFAULT 0 COMMENT '删除时间',
                                `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                `features` text DEFAULT NULL COMMENT '预留后期的拓展字段,json格式',
                                `documents_id` varchar(64) DEFAULT "0" COMMENT '单据id',
                                `sub_documents_id` varchar(64) DEFAULT "0" COMMENT '子单据id',
                                PRIMARY KEY (`stock_flow_id`) USING BTREE,
                                UNIQUE KEY `idx_stock_flow` (`sku_id`, `channel_mapping_id`,`documents_id`,`flow_type`,`sub_documents_id`,`is_deleted`,`delete_time`) USING BTREE
) ENGINE=InnoDB COMMENT '库存流水表';


CREATE TABLE `t_stock_sales_volume_flow` (
                                             `sales_volume_flow_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存销量流水id',
                                             `order_line_id` varchar(50)  DEFAULT NULL COMMENT '订单id',
                                             `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku id',
                                             `sales_type` varchar(10)  DEFAULT '1' COMMENT '销量变动类型:销量减少2 销量增加1',
                                             `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                             `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间,unix时间戳,单位,毫秒',
                                             `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                             `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                             `features` text DEFAULT NULL COMMENT '预留后期的拓展字段,json格式',
                                             PRIMARY KEY (`sales_volume_flow_id`) USING BTREE,
                                             UNIQUE KEY `idx_t_stock_sales_volume_flow_UN` (`order_line_id`,`sku_id`,`sales_type`) USING BTREE
) ENGINE=InnoDB COMMENT '销量流水表';


CREATE TABLE `t_stock_exists` (
                                  `id` bigint(20) NOT NULL comment '主键id',
                                  `item_id` bigint(20) NOT NULL comment '商品id',
                                  `shop_id` bigint(20) NOT NULL comment '店铺id',
                                  `sku_id` bigint(20) NOT NULL comment 'skuid',
                                  `stock_exists` tinyint(4) DEFAULT 0 COMMENT '是否存在可售库存默认存在0,不存在1',
                                  `create_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                  `update_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                  `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                  `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                  `features` text DEFAULT NULL COMMENT '预留后期的拓展字段,json格式',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE KEY `idx_stock_exists` (`sku_id`) USING BTREE
) ENGINE=InnoDB COMMENT '库存存在状态表';


CREATE TABLE `t_channel_mapping` (
                                     `id` bigint(20) NOT NULL comment '主键id',
                                     `sc_batch_id` varchar(100) DEFAULT "0" COMMENT '批次id',
                                     `sc_channel_group_id` varchar(25) DEFAULT "0" COMMENT '渠道组id',
                                     `sc_channel_id` varchar(25) DEFAULT "0" COMMENT '渠道id',
                                     `sc_channel_type` tinyint(4) default 0 COMMENT '映射渠道类型(渠道0,渠道组1,批次2)',
                                     `promotion_id` varchar(50) DEFAULT "0" COMMENT '推广活动id',
                                     `create_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                     `update_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                     `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                     `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                     `features` text DEFAULT NULL COMMENT '预留后期的拓展字段,json格式',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE KEY `idx_channel_mapping` (`sc_batch_id`, `sc_channel_group_id`,`sc_channel_id`, `sc_channel_type`, `promotion_id`) USING BTREE,
                                     KEY `idx_promotion_UN` (`promotion_id`) USING BTREE
) ENGINE=InnoDB COMMENT '渠道映射表';

CREATE TABLE `t_channel_stock` (
                                   `id` bigint(20) NOT NULL comment '主键id',
                                   `channel_mapping_id` bigint(20) NOT NULL comment '渠道映射id',
                                   `stock_num` bigint(20) NOT NULL COMMENT '剩余库存数量(包含锁定数量)',
                                   `locked_stock_num` bigint(20) NOT NULL DEFAULT 0 COMMENT '锁定库存数量,任何时候不得大于剩余库存数量',
                                   `create_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                   `update_time` bigint(20) NOT NULL COMMENT '创建时间,unix时间戳,单位,毫秒',
                                   `is_deleted` tinyint(4) DEFAULT 0 COMMENT '是否删除,否0,是1',
                                   `version` bigint(20) DEFAULT 0 COMMENT '版本号',
                                   `features` text DEFAULT NULL COMMENT '预留后期的拓展字段,json格式',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE KEY `idx_channel_stock` (`channel_mapping_id`) USING BTREE
) ENGINE=InnoDB COMMENT '渠道库存表';


-- id初始化：

INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_stock', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', NULL, '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_stock_exists', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', NULL, '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_stock_flow', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', NULL, '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_stock_sales_volume', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', '2022-03-17 00:19:21', '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_stock_sales_volume_flow', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', '2022-03-14 23:34:24', '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_channel_stock', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:58', NULL, '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('t_channel_mapping', 4000000000, 4000000000, 10, NULL, '2022-03-14 20:19:59', NULL, '', 'league');
INSERT INTO `t_segment` (`biz_tag`, `init_val`, `current_val`, `step`, `reset_time`, `create_time`, `modify_time`, `biz_describe`, `tenant_id`) VALUES ('save_stock_opid', 4000000000, 4000000000, 10, NULL, '2021-11-01 11:36:59', '2022-03-16 16:32:20', '', 'league');
