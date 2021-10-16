package ru.xdx505.moexstatsbot.model;

public enum Page {
    FINAM("https://www.finam.ru/"),
    SMARTLAB_MAIN("https://www.smart-lab.ru/"),
    SMARTLAB_MAP("https://www.smart-lab.ru/q/map/"),
    STOP_COVID("https://xn--80aesfpebagmfblc0a.xn--p1ai/information/");

    private final String url;

    Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
