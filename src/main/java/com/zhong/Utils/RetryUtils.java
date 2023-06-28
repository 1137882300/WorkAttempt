package com.zhong.Utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author: juzi
 * @date: 2023/3/28
 * @description: 重试工具
 */
@Slf4j
public class RetryUtils {
    public interface Supplier {
        void execute();
    }

    //    无返回值
    public static void executeWithRetry(Supplier supplier, int maxRetryNum) throws Exception {
        int retryNum = 0;
        while (retryNum <= maxRetryNum) {
            try {
                supplier.execute();
                return;//成功直接返回
            } catch (Exception e) {
                retryNum++;
            }
        }
        throw new Exception("重试失败");
    }

//    有返回值

    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit    重试次数
     * @param retryCallable 重试回调
     * @param <V>           泛型
     * @return V 结果
     */
    public static <V extends ResultCheck> V retryOnException(int retryLimit,
                                                             Callable<V> retryCallable) {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                log.warn("retry on " + (i + 1) + " times v = " + (v == null ? null : v.getJson()), e);
            }
            if (null != v && v.matching()) break;
            log.error("retry on " + (i + 1) + " times but not matching v = " + (v == null ? null : v.getJson()));
        }
        return v;
    }


    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit    重试次数
     * @param sleepMillis   每次重试之后休眠的时间
     * @param retryCallable 重试回调
     * @param <V>           泛型
     * @return V 结果
     * @throws java.lang.InterruptedException 线程异常
     */
    public static <V extends ResultCheck> V retryOnException(int retryLimit, long sleepMillis,
                                                             Callable<V> retryCallable) throws java.lang.InterruptedException {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                log.warn("retry on " + (i + 1) + " times v = " + (v == null ? null : v.getJson()), e);
            }
            if (null != v && v.matching()) {
                break;
            }
            log.error("retry on " + (i + 1) + " times but not matching v = " + (v == null ? null : v.getJson()));
            if (sleepMillis > 0) {
                Thread.sleep(sleepMillis);
            }
        }
        return v;
    }

    /**
     * 回调结果检查
     */
    public interface ResultCheck {
        /**
         * 是否匹配
         *
         * @return 匹配结果
         */
        boolean matching();

        /**
         * 获取 JSON
         *
         * @return json
         */
        String getJson();
    }

}