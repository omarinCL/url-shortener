package cl.omarin.urlshortener.adapter;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.omarin.urlshortener.model.UrlShortenerModel;

// Dummy response for test purposes
@Repository
public class DatabaseAdapter {
    private static final HashMap<String, UrlShortenerModel> database = new HashMap<String, UrlShortenerModel>();
    @Autowired
    CacheAdapter cacheAdapter;

    public DatabaseAdapter() {
        createDummyData();
    }

    public UrlShortenerModel getData(String code) {
        return database.get(code);
    }

    public boolean createUrlShortener(UrlShortenerModel entity) {
        if (database.get(entity.getCode()) != null) return false;
        database.put(entity.getCode(), entity);
        return true;
    }

    public UrlShortenerModel updateUrlShortener(UrlShortenerModel entity) {
        var code = entity.getCode();
        var data = database.get(code);
        if (data == null) return null;
        if (entity.getOriginalUrl() != null && entity.getOriginalUrl() != data.getOriginalUrl()) data.setOriginalUrl(entity.getOriginalUrl());
        if (entity.getIsEnabled() != null && entity.getIsEnabled() != data.getIsEnabled()) data.setIsEnabled(entity.getIsEnabled());
        database.put(code, data);
        cacheAdapter.removeFromCache(code);
        return data;
    }

    private void createDummyData() {
        database.put("shuoaskj", new UrlShortenerModel("https://aws.amazon.com", "shuoaskj", true));
        database.put("i72g5897", new UrlShortenerModel("https://cloud.mongodb.com", "i72g5897", true));
    }
}
