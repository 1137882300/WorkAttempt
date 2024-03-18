//package com.zhong;
//
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.FileUtil;
//import com.alibaba.fastjson.JSON;
//
//
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.FileUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.youxiake.entity.m.user.response.AnnualMeetingUserJsonVO;
//import com.youxiake.service.shake.ShakeConfig;
//import com.youxiake.service.shake.impl.ShakeServiceImpl;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.RepeatedTest;
//import org.mockito.stubbing.Answer;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.startsWith;
//import static org.mockito.Mockito.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author: juzi
// * @date: 2024/3/14
// * @desc:
// */
//public class TestBiz {
//
//
//    private static RedisTemplate redisTemplate;
//
//    private static ShakeConfig shakeConfig = new ShakeConfig();
//
//    private static Map<String, Object> prizeMap = new HashMap<>();
//
//    private static Map<String, Object> countMap = new HashMap<>();
//
//    private static Map<String, Object> prizeStock = new HashMap<>();
//
//    private static Map<String, Integer> prizeCount = new HashMap<>();
//
//    @BeforeEach
//    public void beforeEach() {
//        prizeMap.clear();
//        countMap.clear();
//        prizeStock.clear();
//
//        for (Map.Entry<String, ShakeConfig.PrizeEntity> prizeEntityEntry : shakeConfig.getPrizeMap().entrySet()) {
//            prizeStock.put("shake:stock:stock_" + prizeEntityEntry.getKey(), prizeEntityEntry.getValue().getStock());
//        }
//    }
//
//    @BeforeAll
//    public static void setUp() {
//        String prizeJson = FileUtil.readUtf8String("/Users/j2ephyr/IdeaProjects/youxiake-app-service/prizes.json");
//        shakeConfig.setPrizeMap(JSON.parseObject(prizeJson, new TypeReference<Map<String, ShakeConfig.PrizeEntity>>() {
//        }));
//        String json = FileUtil.readUtf8String("/Users/j2ephyr/IdeaProjects/youxiake-app-service/users.json");
//        ValueOperations valueOperations = mock(ValueOperations.class);
//        redisTemplate = mock(RedisTemplate.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//        when(redisTemplate.opsForValue().get("app:annual_meeting_2024:sign_in")).thenReturn(JSON.parseObject(json, AnnualMeetingUserJsonVO.class));
//        doAnswer(invocationOnMock -> {
//            String key = (String) invocationOnMock.getArguments()[0];
//            ShakeServiceImpl.MyPrizeEntity value = (ShakeServiceImpl.MyPrizeEntity) invocationOnMock.getArguments()[1];
//            prizeMap.put(key, value);
//            return null;
//        }).when(valueOperations).set(startsWith("shake:user:prize:"), any(ShakeServiceImpl.MyPrizeEntity.class));
//        when(redisTemplate.opsForValue().get(startsWith("shake:user:prize:")))
//                .thenAnswer((Answer<ShakeServiceImpl.MyPrizeEntity>) invocation ->
//                        (ShakeServiceImpl.MyPrizeEntity) prizeMap.get((String) invocation.getArguments()[0]));
//        when(redisTemplate.opsForValue().get(startsWith("shake:user:count:")))
//                .thenAnswer((Answer<Integer>) invocation -> (Integer) countMap.get((String) invocation.getArguments()[0]));
//        when(redisTemplate.opsForValue().get(startsWith("shake:stock:stock_")))
//                .thenAnswer((Answer<Integer>) invocation -> (Integer) prizeStock.get((String) invocation.getArguments()[0]));
//        when(redisTemplate.opsForValue().decrement(startsWith("shake:stock:stock_")))
//                .thenAnswer((Answer<Long>) invocation -> {
//                    String key = (String) invocation.getArguments()[0];
//                    Integer stock = (Integer) prizeStock.get(key);
//                    if (stock > 0) {
//                        prizeStock.put(key, stock - 1);
//                        return Convert.toLong(stock - 1);
//                    }
//                    return 0L;
//                });
//    }
//
//    @RepeatedTest(500)
//    public void initTest() {
//        ShakeServiceImpl shakeService = new ShakeServiceImpl(shakeConfig, redisTemplate, null);
//        shakeService.intNextPrize();
//        ShakeServiceImpl.MyPrizeEntity o = (ShakeServiceImpl.MyPrizeEntity) prizeMap.get("shake:user:prize:11236136");
//        if (o != null) {
//            prizeCount.put(o.getName(), prizeCount.getOrDefault(o.getName(), 0) + 1);
//        }
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        System.out.println(prizeCount);
//    }
//
//
//}
