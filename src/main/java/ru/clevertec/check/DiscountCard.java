package main.java.ru.clevertec.check;

public class DiscountCard {
    private Double percentage;
    private Integer cardId;

    public DiscountCard(Double percentage, Integer cardId) {
        this.percentage = percentage;
        this.cardId = cardId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "cardId=" + cardId +
                ", percentage=" + percentage +
                '}';
    }
}
