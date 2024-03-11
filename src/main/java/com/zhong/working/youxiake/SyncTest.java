package com.zhong.working.youxiake;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2024/3/4
 * @desc:
 */
public class SyncTest {


    static class HttpRequestTask implements Runnable {
        private String baseUrl;
        private Integer hotelOrderId;

        public HttpRequestTask(String baseUrl, Integer hotelOrderId) {
            this.baseUrl = baseUrl;
            this.hotelOrderId = hotelOrderId;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(baseUrl + hotelOrderId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response for hotelOrderId " + hotelOrderId + ": " + response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String baseUrl = "https://m.youxiake.com/wenlv/api/wenlv/web2/hotel/order/sync/hotel?hotelOrderId=";

        int start = 1512;
        int end = 1488;

        List<Integer> hotelOrderIds = IntStream.rangeClosed(end, start)
                .boxed()
                .collect(Collectors.toList());


//        Integer[] hotelOrderIds = {
//
//        }; // 可以根据需要设置不同的 hotelOrderId

        ExecutorService executor = Executors.newFixedThreadPool(3); // 使用固定大小为3的线程池

        for (Integer hotelOrderId : hotelOrderIds) {
            Runnable task = new HttpRequestTask(baseUrl, hotelOrderId);
            executor.execute(task);
        }

        executor.shutdown();
    }

    public static void main1(String[] args) {
        int start = 1512;
        int end = 1488;

        List<Integer> numbers = IntStream.rangeClosed(end, start)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(numbers);
    }


}
