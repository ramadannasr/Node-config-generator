package pl.helwan.fci.logger;

public interface Logger {

    void logInfo(String msg);

    void logError(Exception e, String msg);

    void logError(String msg);
}
