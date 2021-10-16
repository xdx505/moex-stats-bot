package ru.xdx505.moexstatsbot.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.xdx505.moexstatsbot.botapi.model.message.MapMessage;
import ru.xdx505.moexstatsbot.botapi.model.message.StatsMessage;
import ru.xdx505.moexstatsbot.botapi.model.message.WelcomeMessage;

import java.io.IOException;

@Slf4j
@Component
public class TelegramFacade {

    private final WelcomeMessage welcomeMessage;
    private final StatsMessage statsMessage;
    private final MapMessage mapMessage;

    @Autowired
    public TelegramFacade(WelcomeMessage welcomeMessage, StatsMessage statsMessage, MapMessage mapMessage) {
        this.welcomeMessage = welcomeMessage;
        this.statsMessage = statsMessage;
        this.mapMessage = mapMessage;
    }

    public BotApiMethod<?> handleUpdate(Bot bot, Update update) throws InterruptedException, TelegramApiException, IOException {
        var message = update.getMessage();
        BotApiMethod<?> sendMessage = null;
        if (message != null) {
            if (message.hasText()) {
                log.info(
                        "New message from User: {}, chatId: {}, with text: {}", message.getFrom().getUserName(),
                        message.getChatId(), message.getText()
                );
                sendMessage = handleInputMessage(bot, message);
            }
        }
        return sendMessage;
    }

    private BotApiMethod<?> handleInputMessage(Bot bot, Message message) throws InterruptedException, TelegramApiException, IOException {
        var text = message.getText();
        switch (text) {
            case ("Лидеры роста и падения"):
                return statsMessage.handle(bot, message);
            case ("Карта рынка"):
                return mapMessage.handle(bot, message);
            default:
                return welcomeMessage.handle(message);
        }
    }


}
