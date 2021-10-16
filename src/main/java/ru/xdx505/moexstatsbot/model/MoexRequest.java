package ru.xdx505.moexstatsbot.model;

public enum MoexRequest {
    EUR("https://iss.moex.com/iss/engines/currency/markets/index/securities/EURFIX.json?iss.only=marketdata&marketdata.columns=CURRENTVALUE,LASTVALUE&iss.meta=off"),
    MOEX("https://iss.moex.com/iss/engines/stock/markets/index/securities/IMOEX.json?iss.only=marketdata&marketdata.columns=CURRENTVALUE,LASTCHANGEPRC&iss.meta=off"),
    RTSI("https://iss.moex.com/iss/engines/stock/markets/index/securities/RTSI.json?iss.only=marketdata&marketdata.columns=CURRENTVALUE,LASTCHANGEPRC&iss.meta=off"),
    SHARES("https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities.json?iss.meta=off&iss.only=marketdata,securities&marketdata.columns=SECID,LAST,LASTTOPREVPRICE,ISSUECAPITALIZATION&securities.columns=SECID,SHORTNAME,LISTLEVEL&sort_column=LASTTOPREVPRICE&sort_order=desc"),
    USD("https://iss.moex.com/iss/engines/currency/markets/index/securities/USDFIX.json?iss.only=marketdata&marketdata.columns=CURRENTVALUE,LASTVALUE&iss.meta=off");

    private final String uri;

    MoexRequest(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
