package ru.xdx505.moexstatsbot.botapi.model.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.xdx505.moexstatsbot.botapi.Bot;
import ru.xdx505.moexstatsbot.service.SmartLabService;

@Component
public class MapMessage {
    private final SmartLabService smartLabService;
    private final SendPhoto sendPhoto;

    @Autowired
    public MapMessage(SmartLabService smartLabService, SendPhoto sendPhoto) {
        this.smartLabService = smartLabService;
        this.sendPhoto = sendPhoto;
    }

    public SendMessage handle(Bot bot, Message message) throws InterruptedException, TelegramApiException {
        sendPhoto.setPhoto(new InputFile(smartLabService.getMarketMap()));
        sendPhoto.setChatId(message.getChatId().toString());
        bot.execute(sendPhoto);
        return null;
    }
}
