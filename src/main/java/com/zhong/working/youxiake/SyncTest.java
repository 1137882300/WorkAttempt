package com.zhong.working.youxiake;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Integer[] hotelOrderIds = {
                679,
                693,
                760,
                761,
                1037,
                1039,1040,1041,1042,
                1043,1044,
                1048,1049,
                1050,
                1051,
                1052,
                1053,
                1055,
                1056,
                1057,
                1059,
                1060,
                1061,
                1062,
                1154,
                1155,
                1156,
                1157,
                1158,
                1159,
                1160,
                1161,
                1162,
                1163,
                1164,
                1165,
                1166,
                1167,
                1168,
                1169,
                1170,
                1171,
                1172,
                1173,
                1174,
                1175,
                1176,
                1177,
                1178,
                1179,
                1180,
                1181,
                1184,
                1185,
                1186,
                1187,
                1188,
                1190,
                1191,
                1192,
                1193,
                1194,
                1195,
                1196,
                1197,
                1198,
                1199,
                1200,
                1203,
                1204,
                1206,
                1209,
                1210,
                1211,
                1215,
                1217,
                1218,
                1219,
                1220,
                1222,
                1226,
                1227,
                1228,
                1232,
                1234,
                1237,
                1239,
                1244,
                1246,
                1247,
                1248,
                1249,
                1251,
                1253,
                1254,
                1257,
                1258,
                1259,
                1261,
                1262,
                1263,
                1264
        }; // 可以根据需要设置不同的 hotelOrderId

        ExecutorService executor = Executors.newFixedThreadPool(3); // 使用固定大小为3的线程池

        for (Integer hotelOrderId : hotelOrderIds) {
            Runnable task = new HttpRequestTask(baseUrl, hotelOrderId);
            executor.execute(task);
        }

        executor.shutdown();
    }


}
