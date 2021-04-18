package entities;

import annotations.Inject;
import entities.contracts.Contract;
import utils.sorters.ISorter;
import utils.sorters.bubble.BubbleSorter;
import utils.sorters.heap.HeapSorter;

import javax.xml.bind.annotation.*;
import java.util.Comparator;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Сущностный класс хранилища различных контрактов.
 *
 * @author Alexandr Smirnov
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "repository")
public class Repository {

    @XmlElement(name = "contract")
    private Contract[] contracts;

    @XmlElement
    private int actualFinish = 0;

    @Inject(ISorter.class)
    @XmlElements({@XmlElement(type = BubbleSorter.class), @XmlElement(type = HeapSorter.class)})
    private ISorter<Contract> sorter;

    /**
     * @value - Изначальная длина репозитория
     */
    @XmlTransient
    private static final int PRIMARY_LENGTH = 10;

    /**
     *
     * @param contracts - Массив контрактов для формирования репозитория
     */
    public Repository(Contract[] contracts) {
        this.contracts = contracts;
        actualFinish = contracts.length;
    }

    /**
     * Конструктор создания пустого репозитория с изначальной длиной primaryLength.
     *
     */
    public Repository() {
        this.contracts = new Contract[PRIMARY_LENGTH];
    }


    /**
     *
     * @return - Актуальная длина репозитория
     */
    public int getLength() {
        return actualFinish;
    }

    /**
     *
     * @return Копия массива контрактов исходного репозитория
     */
    public Contract[] getContracts() {
        Contract[] returnedContracts = new Contract[actualFinish];
        System.arraycopy(contracts, 0, returnedContracts, 0, actualFinish);
        return returnedContracts;
    }

    /**
     *
     * @param contractId - ID контракта, который должен быть удален
     */
    public void delete(int contractId) {
        deleteByContractId(contractId);
    }

    /**
     *
     * @param contract - Добавляемый контракт
     */
    public void add(Contract contract) {
        addWithCheck(contract);
    }

    /**
     *
     * @param contractsCollection - Массив добавляемых контрактов
     */
    public void add(Contract[] contractsCollection) {
        addContractsCollection(contractsCollection);
    }

    /**
     *
     * @param contractId - ID контракта, который необходимо извлечь
     * @return Экземпляр Optional<Contract>, содержащий искомые данные констракта
     */
    public Optional<Contract> get(int contractId) {
        return getContract(contractId);
    }

    private void addWithCheck(Contract contract) {
        if(contract != null) {
            if(isFullness()) {
                Contract[] copiedContracts = new Contract[(int) (contracts.length * 1.5)];
                System.arraycopy(contracts, 0, copiedContracts, 0, contracts.length);
                contracts = copiedContracts;
            }
            contracts[actualFinish++] = contract;
        }
    }

    private void addContractsCollection(Contract[] contractsCollection) {
        if(contractsCollection != null) {
            if(contracts.length < contracts.length + contractsCollection.length) {
                Contract[] copiedContracts = new Contract[(int)(1.5 * (contracts.length + contractsCollection.length))];
                System.arraycopy(contracts, 0, copiedContracts, 0, contracts.length);
                contracts = copiedContracts;
            }
            System.arraycopy(contractsCollection, 0, contracts, actualFinish, contractsCollection.length);
            actualFinish += contractsCollection.length;
        }
    }

    private void deleteByContractId(int contractId) {
        for(int i = 0; i < actualFinish; i++) {
            if(contracts[i].getId() == contractId) {
                if(i != actualFinish - 1) {
                    System.arraycopy(contracts, i + 1, contracts, i, actualFinish - i);
                }
                contracts[actualFinish - 1] = null;
                actualFinish--;
                return;
            }
        }
    }

    private Optional<Contract> getContract(int contractId) {
        for(int i = 0; i < actualFinish; i++) {
            if(contractId == contracts[i].getId()) {
                return Optional.ofNullable(contracts[i]);
            }
        }
        return Optional.empty();
    }

    private boolean isFullness() {
        return (double)(contracts.length * 2 / 3) < actualFinish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Repository:\n");
        for(int i = 0; i < actualFinish; i++) {
            sb.append("\t").append(contracts[i].toString()).append("\n");
        }

        return sb.toString();
    }


    /**
     *
     * @param comparator - Параметр сортировки контрактов репозитория
     */
    public void sort(Comparator<Contract> comparator) {
        System.arraycopy(sorter.sort(comparator, getContracts()), 0, contracts, 0, actualFinish);
    }

    /**
     *
     * @param predicate - Predicate, определяющий условие вычленения частей репозитория
     * @return Новый репозиторий, содержащий только контракты, удовлетворяющие предикату
     */
    public Repository find(Predicate<Contract> predicate) {
        Repository subRepos = new Repository();
        for(int i = 0; i < actualFinish; i++) {
            if(predicate.test(contracts[i])) {
                subRepos.add(contracts[i]);
            }
        }
        return subRepos;
    }
}
