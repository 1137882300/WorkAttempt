

CREATE TABLE `t_bbmall_cn_user_mapping`  (
                                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                             `biz_type` int(20) NOT NULL DEFAULT 0 COMMENT '场景：1：行云卖家内部账号 2：租户映射 ',
                                             `source` varchar(64)  NOT NULL DEFAULT '' COMMENT '映射账号来源',
                                             `tenant_id` int(20) NOT NULL DEFAULT 0 COMMENT '租户id',
                                             `source_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '来源用户id',
                                             `target_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '系统中用户id',
                                             `is_delete` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = 'user_mapping表' ;





CREATE TABLE `t_global_relation`  (
                                      `id` bigint(20) NOT NULL COMMENT '主键',
                                      `type` varchar(255)  NULL DEFAULT NULL COMMENT '关联类型',
                                      `master` varchar(255)  NULL DEFAULT NULL COMMENT '关联主体',
                                      `slave` varchar(255)  NULL DEFAULT NULL COMMENT '关联从属',
                                      `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
                                      `platform` varchar(100)  NULL DEFAULT NULL COMMENT '平台',
                                      `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                      `modified_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
                                      `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                      `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
                                      `system_update_time` bigint(20) NULL DEFAULT NULL COMMENT '系统更新时间',
                                      `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除，否0，是1',
                                      `version` bigint(20) NULL DEFAULT 1 COMMENT '版本号',
                                      `features` text  NULL COMMENT '预留后期的拓展字段，json格式',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `t_global_relation_master_IDX`(`master`) USING BTREE,
                                      INDEX `t_global_relation_slave_IDX`(`slave`) USING BTREE
) ENGINE = InnoDB  COMMENT = '关联关系表' ;





CREATE TABLE `t_segment`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `biz_tag` varchar(100)  NULL DEFAULT '' COMMENT '业务标识',
                              `init_val` bigint(20) NULL DEFAULT NULL COMMENT '初始值',
                              `current_val` bigint(20) NULL DEFAULT NULL COMMENT '当前值',
                              `step` int(11) NULL DEFAULT NULL COMMENT '号段步长',
                              `reset_time` varchar(20)  NULL DEFAULT NULL COMMENT '重置的时间',
                              `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `modify_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                              `biz_describe` varchar(20)  NULL DEFAULT '' COMMENT '业务模块描述',
                              `tenant_id` varchar(55)  NOT NULL DEFAULT 'bbm' COMMENT '租户ID 默认bbm',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `biz_tag`(`biz_tag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 comment 'id表';





CREATE TABLE `t_shop_unique`  (
                                  `id` bigint(20) NOT NULL COMMENT '主键id',
                                  `biz_id` bigint(20) NULL DEFAULT NULL COMMENT '业务id',
                                  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
                                  `type` tinyint(4) NULL DEFAULT NULL COMMENT '业务类型 0 spu code, 1 sku code',
                                  `biz_value` varchar(150)  NULL DEFAULT NULL COMMENT '业务值',
                                  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                                  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `biz_tag`(`biz_value`, `shop_id`, `type`) USING BTREE,
                                  INDEX `index_bizid`(`biz_id`) USING BTREE
) ENGINE = InnoDB comment '唯一标识表';
