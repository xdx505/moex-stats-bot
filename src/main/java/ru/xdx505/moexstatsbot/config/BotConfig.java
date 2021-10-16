package ru.xdx505.moexstatsbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Data
@Component
@PropertySource("classpath:telegram.properties")
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String botPath;
    private String botUsername;
    private String botToken;
    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private String providerToken;
}
