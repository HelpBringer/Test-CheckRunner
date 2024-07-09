package main.java.ru.clevertec.check;

import java.util.HashMap;

public class Request {

    private HashMap<Integer,Integer> items = new HashMap<>();
    private Integer discountCard;
    private Double balanceDebitCard;

    public Request(HashMap<Integer, Integer> items, Integer discountCard, Double balanceDebitCard ) {
        this.discountCard = discountCard;
        this.balanceDebitCard = balanceDebitCard;
        this.items = items;
    }

    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    public Integer getDiscountCard() {
        return discountCard;
    }

    public Double getBalanceDebitCard() {
        return balanceDebitCard;
    }

    @Override
    public String toString() {
        return "Request{" +
                "items=" + items +
                ", discountCard=" + discountCard +
                ", balanceDebitCard=" + balanceDebitCard +
                '}';
    }
}

