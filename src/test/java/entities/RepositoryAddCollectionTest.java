package entities;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.fail;

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
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2)),
                        4365
                ),
                new WiredInternetContract(
                        2,
                        LocalDate.of(2015, 11, 2),
                        LocalDate.of(2016, 3, 2),
                        new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2)),
                        4365
                )
        };

        r.add(contractCollection);
    }
}
