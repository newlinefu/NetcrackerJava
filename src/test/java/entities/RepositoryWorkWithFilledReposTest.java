package entities;

import entities.contracts.Contract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class RepositoryWorkWithFilledReposTest {

    private Repository r;

    @Before
    public void setUp() {
        r = new Repository();
        int[][] datesStartData = new int[][] {
            {2010, 1, 1}, {2011, 1, 1}, {2012, 1, 1}, {2013, 1, 1}, {2014, 1, 1},
            {2015, 1, 1}, {2016, 1, 1}, {2017, 1, 1}, {2018, 1, 1}, {2019, 1, 1}, {2020, 1, 1}
        };
        int[][] datesEndData = new int[][] {
                {2011, 1, 1}, {2012, 1, 1}, {2013, 1, 1}, {2014, 1, 1}, {2015, 1, 1},
                {2016, 1, 1}, {2017, 1, 1}, {2018, 1, 1}, {2019, 1, 1}, {2020, 1, 1}, {2021, 1, 1}
        };
        int[] idData = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Client c = new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ");

        for(int i = 0; i < 11; i++)
            r.add(new Contract(
                    idData[i],
                    LocalDate.of(datesStartData[i][0], datesStartData[i][1], datesStartData[i][2]),
                    LocalDate.of(datesEndData[i][0], datesEndData[i][1], datesEndData[i][2]),
                    c
            ));
    }

    @Test
    public void lengthTest() {
        Assert.assertEquals(11, r.getLength());
    }

    @Test(expected = NoSuchElementException.class)
    public void getNotExisted() {
        r.get(-1).get();
    }

    @Test
    public void getFirst() {
        Contract c = r.get(1).get();
        Assert.assertTrue(
                r.getLength() == 11 &&
                        c.getStartDate().getYear() == 2010 &&
                        c.getEndDate().getYear() == 2011 &&
                        c.getClient().getId() == 0 &&
                        c.getClient().getFullName().equals("Alex Rum")
        );
    }

    @Test
    public void getEnd() {
        Contract c = r.get(11).get();
        Assert.assertTrue(
                r.getLength() == 11 &&
                        c.getStartDate().getYear() == 2020 &&
                        c.getEndDate().getYear() == 2021 &&
                        c.getClient().getId() == 0 &&
                        c.getClient().getFullName().equals("Alex Rum")
        );
    }

    @Test
    public void getMiddle() {
        Contract c = r.get(5).get();
        Assert.assertTrue(
                r.getLength() == 11 &&
                        c.getStartDate().getYear() == 2014 &&
                        c.getEndDate().getYear() == 2015 &&
                        c.getClient().getId() == 0 &&
                        c.getClient().getFullName().equals("Alex Rum")
        );
    }

    @Test
    public void deleteStart() {
        r.delete(1);
        Assert.assertTrue(
                r.getLength() == 10 &&
                        !r.get(1).isPresent()
        );
    }

    @Test
    public void deleteEnd() {
        r.delete(11);
        Assert.assertTrue(
                r.getLength() == 10 &&
                        !r.get(11).isPresent()
        );
    }

    @Test
    public void deleteMiddle() {
        r.delete(5);
        Assert.assertTrue(
                r.getLength() == 10 &&
                        !r.get(5).isPresent()
        );
    }

    @Test
    public void deleteNotExisted() {
        r.delete(-1);
        Assert.assertTrue(r.getLength() == 11);
    }


    @Test
    public void findNoOne() {
        Repository subR = r.find(contract -> contract.getStartDate().getYear() == 1999);
        Assert.assertEquals(subR.getLength(), 0);
    }

    @Test
    public void findAll() {
        Repository subR = r.find(contract -> contract.getId() < 15);
        Assert.assertEquals(subR.getLength(), r.getLength());
    }

    @Test
    public void findByOddId() {
        Repository subR = r.find(contract -> contract.getId() % 2 == 0);
        boolean isPassed = true;

        for(int i = 2, j = 0; i <= 10 && isPassed; i += 2, j++) {
            if(!subR.get(i).isPresent())
                isPassed = false;
        }

        Assert.assertTrue(isPassed && subR.getLength() == 5);
    }

    @Test
    public void sortByNothing() {
        r.sort((c1, c2) -> 0);
        Contract[] repositoryCopyArray = r.getContracts();
        boolean isPassed = true;


        for(int i = 1; i <= 11 && isPassed; i++)
            if(repositoryCopyArray[i - 1].getId() != i)
                isPassed = false;

        Assert.assertTrue(isPassed);
    }

    @Test
    public void sortToInversion() {
        r.sort((c1, c2) -> c2.getId() - c1.getId());
        Contract[] repositoryCopyArray = r.getContracts();
        boolean isPassed = true;

        for(int i = 11, j = 0; j < r.getLength() && isPassed; i--, j++)
            if(repositoryCopyArray[j].getId() != i)
                isPassed = false;

        Assert.assertTrue(isPassed);
    }
}
