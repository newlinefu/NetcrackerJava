package entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Сущностный класс клиента
 * @author Alexandr Smirnov
 */
public class Client {

    private int id;
    private String fullName;
    private String passport;
    private LocalDate birth;
    private String gender;

    /**
     * @return ID клиента
     */
    public int getId() {
        return id;
    }

    /**
     * @param id - Новый ID клиента
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Полное имя клиента
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName - Новое полное имя клиента
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return Данные о паспорте в строковом формате
     */
    public String getPassport() {
        return passport;
    }

    /**
     *
     * @param passport - Новые данные паспорта в строковом формате
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     *
     * @return Дата рождения клиента
     */
    public LocalDate getBirth() {
        return birth;
    }

    /**
     *
     * @return Колличество полных лет, прошедших с момента рождения клиента
     */
    public int getAge() {
        return Period.between(LocalDate.now(), birth).getYears();
    }

    /**
     *
     * @param birth - Измененная дата рождения клиента
     */
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    /**
     *
     * @return Пол клиента в строковом формате
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender - Измененный пол клиента в строковом формате
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @param id - ID Клиента в системе
     * @param fullName - Полное имя клиента
     * @param passport - Данные о паспорте в строковом формате
     * @param birth - Дата рождения
     */
    public Client(int id, String fullName, String passport, LocalDate birth, String gender) {
        this.id = id;
        this.fullName = fullName;
        this.passport = passport;
        this.birth = birth;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Client: id=( " + id + " ) | " +
                "fullName=( " + fullName + " ) | " +
                "passport=( " + passport + " ) | birth=( " + birth + " ) | " +
                "gender=( " + gender + " ) | " +
                "age=( " + getAge() + " ) ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Client client = (Client) o;
        return  fullName.equals(client.fullName) &&
                passport.equals(client.passport) &&
                birth.equals(client.birth) &&
                gender.equals(client.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, passport, birth, gender);
    }
}
