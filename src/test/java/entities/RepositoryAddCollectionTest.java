package entities;


import entities.contracts.Contract;
import entities.contracts.WiredInternetContract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class RepositoryAddCollectionTest {

    private Repository r;

    @Before
    public void setUp() {
        r = new Repository();
    }


    @Test
    public void addEmptyCollectionTest() {
        Contract[] contractCollection = null;
        r.add(contractCollection);
        Assert.assertEquals(r.getLength(), 0);
    }

    @Test
    public void addSmallCollectionTest() {
        Contract[] contractCollection = new Contract[] {
                new WiredInternetContract(
                        1,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        2,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                )
        };

        r.add(contractCollection);
        Contract[] reposContracts = r.getContracts();

        boolean isPassed = true;
        for(int i = 1; i <= 2 && isPassed; i++)
            if(reposContracts[i - 1].getId() != i)
                isPassed = false;

        Assert.assertTrue(
                isPassed && r.getLength() == 2
        );
    }

    @Test
    public void addLargeCollectionTest() {
        Contract[] contractCollection = new Contract[] {
                new WiredInternetContract(
                        1,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        2,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        3,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        4,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        5,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        6,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        7,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        8,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        9,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        10,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                ),
                new WiredInternetContract(
                        11,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
                        4365
                )
        };

        r.add(contractCollection);
        Contract[] reposContracts = r.getContracts();

        boolean isPassed = true;
        for(int i = 1; i <= 11 && isPassed; i++)
            if(reposContracts[i - 1].getId() != i)
                isPassed = false;

        Assert.assertTrue(
                isPassed && r.getLength() == 11
        );
    }
}
