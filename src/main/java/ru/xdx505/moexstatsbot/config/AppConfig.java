package ru.xdx505.moexstatsbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.xdx505.moexstatsbot.botapi.Bot;
import ru.xdx505.moexstatsbot.botapi.TelegramFacade;
import ru.xdx505.moexstatsbot.botapi.model.markup.ReplyKeyboardService;

@Configuration
public class AppConfig {
    private final BotConfig botConfig;
    private final ReplyKeyboardService replyKeyboardService;

    @Autowired
    public AppConfig(BotConfig botConfig, ReplyKeyboardService replyKeyboardService) {
        this.botConfig = botConfig;
        this.replyKeyboardService = replyKeyboardService;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url((botConfig.getBotPath())).build();
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        var options = new DefaultBotOptions();
        if (botConfig.getProxyType().equals(DefaultBotOptions.ProxyType.NO_PROXY)) return options;
        options.setProxyType(botConfig.getProxyType());
        options.setProxyHost(botConfig.getProxyHost());
        options.setProxyPort(botConfig.getProxyPort());
        return options;
    }

    @Bean
    public Bot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        var bot = new Bot(setWebhook, telegramFacade);
        bot.setBotToken(botConfig.getBotToken());
        bot.setBotUsername(botConfig.getBotUsername());
        bot.setBotPath(botConfig.getBotPath());
        return bot;
    }

    @Bean
    public DefaultAbsSender defaultAbsSender() {
        return new DefaultAbsSender(defaultBotOptions()) {
            @Override
            public String getBotToken() {
                return botConfig.getBotToken();
            }
        };
    }

    @Bean
    public SendMessage sendMessage() {
        var message = new SendMessage();
        message.enableHtml(true);
        message.setDisableWebPagePreview(true);
        message.setReplyMarkup(replyKeyboardService.getMenuReplyMarkup());
        return message;
    }

    @Bean
    public SendPhoto sendPhoto() {
        var message = new SendPhoto();
        message.setParseMode("html");
        message.setReplyMarkup(replyKeyboardService.getMenuReplyMarkup());
        return message;
    }
}
