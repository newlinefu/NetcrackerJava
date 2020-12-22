package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация, предназначенная для передачи имен искомых пакетов.
 *
 * @author Alexandr Smirnov
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     *
     * @return Список имен пакетов
     */
    String[] locations();
}
