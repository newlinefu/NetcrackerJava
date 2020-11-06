package entities;

import java.util.Optional;

/**
 * Сущностный класс хранилища различных контрактов
 * @author Alexandr Smirnov
 */
public class Repository {

    private Contract[] contracts;
    private int actualFinish = 0;

    /**
     * @value - Изначальная длина репозитория
     */
    private static final int primaryLength = 10;

    /**
     *
     * @param contracts - Массив контрактов для формирования репозитория
     */
    public Repository(Contract[] contracts) {
        this.contracts = contracts;
        actualFinish = contracts.length;
    }

    /**
     * Конструктор создания пустого репозитория с изначальной длиной primaryLength
     */
    public Repository() {
        this.contracts = new Contract[primaryLength];
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
     * @return - Актуальная длина репозитория
     */
    public int getLength() {
        return actualFinish;
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
                if(i != actualFinish - 1)
                    System.arraycopy(contracts, i + 1, contracts, i, actualFinish - i);
                contracts[actualFinish - 1] = null;
                actualFinish--;
                return;
            }
        }
    }

    private Optional<Contract> getContract(int contractId) {
        for(int i = 0; i < actualFinish; i++) {
            if(contractId == contracts[i].getId())
                return Optional.ofNullable(contracts[i]);
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
        for(int i = 0; i < actualFinish; i++)
            sb.append("\t").append(contracts[i].toString()).append("\n");

        return sb.toString();
    }
}
