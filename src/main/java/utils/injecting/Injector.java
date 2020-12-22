package utils.injecting;

import annotations.Config;
import annotations.Inject;
import com.google.common.reflect.ClassPath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Класс, производящий инъекции экземпляров классов, искомых через
 * аннотацию Inject. Поиск нужных классов идет из поля locations в аннотации
 * Config.
 *
 * @author Alexandr Smirnov
 */
@Config(locations = {"utils.sorters.bubble", "utils.validators.validator_examples"})
public class Injector {

    private static Logger logger = LogManager.getLogger("CSV convert");

    /**
     * Метод, который находит среди пакетов из списка параметров Config классы,
     * соответствующие пометке Inject на полях входящего объекта.
     *
     * @param injectingTarget - Объект, в который проводится инъекция
     * @param <T> - Тип инъектируемого объекта
     * @throws InjectException - Ошибка инъекции
     */
    public static <T> void inject(T injectingTarget) throws InjectException {

        logger.info("Start injecting");

        //Проверка наличия искомых пакетов
        if (Injector.class.isAnnotationPresent(Config.class)) {
            try {

                //Поиск всех полей в инъектируемом классе
                Field[] fields = injectingTarget.getClass().getDeclaredFields();

                for (Field f : fields) {
                    f.setAccessible(true);
                    fieldInject(f, injectingTarget);
                }

            } catch (IOException
                    | IllegalAccessException
                    | ClassNotFoundException
                    | InvocationTargetException
                    | InstantiationException e) {

                logger.info(e);
                throw new InjectException(e);
            }
        } else {
            logger.info("Config annotation not found");
        }
    }

    private static <T> void fieldInject(Field f, T injectingTarget)
            throws IOException, IllegalAccessException,ClassNotFoundException,
            InvocationTargetException, InstantiationException {

        //Если поле помечено аннотацией - производится инъекция
        if (f.isAnnotationPresent(Inject.class)) {

            logger.info(f.getName()
                    + " field in "
                    + injectingTarget.getClass().getSimpleName()
                    + " with Inject annotation");

            //Из аннотации поля берем класс, который будем искать
            Class<?> injectedClassType = f.getAnnotation(Inject.class).value();

            //Берем список всех путей к пакетам, из которых будем искать
            //вышеописанные классы
            Config conf = Injector.class.getAnnotation(Config.class);
            String[] configLocations = conf.locations();

            //Общее число полей из всех классов. Если всего 0 =>
            //выкидывается ошибка
            int quantityOfFoundedClasses = 0;

            for (String s : configLocations) {

                //Находим список всех классов, наследуемых от искомого класса
                ClassPath cp = ClassPath.from(Injector.class.getClassLoader());
                Set<ClassPath.ClassInfo> allClasses = cp.getTopLevelClasses(s);
                List<Class<?>> filteredClasses = new ArrayList<>();


                for(ClassPath.ClassInfo c : allClasses) {

                    Class<?> foundedClass = Class.forName(c.getName());

                    if(injectedClassType.isAssignableFrom(foundedClass)) {
                        filteredClasses.add(foundedClass);
                        quantityOfFoundedClasses++;
                    }
                }

                if(List.class.isAssignableFrom(f.getType()) && filteredClasses.size() > 0) {

                    listInject(f, injectingTarget, filteredClasses);

                } else if(filteredClasses.size() == 1) {

                    simpleFieldInject(f, injectingTarget, filteredClasses.get(0));

                } else if(filteredClasses.size() > 0) {
                    throw new InjectException("Trying to inject [ " + filteredClasses.size() + " ] fields in non List field");
                }
            }
            if(quantityOfFoundedClasses == 0) {
                throw new InjectException("Trying to inject [ 0 ] fields");
            }
        }
    }

    private static <T> void listInject(Field f, T injectingTarget, List<Class<?>> filteredClasses)
            throws  IllegalAccessException, InvocationTargetException, InstantiationException {

        List validators = (List) f.get(injectingTarget);

        for (Class<?> c : filteredClasses) {
            Constructor constructor = c.getConstructors()[0];
            Object o = constructor.newInstance();

            validators.add(o);
        }
    }

    private static <T> void simpleFieldInject(Field f, T injectingTarget, Class<?> foundedClass)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor constructor = foundedClass.getConstructors()[0];
        Object o = constructor.newInstance();

        f.set(injectingTarget, o);
    }
}
