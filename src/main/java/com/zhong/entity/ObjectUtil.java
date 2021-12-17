package com.zhong.entity;

import org.apache.lucene.util.RamUsageEstimator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/10 11:16
 */
public class ObjectUtil {

    public static void main(String[] args) {
        Dog dog = Dog.builder().id(2).state(StateEnum.OPEN).build();
        //计算指定对象本身在堆空间的大小，单位字节
        long shallowSize = RamUsageEstimator.shallowSizeOf(dog);
        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        long size = RamUsageEstimator.sizeOf(dog);
        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        String humanSize = RamUsageEstimator.humanSizeOf(dog);

        System.out.println(shallowSize +"-"+ size +"-"+ humanSize);
        System.out.println(shallowSize);
        System.out.println(size);
        System.out.println(humanSize);

    }


    /**
    public static Tuple3<Long, Long, String> size(Object o) {
        //计算指定对象本身在堆空间的大小，单位字节
        long shallowSize = RamUsageEstimator.shallowSizeOf(o);
        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        long size = RamUsageEstimator.sizeOf(o);
        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        String humanSize = RamUsageEstimator.humanSizeOf(o);
        return Tuple3.of(shallowSize, size, humanSize);
    }

	public static void main(String[] args) {
		Tuple3<Long, Long, String> size = size(Tuple3.of(1, 1, ""));
		System.out.println(size);
	}

    public static Long multiSize(List<Tuple2<Object, Long>> object2Size) {
        AtomicLong memAll = new AtomicLong();
        object2Size.forEach(t -> {
            Object object = t.getT1();
            Long size = t.getT2();
            if (object == null
                    || size == null
                    || size == 0) {
                return;
            }
            long l = RamUsageEstimator.shallowSizeOf(object);
            long mem = l * size;
            memAll.addAndGet(mem);
        });
        return memAll.get();
    }
     */
}
