package ru.xdx505.moexstatsbot.service;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.springframework.stereotype.Service;
import ru.xdx505.moexstatsbot.config.WebConfig;
import ru.xdx505.moexstatsbot.model.Page;

import java.io.File;

@Service
public class SmartLabService {

    public File getMarketMap() throws InterruptedException {
        var chromeDriver = WebConfig.chromeDriver();
        chromeDriver.manage().window().setSize(new Dimension(750, 800));
        chromeDriver.get(Page.SMARTLAB_MAP.getUrl());
        Thread.sleep(5000);
        var map = chromeDriver.findElementByClassName("highcharts-plot-border").getScreenshotAs(OutputType.FILE);
        chromeDriver.quit();
        return map;
    }
}
