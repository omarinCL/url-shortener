package cl.omarin.urlshortener.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventMessageModel {
    private String eventName;
    private UrlShortenerModel entity;
    private LocalDateTime date;

    public EventMessageModel(UrlShortenerModel entity) {
        this.entity = entity;
        this.date = LocalDateTime.now();
        this.eventName = "CLICK_EVENT";
    }
}
