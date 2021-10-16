package ru.xdx505.moexstatsbot.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.xdx505.moexstatsbot.utils.MoexApiEntityDeserializer;

import java.util.List;

@Data
@JsonDeserialize(using = MoexApiEntityDeserializer.class)
public class MoexApiEntity {
    private List<List<String>> data;
}
