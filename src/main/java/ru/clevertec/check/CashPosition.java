package main.java.ru.clevertec.check;

import java.text.DecimalFormat;

//Extension of Position class to correctly work with calculation
public class CashPosition extends Position{

    private Double total;
    //checking if discount is applicable
    private Double discountPrice = this.getDiscount()? 1.0 : (double) 0;

    private final int WHOLESALE_NUMBER = 5;
    private  final double WHOLESALE_PERCENTAGE = 0.1;
    //precision
    private final double SCALE = Math.pow(10,2);

    public CashPosition(Integer id, String description, Double price, Integer quantity, Boolean discount) {
        super(id, description, price, quantity, discount);
    }


    public CashPosition(Position position, Integer requiredQuantity) throws ServerException {
        super(position);
        if( this.getQuantity() >= requiredQuantity){
            this.setQuantity(requiredQuantity);
        }
        else{
            this.setQuantity(0);
            System.out.println("Not enough products"+position.getDescription());
            throw new ServerException("BAD REQUEST");
        }
    }

    void totalCalculation(Double percentage){
        this.total = Math.round(this.getQuantity()*this.getPrice()* SCALE)/ SCALE;
        //Wholesale or discount
        if(this.getQuantity() >= WHOLESALE_NUMBER){
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
