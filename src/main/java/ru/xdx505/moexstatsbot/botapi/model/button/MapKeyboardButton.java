package ru.xdx505.moexstatsbot.botapi.model.button;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@PropertySource(value = "classpath:button_ru_RU.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "map")
public class MapKeyboardButton extends DefaultKeyboardButton {
    private String text;

    @PostConstruct
    public void init() {
        super.setText(text);
    }
}
