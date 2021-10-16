package ru.xdx505.moexstatsbot.botapi;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class Bot extends SpringWebhookBot {
    @Autowired
    private final TelegramFacade telegramFacade;
    private String botPath;
    private String botUsername;
    private String botToken;

    public Bot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    public Bot(DefaultBotOptions options, SetWebhook setWebhook, TelegramFacade telegramFacade) {
        super(options, setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(this, update);
    }


}
