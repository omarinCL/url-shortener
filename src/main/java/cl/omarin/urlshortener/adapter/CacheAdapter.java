package cl.omarin.urlshortener.adapter;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import cl.omarin.urlshortener.model.UrlShortenerModel;

// Dummy response for test purposes
@Repository
public class CacheAdapter {
    private static final HashMap<String, UrlShortenerModel> database = new HashMap<String, UrlShortenerModel>();

    public CacheAdapter() {
        this.createDummyData();
    }

    public UrlShortenerModel getCachedData(String code) {
        return database.get(code);
    }

    public void createKeyValuePair(UrlShortenerModel model) {
        database.put(model.getCode(), model);
    }

    public void removeFromCache(String code) {
        database.remove(code);
    }

    private void createDummyData() {
        database.put("sjkw123lx", new UrlShortenerModel("https://www.google.cl", "sjkw123lx", true));
        database.put("hj19xa21", new UrlShortenerModel("https://www.mercadolibre.cl", "hj19xa21", true));
    }
}
