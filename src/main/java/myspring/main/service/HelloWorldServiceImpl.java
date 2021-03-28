package myspring.main.service;

import myspring.springframework.annotation.Component;
import myspring.springframework.annotation.Scope;
import myspring.springframework.annotation.Value;

@Component(name = "helloWorldService")
@Scope("prototype")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Value("Hello world")
    private String text;

    @Override
    public void saySomething() {
        System.out.println(text);
    }

    @Override
    public String getString() {
        return text;
    }
}
