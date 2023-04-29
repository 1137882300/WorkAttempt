package com.zhong.Utils;

/**
 * @author: juzi
 * @date: 2023/3/28
 * @description: 重试工具
 */
public class RetryUtils {
    public interface Supplier{
        void execute();
    }

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

}