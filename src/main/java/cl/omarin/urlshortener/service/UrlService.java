package cl.omarin.urlshortener.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.omarin.urlshortener.adapter.DatabaseAdapter;
import cl.omarin.urlshortener.adapter.QueueAdapter;
import cl.omarin.urlshortener.model.UrlShortenerModel;
import cl.omarin.urlshortener.utils.Utils;

@Service
public class UrlService {
    @Autowired
    Utils utils;
    @Autowired
    QueueAdapter queueAdapter;
    @Autowired
    DatabaseAdapter databaseAdapter;

    public String getOriginalUrl(String code) {
        Optional<UrlShortenerModel> result = Optional.ofNullable(utils.getUrlShortenerResolver(code));
        if (result.isPresent() && result.get().getIsEnabled()) {
            queueAdapter.sendEvent(result.get());
            return result.get().getOriginalUrl();
        }
        return null;
    }

    public String createUrlShortener(String url) {
        var code = utils.generateCode();
        var result = false;
        while (!result) {
            result = databaseAdapter.createUrlShortener(new UrlShortenerModel(url, code, true));
            if (!result) code = utils.generateCode();
        }
        return code;
    }

    public UrlShortenerModel editUrlShortener(UrlShortenerModel entity) {
        return databaseAdapter.updateUrlShortener(entity);
    }
}
