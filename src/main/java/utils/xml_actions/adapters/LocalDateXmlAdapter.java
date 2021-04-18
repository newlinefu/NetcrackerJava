package utils.xml_actions.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Класс-адаптер для преобразования даты в xml
 *
 * @author Alexandr Smirnov
 */
public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {
    /**
     *
     * @param s Строка, полученная из xml документа
     * @return Дата
     */
    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s);
    }

    /**
     *
     * @param localDate Дата, которая должна быть переведена в xml
     * @return Строка для записи в xml
     */
    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.toString();
    }
}
