package com.zhong.Utils;

import com.google.common.collect.Lists;
import com.zhong.entity.AreaInfoPO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: juzi
 * @date: 2023/5/6
 * @desc:
 */
public class FunctionUtil {

    /**
     * @author juzi
     * @date 2023/5/6 上午 10:30
     * @description 按照 fields 去重，同时满足fields,相当于联合去重
     */
    @SafeVarargs
    public static <T> List<T> distinctByField(Collection<T> collection, Function<T, ?>... mappers) {
        if (collection == null || mappers == null) {
            return Collections.emptyList();
        }
        return collection.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(c -> Arrays.stream(mappers)
                        .map(m -> {
                            Object o = m.apply(c);
                            if (o == null) {
                                return null;
                            } else {
                                return o.toString();
                            }
                        }).filter(Objects::nonNull).collect(Collectors.joining())
                ))),
                ArrayList::new));
    }

    public static void main(String[] args) {
        List<AreaInfoPO> list = Lists.newArrayList(
                AreaInfoPO.builder().id(12L).countryId(12L).parentId(76L).build(),
                AreaInfoPO.builder().id(18L).countryId(12L).parentId(76L).build(),
                AreaInfoPO.builder().id(20L).countryId(13L).parentId(77L).build()
        );
        List<AreaInfoPO> distinctList = distinctByField(list, AreaInfoPO::getCountryId, AreaInfoPO::getParentId);
        distinctList.forEach(System.out::println);
    }

}
