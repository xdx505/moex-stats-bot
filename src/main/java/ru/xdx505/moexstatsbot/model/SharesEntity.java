package ru.xdx505.moexstatsbot.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SharesEntity {
    private String secId; //SECID
    private String shortName; //SHORTNAME
    private BigDecimal currentPrice; //LAST
    private BigDecimal changeInPercent; //LASTTOPREVPRICE
    private long capitalization; //ISSUECAPITALIZATION
    private int listLevel; //LISTLEVEL
}
