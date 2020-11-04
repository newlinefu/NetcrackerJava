package entities;

import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class RepositoryAddTest {

    private Repository r;
    private Contract contract;
    private int extLength;
    private int extContractStartLocalDateYear;
    private int extContractEndLocalDateYear;
    private int clientId;
    private String extClientFullName;


    public RepositoryAddTest(
            Contract contract,
            int extLength,
            int extContractStartLocalDateYear,
            int extContractEndLocalDateYear,
            int clientId,
            String extClientFullName
    ) {
        this.contract = contract;
        this.extLength = extLength;
        this.extContractStartLocalDateYear = extContractStartLocalDateYear;
        this.extContractEndLocalDateYear = extContractEndLocalDateYear;
        this.clientId = clientId;
        this.extClientFullName = extClientFullName;
    }

    @Before
    public void setUp() {
        r = new Repository();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] params = new Object[][]{
                {
                        new Contract(
                                1,
                                LocalDate.of(2019, 11, 2),
                                LocalDate.of(2020, 3, 2),
                                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2))
                        ),
                        1,
                        2019,
                        2020,
                        0,
                        "Alex Rum"
                },
                {
                        new CellularContract(
                                1,
                                LocalDate.of(2015, 11, 2),
                                LocalDate.of(2016, 3, 2),
                                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2)),
                                3.5,
                                400,
                                400
                        ),
                        1,
                        2015,
                        2016,
                        0,
                        "Alex Rum"
                },
                {
                        new DigitalTelevisionContract(
                                1,
                                LocalDate.of(2015, 11, 2),
                                LocalDate.of(2016, 3, 2),
                                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2)),
                                new String[]{"Ren TV", "TV 3"}
                        ),
                        1,
                        2015,
                        2016,
                        0,
                        "Alex Rum"
                },
                {
                        new WiredInternetContract(
                                1,
                                LocalDate.of(2015, 11, 2),
                                LocalDate.of(2016, 3, 2),
                                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2)),
                                4365
                        ),
                        1,
                        2015,
                        2016,
                        0,
                        "Alex Rum"
                },
                {
                    null,
                    0,
                    0,
                    0,
                    0,
                    null
                }
        };

        return Arrays.asList(params);
    }

    @Test
    public void addTest() {
        r.add(contract);
        if (contract == null)
            Assert.assertEquals(0, extLength);
        else {

            Optional<Contract> optcon = r.get(contract.getId());
            if (!optcon.isPresent())
                fail("Return null");
            else {
                Contract con = optcon.get();
                Assert.assertTrue(
                        extLength == 1 &&
                                extContractStartLocalDateYear == con.getStartDate().getYear() &&
                                extContractEndLocalDateYear == con.getEndDate().getYear() &&
                                clientId == con.getClient().getId() &&
                                extClientFullName.equals(con.getClient().getFullName())
                );
            }
        }
    }
}
