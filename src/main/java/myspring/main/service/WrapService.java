package myspring.main.service;

import myspring.springframework.annotation.Autowired;
import myspring.springframework.annotation.Component;

@Component(name = "wrapService")
public class WrapService {

    @Autowired
    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }

}
