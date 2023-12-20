package com.zhong.gitee;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author: juzi
 * @date: 2023/12/20
 * @desc:
 */
public class DrawingBed {

    public abstract static class Param {

        public static final String branch = "master";// 上传到仓库的分支，以前是master;2020.10.01开始就变成了main了
        public static final String name = "juzi"; // 提交的作者或提交者的姓名"+
        public static final String email = "923828430@qq.com";// 提交的作者或提交者的电子邮件"+
        //        public static final String OWNER = "1137882300";// 存库的账户所有者。名称不区分大小写,也就是你GitHub的账号名称
        public static final String OWNER = "coderzane";// 存库的账户所有者。名称不区分大小写,也就是你GitHub的账号名称
        public static final String REPO = "images";// 仓库名称"+
        public static final String PATH = "images/";// 存储仓库里面的路径//第一步设置的时候弄的签名,为了方便理解我区分开，token后面有一个空格，1.8步骤中生产的token
        public static final String Authorization = "token " + "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";// 第一步在GitHub生产的Token
        public static final String token = "9556d23c97824374fc83207e3557bf1a";
    }

    /**
     * @author 小影
     * @create: 2022/7/28 17:27
     * @describe：GitHub图床工具类
     */
    @Slf4j
    public static class GitHubFileUtil {
        /**
         * 上传文件
         *
         * @param filepath    文件地址
         * @param filePostfix 文件后缀
         * @param message     提交描述
         * @return 文件访问地址
         */
        public static String uploading(String filepath, String filePostfix, String message) {
            // 把上传文件内容Base64
            String fileBase64 = encryptToBase64(filepath);
            // 重置文件名防止重复
            String fileName = UUID.randomUUID().toString().replace("-", "") + filePostfix;
            JSONObject param = new JSONObject();
            param.put("message", message);// 提交描述
            param.put("content", fileBase64);// Base64过的文件
            param.put("branch", Param.branch);// 上传到仓库的分支，以前是master;2020.10.01开始就变成了main了
            JSONObject committer = new JSONObject();
            committer.put("name", Param.name); // 提交的作者或提交者的姓名
            committer.put("email", Param.email);// 提交的作者或提交者的电子邮件
            param.put("committer", committer);
            param.put("sha", "");// 添加的时候没有参数也要写""
            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", Param.PATH);// 存储仓库里面的路径
            url = url + fileName;

            HttpResponse response = HttpRequest.put(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).body(param.toString()).execute();
            log.info("响应结果：{}", response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            // 取出访问地址
            JSONObject content = jsonObject.getJSONObject("content");
            return content.getString("download_url");
        }

        /**
         * 查询所有文件的访问地址
         *
         * @param directory 要查询那个目录下的所有图片
         * @return 所有文件访问地址集合
         */
        public static List<String> getFileAll(String directory) {
            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", directory);// 存储仓库里面的路径

            HttpResponse response = HttpRequest.get(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).execute();
            JSONArray objects = JSONObject.parseArray(response.body());
            List<String> allImages = new ArrayList<>();
            objects.forEach(obj -> {
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                allImages.add(jsonObject.getString("download_url"));
            });
            return allImages;
        }

        /**
         * 查询单文件
         *
         * @param fileUrl github库中的路径；例：img/xxx.jpg
         * @return 文件访问地址
         */
        public static String getImages(String fileUrl) {
            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", fileUrl);// 存储仓库里面的路径

            HttpResponse response = HttpRequest.get(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).execute();
            log.info("响应结果：{}", response.body());
            JSONObject result = JSONObject.parseObject(response.body());
            return result.getString("download_url");
        }

        /**
         * 查询sha 替换、删除文件时使用
         *
         * @param fileUrl github库中的路径；例：img/xxx.jpg
         * @return sha
         */
        public static String getSha(String fileUrl) {
            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", fileUrl);// 存储仓库里面的路径

            HttpResponse response = HttpRequest.get(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).execute();
            log.info("响应结果：{}", response.body());
            JSONObject result = JSONObject.parseObject(response.body());
            return result.getString("sha");
        }

        /**
         * 替换文件
         *
         * @param filepath 新文件地址
         * @param message  提交描述
         * @param sha      被替换文件的标识
         * @param path     要替换的路径+文件名 例：img/12.png
         * @return 新文件访问地址
         */
        public static String updateFile(String filepath, String message, String sha, String path) {
            // 把上传文件内容Base64
            String fileBase64 = encryptToBase64(filepath);
            // 重置文件名防止重复
            JSONObject param = new JSONObject();
            param.put("message", message);// 提交描述
            param.put("content", fileBase64);// Base64过的文件
            param.put("branch", Param.branch);// 上传到仓库的分支，以前是master;2020.10.01开始就变成了main了
            JSONObject committer = new JSONObject();
            committer.put("name", Param.name); // 提交的作者或提交者的姓名
            committer.put("email", Param.email);// 提交的作者或提交者的电子邮件
            param.put("committer", committer);
            param.put("sha", sha);// 修改必填
            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", path);// 存储仓库里面的路径

            HttpResponse response = HttpRequest.put(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).body(param.toString()).execute();
            log.info("响应结果：{}", response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            // 取出访问地址
            JSONObject content = jsonObject.getJSONObject("content");
            String download_url = content.getString("download_url");
            return download_url;
        }


        /**
         * 删除文件
         *
         * @param message 提交描述
         * @param sha     被删除文件的标识
         * @param path    删除文件路径 例：img/12.png
         * @return status[200:成功；404:找不到资源；409:冲突；422:验证失败，参数有问题；503:暂停服务]
         */
        public static Integer delFile(String message, String sha, String path) {
            JSONObject param = new JSONObject();
            param.put("message", message);
            param.put("sha", sha);// 删除必填
            param.put("branch", "main");// 分支名称选填
            JSONObject committer = new JSONObject();//提价者信息
            committer.put("name", "2849799912");
            committer.put("email", "2849799912@qq.com");
            param.put("committer", committer);

            String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", path);// 存储仓库里面的路径

            HttpResponse response = HttpRequest.delete(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).body(param.toString()).execute();
            log.info("响应结果：{}", response.body());
            return response.getStatus();
        }


        /**
         * 获取存储库自述文件README.md
         *
         * @return README.md的内容
         */
        public static String getReadme() {
            String url = "https://api.github.com/repos/OWNER/REPO/readme";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO);// 仓库名称

            HttpResponse response = HttpRequest.get(url)
                    .header("Accept", "application/vnd.github+json")
                    .header("Authorization", Param.Authorization).execute();
            log.info("响应结果：{}", response);
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            return jsonObject.getString("download_url");
        }

        /**
         * 文件转base64
         *
         * @param filePath 文件地址
         */

    }

    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Slf4j
    public static class GiteeFileUtil {
        public static String uploading(String filepath, String filePostfix, String message) {
            // 把上传文件内容Base64
            String fileBase64 = encryptToBase64(filepath);
            JSONObject param = new JSONObject();
            param.put("access_token", Param.token);
            param.put("content", fileBase64);// Base64过的文件
            param.put("message", message);// 提交描述
            param.put("branch", Param.branch);
//            https://gitee.com/api/v5/repos/owner/repo/contents/path/fileName
            String url = "https://gitee.com/api/v5/repos/OWNER/REPO/contents/PATH";
            url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                    .replace("REPO", Param.REPO)// 仓库名称
                    .replace("PATH", Param.PATH);// 存储仓库里面的路径
            url = url + System.currentTimeMillis() + filePostfix;
            log.info("url：{}", url);
            HttpResponse response = HttpRequest.post(url)
                    .body(param.toString())
                    .execute();
            log.info("响应结果：{}", response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            // 取出访问地址
            JSONObject content = jsonObject.getJSONObject("content");
            return content.getString("download_url");
        }

        public static String getTree() {
            //https://gitee.com/api/v5/repos/coderzane/images/git/gitee/trees/sha
            String url = String.format("https://gitee.com/api/v5/repos/coderzane/images/git/gitee/trees/%s", Param.branch);
            HttpResponse response = HttpRequest.get(url)
                    .execute();
            log.info("响应结果：{}", response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            JSONArray tree = jsonObject.getJSONArray("tree");
            Optional<String> first = tree.stream().filter(x -> {
                JSONObject js = (JSONObject) x;
                String path = js.getString("path");
                return path.equalsIgnoreCase("images");
            }).map(x -> {
                JSONObject js = (JSONObject) x;
                return js.getString("sha");
            }).findFirst();

            if (!first.isPresent()) {
                return null;
            }

            String url2 = String.format("https://gitee.com/api/v5/repos/coderzane/images/git/gitee/trees/%s", first.get());
            HttpResponse response2 = HttpRequest.get(url2)
                    .execute();
            log.info("响应结果：{}", response2.body());
            JSONObject object = JSONObject.parseObject(response2.body());

            JSONArray array = object.getJSONArray("tree");
            List<String> pathList = array.stream().map(x -> {
                JSONObject js = (JSONObject) x;
                return js.getString("path");
            }).collect(Collectors.toList());

            List<String> result = Lists.newArrayList();
            String prefix = "https://gitee.com/coderzane/images/raw/master/images/";
            pathList.forEach(x -> result.add(prefix + x));

            return JSONObject.toJSONString(result);
        }


        public static String getImg() {
            //https://gitee.com/api/v5/repos/coderzane/images/git/blobs/sha

            String url = String.format("https://gitee.com/api/v5/repos/coderzane/images/git/blobs/%s", "72a38dc78d64927481996358d5e2bf60052e1827");
            Map<String, Object> params = new HashMap<>();
            params.put("method", "GET");
            params.put("json", true);
            params.put("resolveWithFullResponse", true);
            params.put("url", url);
            Map<String, Object> qs = new HashMap<>();
            qs.put("access_token", Param.token);
            params.put("qs", qs);

            HttpResponse response = HttpRequest.get(url)
                    .form(params)
                    .execute();
            log.info("响应结果：{}", response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            String content = jsonObject.getString("content");

            byte[] encode = Base64.getEncoder().encode(content.getBytes());
            return new String(encode, StandardCharsets.UTF_8);

        }

    }


}
