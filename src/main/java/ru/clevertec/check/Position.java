package main.java.ru.clevertec.check;

public class Position {
    private Integer id;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean discount;

    public Position(Integer id,String description, Double price, Integer quantity, Boolean discount) {
        this.id = id;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.discount = discount;

    }

    public Position(Position position){
        this.id = position.id;;
        this.description = position.description;
        this.price = position.price;
        this.quantity = position.quantity;
        this.discount = position.discount;
    }
    public Position(String[] positionInfo){
        if (positionInfo.length == 5){
            this.id = Integer.parseInt(positionInfo[0]);
            this.description = positionInfo[1];
            this.price = Double.parseDouble(positionInfo[2]);
            this.quantity = Integer.parseInt(positionInfo[3]);
            this.discount = Boolean.parseBoolean(positionInfo[4]);
        }
        else{
            //TODO: handle exception
        }
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                '}'+'\n';
    }
}
