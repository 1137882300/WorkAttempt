update t_tag_relation
SET valid_time_start = case when id = 945 and version = 13 then 0 else null end,
    valid_time_end   = case when id = 945 and version = 13 then 2652777512717 end,
    features         = case when id = 945 and version = 13 then '{"huigun":"true"}' end,
    modified_id      = case when id = 945 and version = 13 then -10 end,
    update_time      = case when id = 945 and version = 13 then 1655792652360 end,
    platform         = case when id = 945 and version = 13 then 'TT' end,
    shop_id          = case when id = 945 and version = 13 then null end,
    tenant_id        = case when id = 945 and version = 13 then null end,
    is_deleted       = case when id = 945 and version = 13 then 0 end,
    version          = IFNULL(version, 0) + 1
WHERE id in (945)


update t_tag_relation
SET valid_time_start = case when id = 945 and version = 13 then 0 end, 945(Long), 11(Long), 0(Long),
    valid_time_end = case when id = 945 and version = 13 then 2652777512717
end
,         945(Long), 11(Long), 2652777512717(Long),
                          features = case when id = 945 and version = 13 then '{"huigun":"true"}'
end
,           945(Long), 11(Long), {"huigun":"true"}(String),
                          modified_id = case when id = 945 and version = 13 then -10
end
,     945(Long), 11(Long), -10(Long),
                          update_time = case when id = 945 and version = 13 then 1655792652360
end
,    945(Long), 11(Long), 1655792652360(Long),
                          platform = case when id = 945 and version = 13 then 'TT'
end
,       945(Long), 11(Long), TT(String),
                          shop_id = case when id = 945 and version = 13 then null
end
,       945(Long), 11(Long), null,
                          tenant_id = case when id = 945 and version = 13 then null
end
,       945(Long), 11(Long), null,
                          is_deleted = case when id = 945 and version = 13 then 0
end
,      945(Long), 11(Long), 0(Integer),
                          version = IFNULL(version,0) + 1 WHERE id in ( 945 )              945(Long)



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

update t_item_base_master
set brand_id           = case item_id
                             when 2000008250 then 11597
                             when 2000008679 then 11597
    end,
    version            = version + 1,
    system_update_time = 1656470658000
where item_id in (2000008250, 2000008679)





