package utils.validators;

/**
 * Класс сообщения валидатора.
 *
 * @author Alexandr Smirnov
 */
public class Message {

    private Status status;
    private String messageBody;

    /**
     * @param status - Статус сообщения
     * @param messageBody - Строковая информация сообщения
     */
    public Message(Status status, String messageBody) {
        this.status = status;
        this.messageBody = messageBody;
    }

    /**
     * @return Строковая информация сообщения
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody - Новая строковая информация сообщения
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * @return - Новый статус сообщения
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status - Статус сообщения
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{"
                + "status="
                + status
                + ", messageBody='"
                + messageBody
                + "\'}";
    }
}
