package ru.xdx505.moexstatsbot.botapi.model.message;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface DefaultMessage {
    PartialBotApiMethod<Message> handle(Message message) throws InterruptedException;
}
