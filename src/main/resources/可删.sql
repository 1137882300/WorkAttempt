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









