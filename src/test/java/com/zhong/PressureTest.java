package com.zhong;

import cn.hutool.core.io.FileUtil;
import io.restassured.response.ValidatableResponse;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.report.HtmlReportModule;
import org.databene.contiperf.report.ReportContext;
import org.databene.contiperf.report.ReportModule;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Throughput，表示QPS，
 * Average Latency，表示平均响应速度。
 */
public class PressureTest {
    /**
     * 注解参数说明
     *
     * @PerfTest :
     * invocations：执行次数n，与线程数量无关，默认值为1
     * threads：线程池数量n，并发执行n个线程
     * duration：重复地执行时间n，测试至少执行n毫秒
     * @Required :
     * throughput：吞吐要求n，要求每秒至少执行n个测试
     * average：平均执行时间n，要求平均执行时间不超过n毫秒
     * max：最大执行时间n，要求最大的执行时间不超过n毫秒
     * totalTime：总执行时间n，要求总的执行时间不超过n毫秒
     * median：50%平均执行时间n，要求所有执行的50%测试平均执行时间不超过n毫秒
     * percentile90：90%平均执行时间n，要求所有执行的90%测试平均执行时间不超过n毫秒
     * percentile95：95%平均执行时间n，要求所有执行的95%测试平均执行时间不超过n毫秒
     * percentile99：99%平均执行时间n，要求所有执行的99%测试平均执行时间不超过n毫秒
     * percentiles：表达式"a:n,b:m"，要求a%的测试不超过n毫秒，b%的测试不超过m毫秒
     */
    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

//    private final String baseUrl = "http://127.0.0.1:8003/wx/subscribe/message/";
    private final String baseUrl = " http://localhost:8003/api/wx/subscribe/message/";

    @Test
    @PerfTest(duration = 50000, threads = 10, invocations = 100)
    public void testApi() {
        given()
                .pathParam("type", new Random().nextInt(2) + 1)
                .when() //when()：开始发起请求
                .post(baseUrl + "{type}")
                .then() //对请求结果进行断言/验证
                .statusCode(200)
//                .body("code", equalTo("200"))
        ;
    }

    @Test
    @After
    public void testRep() {
        //压测报告的位置
        File reportFolder = contiPerfRule.getContext().getReportFolder();
        System.out.println(reportFolder);
    }

    @Test
    @PerfTest(duration = 60000, threads = 10)
    public void testApiPerformance() {
        given()
                .params("param1", "value1")
                .params("param2", "value2")
                .when()
                .get(baseUrl + "/api")
                .then()
                .statusCode(200)
                .body("result", equalTo("success"));
    }

    @PerfTest(invocations = 100, threads = 10)
    @Test
    public void testApiPerformance2() {
        given()
                .params("param1", "value1")
                .params("param2", "value2")
                .pathParam("id", 123)
                .when()
                .get(baseUrl + "/resource/{id}")
                .then()
                .statusCode(200)
                .body("result", equalTo("success"));
    }

    @PerfTest(duration = 30000, threads = 5)
    @Test
    public void testApiPerformance3() {
        String requestBody = "{\"name\": \"Judy\", \"age\": 25}";

        given()
                .header("Authorization", "Bearer token")
                .body(requestBody)
                .when()
                .post(baseUrl + "/resource")
                .then()
                .statusCode(201);
    }

    @Test
    public void generateHtmlReport() throws IOException {
        String reportPath = "target/performance-test-report.html";
//        Files.write(Paths.get(reportPath), contiPerfRule.getContext().getReportFolder().);
//        Files.write(Paths.get(reportPath), contiPerfRule.getReport().toHtml().getBytes());
        System.out.println("测试报告已生成：" + reportPath);
    }
}