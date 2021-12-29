package com.zhong.cache;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/28 16:32
 */
public interface Cache {

    /**
     * 存储数据
     */
    void putObject(Object key,Object value);

    /**
     * 基于key获取数据
     */
    Object getObject(Object key);

    /**
     * 移除指定key的数据
     */
    Object removeObject(Object key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 获取缓存中数据的个数
     */
    int size();

}
