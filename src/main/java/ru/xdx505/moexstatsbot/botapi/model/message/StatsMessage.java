package ru.xdx505.moexstatsbot.botapi.model.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.xdx505.moexstatsbot.botapi.Bot;
import ru.xdx505.moexstatsbot.service.MessageService;
import ru.xdx505.moexstatsbot.service.SmartLabService;

import java.io.IOException;

@Component
public class StatsMessage {
    private final SendPhoto sendPhoto;
    private final SmartLabService smartLabService;
    private final MessageService messageService;

    @Autowired
    public StatsMessage(SendPhoto sendPhoto, SmartLabService smartLabService, MessageService messageService) {
        this.sendPhoto = sendPhoto;
        this.smartLabService = smartLabService;
        this.messageService = messageService;
    }

    public SendMessage handle(Bot bot, Message message) throws TelegramApiException, InterruptedException, IOException {
        sendPhoto.setPhoto(new InputFile(smartLabService.getMarketMap()));
        sendPhoto.setCaption(messageService.getDayText());
        sendPhoto.setChatId(message.getChatId().toString());
        bot.execute(sendPhoto);
        return null;
    }

}
