package ru.xdx505.moexstatsbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.xdx505.moexstatsbot.model.MoexRequest;
import ru.xdx505.moexstatsbot.model.SharesEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SharesEntityService {

    public Map<String, BigDecimal> getLeaders(int count) throws IOException {
        var leaders = new LinkedHashMap<String, BigDecimal>();
        var sortedList = getShares().stream()
                .sorted(Comparator.comparing(SharesEntity::getChangeInPercent).reversed())
                .collect(Collectors.toList());

        var upLeaders = new LinkedList<SharesEntity>();
        var downLeaders = new LinkedList<SharesEntity>();

        for (int i = 0; i < count; i++) {
            upLeaders.add(sortedList.get(i));
            downLeaders.add(sortedList.get(sortedList.size() - 1 - i));
        }
        downLeaders.sort(Comparator.comparing(SharesEntity::getChangeInPercent).reversed());

        for (int i = 0; i < count; i++) {
            leaders.put(upLeaders.get(i).getShortName(), upLeaders.get(i).getChangeInPercent());
        }
        for (int i = 0; i < count; i++) {
            leaders.put(
                    downLeaders.get(i).getShortName(), downLeaders.get(i).getChangeInPercent());
        }
        return leaders;
    }

    private JsonNode getJson() throws IOException {
        return new ObjectMapper().readTree(new URL(MoexRequest.SHARES.getUri()));
    }

    private Collection<SharesEntity> getShares() throws IOException {
        var response = getJson();
        var sharesMap = new HashMap<String, SharesEntity>();
        response.get("securities").get("data").elements().forEachRemaining(e -> {
            var sharesEntity = new SharesEntity();
            sharesEntity.setSecId(e.get(0).asText());
            sharesEntity.setShortName(e.get(1).asText());
            sharesEntity.setListLevel(e.get(2).asInt());
            sharesMap.put(sharesEntity.getSecId(), sharesEntity);
        });
        response.get("marketdata").get("data").elements().forEachRemaining(e -> {
            var currentEntity = sharesMap.get(e.get(0).asText());
            currentEntity.setCurrentPrice(BigDecimal.valueOf(e.get(1).asDouble()));
            currentEntity.setChangeInPercent(BigDecimal.valueOf(e.get(2).asDouble()));
            currentEntity.setCapitalization(e.get(3).asLong());
        });
        return sortAndFilterShares(sharesMap);
    }

    private Collection<SharesEntity> sortAndFilterShares(Map<String, SharesEntity> sharesEntityMap) {
        return sharesEntityMap.values().stream()
                .filter(e -> e.getListLevel() <= 2)
                .filter(e -> e.getCapitalization() == 0 || e.getCapitalization() >= 10000000000L)
                .collect(Collectors.toList());
    }
}
