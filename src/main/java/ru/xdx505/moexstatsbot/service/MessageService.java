package ru.xdx505.moexstatsbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

@Service
public class MessageService {
    private final MoexService moexService;
    private final SharesEntityService sharesEntityService;

    @Autowired
    public MessageService(MoexService moexService, SharesEntityService sharesEntityService) {
        this.moexService = moexService;
        this.sharesEntityService = sharesEntityService;
    }

    public String getDayText() throws IOException {
        var count = 5;
        var leadersMap = sharesEntityService.getLeaders(count);
        var iterator = leadersMap.entrySet().iterator();

        var stringBuilder = new StringBuilder();

        stringBuilder.append("<b>\uD83D\uDDD3")
                .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(new Locale("ru")).format(LocalDate.now()))
                .append(". Текущая ситуация на рынке</b>\n\n");

        stringBuilder.append("Индекс Мосбиржи: ").append(moexService.getMoexStats()).append("\n");
        stringBuilder.append("Индекс РТС: ").append(moexService.getRtsiStats()).append("\n");
        stringBuilder.append("\uD83D\uDCB5 Доллар: ").append(moexService.getUsdStats()).append("\n");
        stringBuilder.append("\uD83D\uDCB6 Евро: ").append(moexService.getEurStats()).append("\n\n");

        stringBuilder.append(getLeaders(count, iterator));

        return stringBuilder.toString();
    }

    public String getEveningText() throws IOException {
        var count = 3;
        var leadersMap = sharesEntityService.getLeaders(count);
        var iterator = leadersMap.entrySet().iterator();
        var stringBuilder = new StringBuilder();

        stringBuilder.append("\uD83C\uDDF7\uD83C\uDDFA Индекс Мосбиржи: ").append(moexService.getMoexStats()).append("\n");
        stringBuilder.append("\uD83D\uDCB5 Доллар: ").append(moexService.getUsdStats()).append("\n\n");

        stringBuilder.append(getLeaders(count, iterator));

        stringBuilder.append("\uD83D\uDCBC Получите доступ к нашим портфелям и следите за сделками в <a href=\"https://t.me/gazeta_investora/4684\">Premium канале</a>.");

        return stringBuilder.toString();
    }

    private String getLeaders(int count, Iterator<Map.Entry<String, BigDecimal>> iterator) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("<b>\uD83D\uDCC8 Лидеры роста:</b>\n");
        for (int i = 0; i < count; i++) {
            var pair = iterator.next();
            stringBuilder.append(String.format("%s (%,.2f%%)\n", pair.getKey(), pair.getValue()));
        }

        var downLeaders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var pair = iterator.next();
            downLeaders.add(String.format("%s (%,.2f%%)\n", pair.getKey(), pair.getValue()));
        }
        stringBuilder.append("\n").append("<b>\uD83D\uDCC9 Лидеры падения:</b>\n");
        Collections.reverse(downLeaders);
        downLeaders.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
