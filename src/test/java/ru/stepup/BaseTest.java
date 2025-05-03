package ru.stepup;

import lombok.Getter;

import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {
    @Getter
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new RuntimeException("Файл application.properties не найден в classpath");
            }

            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить application.properties", e);
        }
    }

    /**
     * Protected-геттер для доступа к свойствам конфигурации из подклассов.
     */
    protected static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * Если нужно получить весь объект Properties.
     */
    protected static Properties getProperties() {
        return props;
    }
}
