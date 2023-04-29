package com.zhong.designPatterns.strategyMode.v2;

import lombok.SneakyThrows;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: juzi
 * @date: 2023/3/15
 */
public class Test {

    private static final Map<String, ConditionMethod<String>> MAP = new HashMap<>(5);
    private static final Map<String, ConditionMethodFunction<String, String>> MAP2 = new HashMap<>(5);

    static {
        MAP.put("type1", () -> "感谢你长这么帅还看我的文章");
        MAP.put("type2", () -> "感谢你长这么美还看我的文章");

    }


    public String doEasySomething(String type) {
        return MAP.getOrDefault(type, () -> "不知道你啥情况").invoke();
    }


    @SneakyThrows
    public static void main(String[] args) {
        Test test = Test.class.newInstance();
        String type1 = test.doEasySomething("type1");
        System.out.println(type1);

    }

}
