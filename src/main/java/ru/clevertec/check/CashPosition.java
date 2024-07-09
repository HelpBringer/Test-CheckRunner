package main.java.ru.clevertec.check;

import java.text.DecimalFormat;

public class CashPosition extends Position{

    private Double total;
    private Double discountPrice = this.getDiscount()? 1.0 : (double) 0;

    private  final double WHOLESALE_PERCENTAGE = 0.1;
    private final double SCALE = Math.pow(10,2);

    public CashPosition(Integer id, String description, Double price, Integer quantity, Boolean discount) {
        super(id, description, price, quantity, discount);
    }


    public CashPosition(Position position, Integer requiredQuantity) {
        super(position);
        if( this.getQuantity() >= requiredQuantity){
            this.setQuantity(requiredQuantity);
        }
        else{
            this.setQuantity(0);
            System.out.println("Not enough products"+position.getDescription());
        }
    }

    void totalCalculation(Double percentage){
        this.total = Math.round(this.getQuantity()*this.getPrice()* SCALE)/ SCALE;
        if(this.getQuantity() >= 5){
            this.discountPrice = Math.round(this.total * discountPrice * WHOLESALE_PERCENTAGE * SCALE) / SCALE;
        }
        else {
            this.discountPrice = Math.round(this.total * discountPrice * percentage * SCALE) / SCALE;
        }
    }

    public Double getTotal() {
        return total;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    @Override
    public String toString() {
        return getQuantity()+";"
                +getDescription()+";"
                +new DecimalFormat("#0.00$").format(getPrice())+";"
                +new DecimalFormat("#0.00$").format(getDiscountPrice())+";"
                +new DecimalFormat("#0.00$").format(getTotal());

    }


}
