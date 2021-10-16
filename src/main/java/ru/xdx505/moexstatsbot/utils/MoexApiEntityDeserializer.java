package ru.xdx505.moexstatsbot.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.xdx505.moexstatsbot.model.MoexApiEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoexApiEntityDeserializer extends StdDeserializer<MoexApiEntity> {
    public MoexApiEntityDeserializer() {
        this(null);
    }

    public MoexApiEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MoexApiEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode moexNode = jsonParser.getCodec().readTree(jsonParser);
        final MoexApiEntity moexApiEntity = new MoexApiEntity();
        final List<List<String>> data = new ArrayList<>();

        moexNode.get("marketdata").get("data").elements().forEachRemaining(jsonNode -> {
            final List<String> values = new ArrayList<>();
            Iterator<JsonNode> iterator = jsonNode.elements();
            while (iterator.hasNext()) {
                values.add(iterator.next().asText());
            }
            data.add(values);
        });
        moexApiEntity.setData(data);
        return moexApiEntity;
    }

}
