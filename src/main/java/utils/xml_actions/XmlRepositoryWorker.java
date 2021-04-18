package utils.xml_actions;

import entities.Repository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.interfaces.IRepositoryWorker;

import javax.xml.bind.*;
import java.io.File;

/**
 * Класс для преобразования данных репозиториев в XML и обратно
 *
 * @author Alexandr Smirnov
 */
public class XmlRepositoryWorker implements IRepositoryWorker {

    private static final String XML_FILE_PATH = "repository.xml";
    private static Logger logger = LogManager.getLogger("XML logger");
    private final JAXBContext jc;

    public XmlRepositoryWorker() throws JAXBException {
        this.jc = JAXBContext.newInstance(Repository.class);
    }


    /**
     * @param repos - Репозиторий для сохранения
     */
    @Override
    public void save(Repository repos) {
        try {
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(repos, new File(XML_FILE_PATH));
        } catch (JAXBException e) {
            logger.error(e);
        }
    }

    /**
     * @return Загруженный репозиторий
     */
    @Override
    public Repository restore() {
        Repository repository = null;
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            repository = (Repository) unmarshaller.unmarshal(new File(XML_FILE_PATH));
        } catch (JAXBException e) {
            logger.error(e);
        }
        return repository;
    }
}
