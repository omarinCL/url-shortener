package cl.omarin.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlShortenerModel {
    private String originalUrl;
    private String code;
    private Boolean isEnabled;
}
