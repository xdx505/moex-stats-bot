package ru.xdx505.moexstatsbot.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    @Bean
    public ChromeDriver chromeDriver() {
        var chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions", "--headless", "--disable-gpu", "--no-sandbox");
        return new ChromeDriver(chromeOptions);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
