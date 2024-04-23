package cl.omarin.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import cl.omarin.urlshortener.model.CreateUrlShortenerModel;
import cl.omarin.urlshortener.model.GenericResponseModel;
import cl.omarin.urlshortener.model.UrlShortenerModel;
import cl.omarin.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;

@RestController
@Validated
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH}) 
public class UrlShortenerController {
    @Autowired
    private UrlService service;

    @GetMapping("/{code}")
    public void getUrlRedirection(@PathVariable @NotEmpty String code, HttpServletResponse response) throws Exception {
        String originalUrl = service.getOriginalUrl(code);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, originalUrl);
        }
    }

    @PostMapping("/url-shortener")
    public GenericResponseModel<String> createUrlShortener(@RequestBody CreateUrlShortenerModel body) throws Exception {
        var code = service.createUrlShortener(body.getUrl());
        return new GenericResponseModel<String>("OK", "Short url created", code);
    }

    @PatchMapping("/url-shortener/{code}")
    public GenericResponseModel<UrlShortenerModel> editUrlShortener(
        @PathVariable @NotEmpty String code, 
        @RequestBody UrlShortenerModel entity
    ) throws Exception {
        entity.setCode(code);
        var result = service.editUrlShortener(entity);
        if (result == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Url not found with code %s", code));
        return new GenericResponseModel<UrlShortenerModel>("OK", "Url shortener data modified", result);
    }
}
