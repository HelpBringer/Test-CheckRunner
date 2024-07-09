package main.java.ru.clevertec.check;


//java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx

public class CheckRunner {

    static String productsPath = "./src/main/resources/products.csv";
    static String discountCardsPath = "./src/main/resources/discountCards.csv";


    public static void main(String[] args){

        //readCheck();
        RequestParser parsedData = new RequestParser(args);
        try {
            parsedData.parse();
            System.out.println(parsedData);

            ProductParser productsData = new ProductParser(productsPath, parsedData.getRequest().getItems().keySet() );
            productsData.parse();
            System.out.println(productsData);

            DiscountParser discountParser = new DiscountParser(discountCardsPath, parsedData.getRequest().getDiscountCard());
            discountParser.parse();
            System.out.println(discountParser);

            //formCheck();
            CheckFormer checkFormer = new CheckFormer();
            checkFormer.formCheck(parsedData.getRequest(), productsData.getPositions(), discountParser.getDiscountPercentage());
            //writeCheck();

            CheckWriter checkWriter = new CheckWriter();
            checkWriter.writeToCsv(checkFormer.getCheckDate(), checkFormer.getCheckTime(), checkFormer.getAccountedPositions(),
                    discountParser.getDiscountCard(), parsedData.getRequest());
        }
        catch (ServerException serverException){
            CheckWriter checkWriter = new CheckWriter();
            checkWriter.writeException(serverException.getMessage());
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            CheckWriter checkWriter = new CheckWriter();
            checkWriter.writeException(exception.getMessage());
        }

        System.out.println("Executed successfully");
    }



}
