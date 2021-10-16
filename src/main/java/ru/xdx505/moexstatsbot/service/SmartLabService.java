package ru.xdx505.moexstatsbot.service;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xdx505.moexstatsbot.model.Page;

import java.io.File;

@Service
public class SmartLabService {
    private final ChromeDriver chromeDriver;

    @Autowired
    public SmartLabService(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public File getMarketMap() throws InterruptedException {
        chromeDriver.manage().window().setSize(new Dimension(750, 800));
        chromeDriver.get(Page.SMARTLAB_MAP.getUrl());
        Thread.sleep(5000);
        return chromeDriver.findElementByClassName("highcharts-plot-border").getScreenshotAs(OutputType.FILE);
    }
}
