package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которой должны быть помечены поля, которые
 * должны быть целью метода inject класса Inject. Имеет поле
 * с данными класса, экземпляр которого будет инъектирован.
 *
 * @author Alexandr Smirnov
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    /**
     *
     * @return Класс, потомки которого (или сам класс)
     * будут искаться для инъекции
     */
    Class<?> value();
}
