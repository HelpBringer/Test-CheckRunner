package main.java.ru.clevertec.check;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckWriter  {

    File file;
    final String TIME_HEADER = "Date;Time";
    final String DESCRIPTION_HEADER = "QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL";
    final String TOTAL_HEADER = "TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT";
    final String DISCOUNT_HEADER = "DISCOUNT CARD;DISCOUNT PERCENTAGE";
    final String DECIMAL_FORMAT = "#0.00$";

    final String DATE_FORMAT ="dd.MM.yyyy";
    final String TIME_FORMAT = "HH:mm:ss";


    final String DEFAULT_SEPARATOR = ";";

    public CheckWriter(String pathToSave){
        this.file = new File(pathToSave);
    }

    public String convertToCsvFormat(String[] line) {
        return String.join(DEFAULT_SEPARATOR, line);
    }


    void writeToCsv(LocalDate localDate, LocalTime localTime, List<CashPosition> positions, DiscountCard discountCard, Request request) throws Exception {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            //TIME
            writeTime(bufferedWriter, localDate, localTime);

            //PRODUCTS DESCRIPTION
            ArrayList<Double> totalResult=writeProducts(bufferedWriter, positions);
            Double totalPrice = totalResult.get(0);
            Double totalDiscount = totalResult.get(1);

            //DISCOUNT
            if( !discountCard.getCardId().equals(0) ){
                writeDiscount(bufferedWriter, discountCard);
            }

            //TOTAL
            if ( (totalPrice - totalDiscount) <= request.getBalanceDebitCard() ) {
                writeTotal(bufferedWriter, totalPrice, totalDiscount);
            }
            else{
                throw new ServerException("NOT ENOUGH MONEY");
            }

        }
        catch (IOException exception){
            throw new ServerException("INTERNAL SERVER ERROR");
        }
        catch (ServerException serverException){
            throw new ServerException(serverException.getMessage());
        }
        catch (Exception e) {
            throw new Exception(e);
        }

    }

    public void writeTime(BufferedWriter bufferedWriter, LocalDate localDate, LocalTime localTime) throws IOException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(TIME_FORMAT);

        bufferedWriter.write(TIME_HEADER);
        bufferedWriter.newLine();
        bufferedWriter.write(dateFormat.format(localDate)+DEFAULT_SEPARATOR+timeFormat.format(localTime));
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        System.out.println(TIME_HEADER);
        System.out.println(dateFormat.format(localDate)+DEFAULT_SEPARATOR+timeFormat.format(localTime)+'\n');
    }

    public ArrayList<Double> writeProducts(BufferedWriter bufferedWriter, List<CashPosition> positions) throws IOException {
        Double totalPrice = (double) 0;
        Double totalDiscount = (double) 0;
        bufferedWriter.write(DESCRIPTION_HEADER);
        bufferedWriter.newLine();

        System.out.println(DESCRIPTION_HEADER);

        for (CashPosition line : positions) {
            totalPrice += line.getTotal();
            totalDiscount += line.getDiscountPrice();
            bufferedWriter.write(line.toString());
            bufferedWriter.newLine();

            System.out.println(line);
        }
        bufferedWriter.newLine();

        System.out.println();
        ArrayList<Double> result = new ArrayList<>();
        result.add(totalPrice);
        result.add(totalDiscount);
        return result;
    }

    public void writeDiscount(BufferedWriter bufferedWriter, DiscountCard discountCard) throws IOException {
        bufferedWriter.write(DISCOUNT_HEADER);
        bufferedWriter.newLine();
        bufferedWriter.write(discountCard.getCardId()+DEFAULT_SEPARATOR+ discountCard.getPercentage().intValue()+"%");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        System.out.println(DISCOUNT_HEADER);
        System.out.println(discountCard.getCardId()+DEFAULT_SEPARATOR+ discountCard.getPercentage().intValue()+"%\n");
    }

    public void writeTotal(BufferedWriter bufferedWriter, Double totalPrice, Double totalDiscount) throws IOException {
        bufferedWriter.write(TOTAL_HEADER);
        bufferedWriter.newLine();
        bufferedWriter.write(new DecimalFormat(DECIMAL_FORMAT).format(totalPrice)+DEFAULT_SEPARATOR
                +new DecimalFormat(DECIMAL_FORMAT).format(totalDiscount)+DEFAULT_SEPARATOR
                +new DecimalFormat(DECIMAL_FORMAT).format(totalPrice-totalDiscount));


        System.out.println(TOTAL_HEADER);
        System.out.println(new DecimalFormat(DECIMAL_FORMAT).format(totalPrice)+DEFAULT_SEPARATOR
                +new DecimalFormat(DECIMAL_FORMAT).format(totalDiscount)+DEFAULT_SEPARATOR
                +new DecimalFormat(DECIMAL_FORMAT).format(totalPrice-totalDiscount));
    }

    public void writeException(String errorMessage){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            System.out.println("ERROR\n"+errorMessage);
            bufferedWriter.write("ERROR");
            bufferedWriter.newLine();
            bufferedWriter.write(errorMessage);

        }
        catch (IOException ioException){
            System.out.println("INTERNAL SERVER ERROR");
        }
    }
}
