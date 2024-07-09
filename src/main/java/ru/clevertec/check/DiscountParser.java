package main.java.ru.clevertec.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class DiscountParser implements Parser{
    String inputPath;
    Integer searchCard;
    private DiscountCard discountCard = new DiscountCard( (double)0,0);

    public DiscountParser(String inputPath, Integer searchCard) {
        this.inputPath = inputPath;
        this.searchCard = searchCard;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Double getDiscountPercentage() {
        return discountCard.getPercentage();
    }

    public void setDiscountCard(Double discountCard) {
        this.discountCard.setPercentage(discountCard);
    }

    @Override
    public void parse() throws Exception {
        if(searchCard == null){
            return;
        }
        Optional<Double> percentage;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath))) {
            percentage = reader.lines()
                    .filter(line -> line.matches("\\d+.*"))
                    .filter(line -> line.contains(Integer.toString(searchCard )))
                    .map(line -> Double.parseDouble(line.substring(line.lastIndexOf(";")+1)))
                    .findFirst();
        } catch (IOException e) {
            throw new ServerException("INTERNAL SERVER ERROR");
        }
        percentage.ifPresent(this::setDiscountCard);
        if (percentage.isPresent()){
            this.discountCard.setCardId(searchCard);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "DiscountParser{" +
                "discountCard=" + discountCard +
                '}';
    }
}
