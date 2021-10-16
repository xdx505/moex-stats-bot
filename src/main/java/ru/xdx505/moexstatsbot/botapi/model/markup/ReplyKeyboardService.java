package ru.xdx505.moexstatsbot.botapi.model.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.xdx505.moexstatsbot.botapi.model.button.LeadersKeyboardButton;
import ru.xdx505.moexstatsbot.botapi.model.button.MapKeyboardButton;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReplyKeyboardService {
    private final LeadersKeyboardButton leadersKeyboardButton;
    private final MapKeyboardButton mapKeyboardButton;

    @Autowired
    public ReplyKeyboardService(LeadersKeyboardButton leadersKeyboardButton, MapKeyboardButton mapKeyboardButton) {
        this.leadersKeyboardButton = leadersKeyboardButton;
        this.mapKeyboardButton = mapKeyboardButton;
    }

    public ReplyKeyboardMarkup getMenuReplyMarkup() {
        return ReplyKeyboardMarkup.builder()
                .keyboardRow(
                        new KeyboardRow(
                                Stream.of(leadersKeyboardButton, mapKeyboardButton).collect(Collectors.toList())
                        ))
                .resizeKeyboard(true)
                .build();
    }
}
