package co.com.crediya.model;

public interface LoggerGateway {
    void info(String message);
    void error(String message, Throwable t);
    void debug(String message);
    void warn(String message);
    void trace(String message);
}