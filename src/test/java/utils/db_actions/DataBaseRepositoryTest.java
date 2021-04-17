package utils.db_actions;

import entities.Client;
import entities.Repository;
import entities.contracts.CellularContract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseRepositoryTest extends TestCase {

    @Test
    void restore() {
        DataBaseRepository dbr = new DataBaseRepository();
        Repository reposPrimary = new Repository();
        reposPrimary.add(new WiredInternetContract(
                1,
                LocalDate.of(2015, 11, 2),
                LocalDate.of(2016, 3, 2),
                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                4365
        ));
        reposPrimary.add(new CellularContract(
                2,
                LocalDate.of(2015, 11, 2),
                LocalDate.of(2016, 3, 2),
                new Client(1, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                3.5,
                400,
                400
        ));
        reposPrimary.add(new DigitalTelevisionContract(
                3,
                LocalDate.of(2015, 11, 2),
                LocalDate.of(2016, 3, 2),
                new Client(2, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                new String[]{"Ren TV", "TV 3"}
        ));

        dbr.save(reposPrimary);
        Repository reposRestored = dbr.restore();

        assertEquals(reposPrimary.getLength(), reposRestored.getLength());
    }
}