package pl.helwan.fci.logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomLoggerFactory {

    private static CustomLoggerFactory INSTANCE = new CustomLoggerFactory();

    @Getter @Setter private Logger logger;

    public static CustomLoggerFactory getInstance() {
        return INSTANCE;
    }
}
