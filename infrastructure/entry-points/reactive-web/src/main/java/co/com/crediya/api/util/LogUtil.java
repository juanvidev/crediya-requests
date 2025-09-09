package co.com.crediya.api.util;

import co.com.crediya.model.LoggerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogUtil implements LoggerGateway {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message, Throwable t) {
        logger.error(message, t);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void trace(String message) {
        logger.trace(message);
    }
}
