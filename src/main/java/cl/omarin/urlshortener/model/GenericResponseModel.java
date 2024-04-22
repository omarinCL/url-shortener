package cl.omarin.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponseModel<T> {
    private String status;
    private String info;
    private T data;
}
