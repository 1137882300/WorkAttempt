package com.zhong.cache;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/28 16:38
 */
public class LoggingCache implements Cache{

    /**对这个cache进行命中率的记录*/
    private Cache cache;
    /**请求次数*/
    private int requests;
    /**命中的次数*/
    private int hints;
    public LoggingCache(Cache cache){
        this.cache=cache;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.putObject(key,value);
    }
    @Override
    public Object getObject(Object key) {
        //1.记录请求次数
        requests++;
        //2.从cache获取数据
        Object object = cache.getObject(key);
        if(object!=null){
            //计算命中率
            hints++;
            System.out.println("hits/requests is "+hints*1.0/requests);
        }
        return object;
    }
    @Override
    public Object removeObject(Object key) {
        return cache.removeObject(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
    @Override
    public int size() {
        return cache.size();
    }

    public static void main(String[] args) {
        LoggingCache loggingCache = new LoggingCache(new DefaultCache());
        loggingCache.putObject("A",100);
        loggingCache.putObject("B",200);
        loggingCache.putObject("C",300);
        loggingCache.getObject("D");
        loggingCache.getObject("A");
        loggingCache.getObject("A");
    }




}
