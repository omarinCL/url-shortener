package cl.omarin.urlshortener.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.omarin.urlshortener.adapter.CacheAdapter;
import cl.omarin.urlshortener.adapter.DatabaseAdapter;
import cl.omarin.urlshortener.model.UrlShortenerModel;

@Component
public class Utils {
    @Autowired
    private CacheAdapter cacheAdapter;

    @Autowired
    private DatabaseAdapter databaseAdapter;

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public UrlShortenerModel getUrlShortenerResolver(String code) {
        try {
            var result = this.cacheAdapter.getCachedData(code);
            if (result == null) result = this.databaseAdapter.getData(code);
            if (result != null) this.cacheAdapter.createKeyValuePair(result);
            return result;
        } catch (Exception e) {
            logger.error("Error al obtener la url original", e);
            return null;
        }
    }

    public String generateCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }
}
