package ru.xdx505.moexstatsbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.xdx505.moexstatsbot.model.MoexApiEntity;
import ru.xdx505.moexstatsbot.model.MoexRequest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MoexService {
    private final RestTemplate restTemplate;

    @Autowired
    public MoexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getMoexStats() {
        var data = getData(MoexRequest.MOEX.getUri());
        return String.format("%s (%s%%)", setScale(data.get(0).get(0), 2), setScale(data.get(0).get(1), 2));
    }

    public String getRtsiStats() {
        var data = getData(MoexRequest.RTSI.getUri());
        return String.format("%s (%s%%)", setScale(data.get(0).get(0), 2), setScale(data.get(0).get(1), 2));
    }

    public String getUsdStats() {
        var data = getData(MoexRequest.USD.getUri());
        var changeValue = changeInPercentFormat(data.get(0).get(0), data.get(0).get(1));
        return String.format("%s (%.2f%%)", setScale(data.get(0).get(0), 2), changeValue);
    }

    public String getEurStats() {
        var data = getData(MoexRequest.EUR.getUri());
        var changeValue = changeInPercentFormat(data.get(0).get(0), data.get(0).get(1));
        return String.format("%s (%.2f%%)", setScale(data.get(0).get(0), 2), changeValue);
    }

    public List<List<String>> getData(String uri) {
        var response = restTemplate.getForEntity(uri, MoexApiEntity.class);
        return response.getBody().getData();
    }

    private double changeInPercentFormat(String current, String last) {
        var currentDec = BigDecimal.valueOf(Double.parseDouble(current));
        var lastDec = BigDecimal.valueOf(Double.parseDouble(last));
        return ((currentDec.subtract(lastDec))
                .divide(lastDec, MathContext.DECIMAL32))
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

    private double setScale(String data, int scale) {
        return BigDecimal.valueOf(Double.parseDouble(data)).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
}
