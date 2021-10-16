package ru.xdx505.moexstatsbot.botapi.model.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class WelcomeMessage implements DefaultMessage {
    private final SendMessage sendMessage;

    @Autowired
    public WelcomeMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public SendMessage handle(Message message) {
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(prepareText());
        return sendMessage;
    }

    private String prepareText() {
        return "<b>Помогатор ИнвесТемы</b>\n\n" +
                "Привет я <b>ИнвестТёма</b> - бот помогатор для администраторов ИнвестТемы! 👋\n Здесь ты сможешь получить полезные посты по эмитентам с мосбиржи. А в дальнейшем может ещё что-нибудь... 🙂";
    }
}
