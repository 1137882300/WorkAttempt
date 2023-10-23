//package com.zhong.mask;
//
//import com.google.common.collect.Collections2;
//import com.google.common.collect.Maps;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.function.Function;
//
///**
// * @author: juzi
// * @date: 2023/10/23
// * @desc: 脱敏
// */
//public class MaskEntryTransformer implements Maps.EntryTransformer<Object, Object, Object> {
//    private static final Maps.EntryTransformer<Object, Object, Object> MASK_ENTRY_TRANSFORMER = new MaskEntryTransformer();
//
//    private MaskEntryTransformer() {
//
//    }
//
//    public static Maps.EntryTransformer<Object, Object, Object> getInstance() {
//        return MASK_ENTRY_TRANSFORMER;
//    }
//
//    @Override
//    public Object transformEntry(Object objectKey, Object value) {
//        if (value == null) {
//            return null;
//        }
//        if (value instanceof Map) {
//            Map valueMap = (Map) value;
//            return Maps.transformEntries(valueMap, this);
//        }
//        final Maps.EntryTransformer<Object, Object, Object> thisFinalMaskEntryTransformer = this;
//        if (value instanceof Collection) {
//            Collection valueCollection = (Collection) value;
//            if (valueCollection.isEmpty()) {
//                return valueCollection;
//            }
//            return Collections2.transform(valueCollection, new Function<Object, Object>() {
//                @Override
//                public Object apply(Object input) {
//                    if (input == null) {
//                        return null;
//                    }
//                    if (input instanceof Map) {
//                        Map inputValueMap = (Map) input;
//                        return Maps.transformEntries(inputValueMap, thisFinalMaskEntryTransformer);
//                    }
//                    if (input instanceof Collection) {
//                        Collection inputValueCollection = (Collection) input;
//                        return Collections2.transform(inputValueCollection, this);
//                    }
//                    if (!(objectKey instanceof String)) {
//                        return input;
//                    }
//                    final String key = (String) objectKey;
//                    return transformPrimitiveType(key, input);
//                }
//            });
//        }
//        if (!(objectKey instanceof String)) {
//            return value;
//        }
//        final String key = (String) objectKey;
//        return transformPrimitiveType(key, value);
//    }
//
//
//    /**
//     * 按照脱敏规则脱敏基本数据类型
//     *
//     * @param key
//     * @param value
//     * @return
//     */
//    private Object transformPrimitiveType(final String key, final Object value) {
//        // ...
//        return null;
//    }
//}