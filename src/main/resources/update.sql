-- case when + 条件表达式
update t_tag_relation
SET valid_time_start = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    valid_time_end   = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    features         = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    modified_id      = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    update_time      = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    platform         = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    is_deleted       = case when id = ? and version = ? then ? when id = ? and version = ? then ? end,
    version          = IFNULL(version, 0) + 1
WHERE id in (?, ?)
;

-- case 字段 when + 字段的值
update t_spu_base
set brand_id           = case spu_id
                             when 2000002291 then 11597
                             when 2000002292 then 11540
                             when 2000002293 then 11540
                             when 2000002294 then 10355
                             when 2000002295 then 11540
                             when 2000002296 then 11540
    end,
    version            = version + 1,
    system_update_time = 1656470658000
where spu_id in (2000002291, 2000002292, 2000002293, 2000002294, 2000002295, 2000002296)
;
update t_item_base_master
set brand_id           = case item_id
                             when 2000008250 then 11597
                             when 2000008679 then 11597
    end,
    version            = version + 1,
    system_update_time = 1656470658000
where item_id in (2000008250, 2000008679)
;


update t_item_base_info_copy_language
set title              = "xxx",
    version            = version + 1,
    system_update_time = 13
where item_id = 123
  and preferred_language = "zh_CN"