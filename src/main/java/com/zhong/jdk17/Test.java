package com.zhong.jdk17;


import lombok.SneakyThrows;
import org.apache.commons.math3.geometry.spherical.twod.Circle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author: juzi
 * @date: 2024/1/19
 * @desc:
 */
public class Test {


    @SneakyThrows
    public static void main(String[] args) {

        // JDK8
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach(n -> System.out.println(n));

        // JDK17
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        list2.forEach((var n) -> System.out.println(n));


        Object obj = new Object();
        obj = 1;
        // JDK8
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println(str.toUpperCase());
        } else {
            System.out.println(obj);
        }

        // JDK17
        if (obj instanceof String str) {
            System.out.println(str.toUpperCase());
        } else {
            System.out.println(obj);
        }

        if (obj instanceof Integer num) {
            System.out.println(num);
        }


        // JDK8
        List<Integer> list3 = Arrays.asList(1, 2, 3, 4, 5);
        int sum = list3.stream().filter(n -> n % 2 == 0).mapToInt(Integer::intValue).sum();

        // JDK17
        List<Integer> list4 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> evenList = list4.stream().filter(n -> n % 2 == 0).toList();
//        Set<Integer> oddSet = list4.stream().filter(n -> n % 2 != 0).toSet();


        // JDK8
        String dayOfWeek = "MON";
        switch (dayOfWeek) {
            case "MON":
            case "TUE":
            case "WED":
            case "THU":
            case "FRI":
                System.out.println("Weekday");
                break;
            case "SAT":
            case "SUN":
                System.out.println("Weekend");
                break;
            default:
                System.out.println("Invalid day");
        }

        // JDK17
        String dayOfWeek2 = "MON";
        String dayType = switch (dayOfWeek2) {
            case "MON", "TUE", "WED", "THU", "FRI" -> "Weekday";
            case "SAT", "SUN" -> "Weekend";
            default -> {
                System.out.println("Invalid day");
                //用  yield  返回一个值作为switch语句的返回值
                yield "Invalid day";
            }
        };

        Object obj2 = "hello";

        switch (obj2) {
            case String s && s.length() > 5 -> System.out.println("长字符串");
            case String s -> System.out.println("短字符串");
            case Integer i -> System.out.println("整型数");
            default -> System.out.println("不支持的类型");
        }


        // JDK8
        // 使用第三方HTTP库

        // JDK17
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com/"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


        //使用 text blocks 可以更容易地创建包含多行文本的字符串。三个双引号
        String html = """
                <html>
                    <body>
                        <h1>Hello, world!</h1>
                    </body>
                </html>
                """;
        System.out.println(html);


//        Foreign function and memory API 是一种与非 Java 代码和内存交互的标准 API
//        try (MemorySegment segment = MemorySegment.allocateNative(1024)) {
//            MemoryAddress address = segment.baseAddress();
//            MemoryLayout layout = MemoryLayout.ofValue(1);
//            int value = 42;
//            MemoryAccess.setInt(address, 0, value);
//            int result = MemoryAccess.getInt(address, 0);
//            System.out.println(result);
//        }


//        对 Unicode 13.0.0 中定义的新字符和属性的支持
//        char emoji = (char) 0x1F600;
//        if (Character.isEmoji(emoji)) {
//            System.out.println("This is an emoji!");
//        } else {
//            System.out.println("This is not an emoji.");
//        }


    }

    // 记录类型类
//    用于定义只有属性和访问器的简单数据对象
    public record Person(String name, int age) {

    }

    Person person = new Person("John Doe", 30);


}
