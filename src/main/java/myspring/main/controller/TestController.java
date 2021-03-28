package myspring.main.controller;

import myspring.main.service.HelloWorldService;
import myspring.springframework.annotation.Autowired;
import myspring.springframework.annotation.Controller;
import myspring.springframework.annotation.RequestMapping;
import myspring.springframework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam("param") String param) {
        try {
            String text = helloWorldService.getString();
            response.getWriter().write(text + " and the param is " + param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
