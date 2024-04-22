package cl.omarin.urlshortener.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUrlShortenerModel {
    @NotEmpty private String url;
}
