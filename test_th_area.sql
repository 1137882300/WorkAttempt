-- cspu的：
-- cspuID：20000039827  code：4547691689696  type：'CSPU_INTERNATIONAL'        2000000494
-- cspuID：20000043125  code：4547691689778	 type：'CSPU_INTERNATIONAL'          2000000494
-- cspuID：20000043144 	code：4547691689696  type：'CSPU_INTERNATIONAL'           2000000494


-- sku的：
-- skuID：29827  code：4547691689696   type = 'SKU_INTERNATIONAL'     itemID：13633
-- skuID：33144  code：4547691689696   type = 'SKU_INTERNATIONAL'        itemID：15480
-- skuID：20000007037  code：4547691784865  type = 'SKU_INTERNATIONAL'        itemID：2000005637
-- skuID：20000007167  code：4547691239099  type = 'SKU_INTERNATIONAL'    itemID：2000005739


insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"shopId":"2000000494"}', '2000001250', 0, '20000039827', '-1', NULL, '4547691689696',
        0, NULL, 'CSPU_INTERNATIONAL', '1663745141000', '1');
insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"shopId":"2000000494"}', '2000001249', 0, '20000043125', '-1', NULL, '4547691689778',
        0, NULL, 'CSPU_INTERNATIONAL', '1663745141000', '1');
insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"shopId":"2000000494"}', '2000001248', 0, '20000043144', '-1', NULL, '4547691689696',
        0, NULL, 'CSPU_INTERNATIONAL', '1663745141000', '1');

insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"itemId":"13633","shopId":"2000000494"}', '2000001247', 0, '29827', '-1', NULL,
        '4547691689696', 0, NULL, 'SKU_INTERNATIONAL', '1663745141000', '1');
insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"itemId":"15480","shopId":"2000000494"}', '2000001246', 0, '33144', '-1', NULL,
        '4547691689696', 0, NULL, 'SKU_INTERNATIONAL', '1663745141000', '1');
insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"itemId":"2000005637","shopId":"2000000494"}', '2000001245', 0, '20000007037', '-1',
        NULL, '4547691784865', 0, NULL, 'SKU_INTERNATIONAL', '1663745141000', '1');
insert into `t_global_relation` (`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`,
                                 `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`)
values ('1663745141000', '-1', '{"itemId":"2000005739","shopId":"2000000494"}', '2000001244', 0, '20000007167', '-1',
        NULL, '4547691239099', 0, NULL, 'SKU_INTERNATIONAL', '1663745141000', '1');





















