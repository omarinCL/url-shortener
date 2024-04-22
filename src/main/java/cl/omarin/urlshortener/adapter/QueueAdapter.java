package cl.omarin.urlshortener.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cl.omarin.urlshortener.model.EventMessageModel;
import cl.omarin.urlshortener.model.UrlShortenerModel;

@Repository
public class QueueAdapter {
    private static final Logger logger = LoggerFactory.getLogger(QueueAdapter.class);
    public void sendEvent(UrlShortenerModel model) {
        EventMessageModel message = new EventMessageModel(model);
        // Send message method call producer
        logger.info("Send message: {}", message.toString());
    }
}
