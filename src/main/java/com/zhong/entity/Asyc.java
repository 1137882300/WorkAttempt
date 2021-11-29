package com.zhong.entity;

import com.google.common.collect.Maps;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/24 18:54
 */
public class Asyc {
/**
    public Boolean addPackageIdCategory() {
        List<Long> allEnterpriseId = storeRpcService.getAllEnterpriseId();
        List<PackageTenantMapping> mappings = packageTenantMappingRpService.getByTenantIds(null, allEnterpriseId);
        Map<Long, Long> tenantPackageMap = mappings.stream().collect(Collectors.toMap(PackageTenantMapping::getTenantId,PackageTenantMapping::getPackageId, (s, e) -> s));
        List<StoreAllInfoVO> storeAllInfo = storeRpcService.getStoreByEnterpriseId(allEnterpriseId);
        //学院与课程包id的关系 -> map
        Map<Long, Long> map = Maps.newHashMap();
        storeAllInfo.forEach(s ->{
            if (tenantPackageMap.containsKey(s.getEnterpriseId())){
                map.put(s.getId(),tenantPackageMap.get(s.getEnterpriseId()));
            }
        });
        List<CourseSchedule> scheduleList = courseScheduleRpService.getByStoreIds(storeAllInfo.stream().map(StoreAllInfoVO::getId).collect(Collectors.toList()));
        Map<Long, List<CourseSchedule>> tenantMap = scheduleList.stream().collect(Collectors.groupingBy(CourseSchedule::getTenantId));
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 50, 2,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(storeAllInfo.size()));
        CountDownLatch countDownLatch = new CountDownLatch(tenantMap.size());
        tenantMap.forEach((k,v) ->{
            poolExecutor.execute(() -> {
                if(map.containsKey(k)){
                    List<CourseSchedule> list = tenantMap.get(k);
                    list.forEach(s ->{
                        s.setUpdated(new Date());
                        s.setCategoryPath(map.get(s.getTenantId())+"/"+s.getCategoryPath());
                    });
                    courseScheduleRpService.updateBatchById(list);
                    log.info("学院" + k + "执行完成");
                }
                countDownLatch.countDown();
            });
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("查询异常", e);
        }
        log.info("执行完毕");
        return true;
    }
 */
}
