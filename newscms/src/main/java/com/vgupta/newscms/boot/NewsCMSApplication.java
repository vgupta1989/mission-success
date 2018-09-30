package com.vgupta.newscms.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@Import({NewsCMSAppConfig.class})
public class NewsCMSApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello from NewsCmsApplication";
    }

    public static void main(String[] args) {
        SpringApplication.run(NewsCMSApplication.class, args);
    }
}
