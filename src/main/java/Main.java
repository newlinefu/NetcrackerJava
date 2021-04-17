import entities.Client;
import entities.Repository;
import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import utils.db_actions.DataBaseRepository;

import java.time.LocalDate;

public class Main {

    /**
     * Точка входа в приложение.
     * @param args -//-
     */
    public static void main(String[] args){
        DataBaseRepository dbr = new DataBaseRepository();
//        Repository r = new Repository();
//        r.add(new WiredInternetContract(
//                1,
//                LocalDate.of(2015, 11, 2),
//                LocalDate.of(2016, 3, 2),
//                new Client(0, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
//                4365
//        ));
//        r.add(new CellularContract(
//                2,
//                LocalDate.of(2015, 11, 2),
//                LocalDate.of(2016, 3, 2),
//                new Client(1, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
//                3.5,
//                400,
//                400
//        ));
//        r.add(new DigitalTelevisionContract(
//                3,
//                LocalDate.of(2015, 11, 2),
//                LocalDate.of(2016, 3, 2),
//                new Client(2, "Alex Rum", "123", LocalDate.of(1995, 3, 2), "МУЖ"),
//                new String[]{"Ren TV", "TV 3"}
//        ));
//
//        dbr.save(r);
        Repository r = dbr.restore();
        System.out.print(r);

    }
}
