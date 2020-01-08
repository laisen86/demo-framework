package com.example.demoframework.web.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * druid数据库连接监控控制层
 *
 * @author zhangxueli6
 * @date 2019/2/18
 */
@Controller
public class JsoupTestController {
    @GetMapping("/jsoup/test")
    public void jsoupTest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Document document = Jsoup.connect("https://osx.cx/").header("User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36").get();
        System.out.println(document.outerHtml());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
//       writer.println(document.outerHtml());
        OKHttpRequest okHttpRequest = new OKHttpRequest();
        String html = okHttpRequest.get("https://osx.cx/");
         writer.println(html);
    }
}
