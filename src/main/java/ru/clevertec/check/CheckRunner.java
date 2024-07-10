package main.java.ru.clevertec.check;


//java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx.csv saveToFile=xxx.csv

public class CheckRunner {

    private static final String FILE_PATH = "result.csv";
    static String productsPath;
    static String discountCardsPath = "./src/main/resources/discountCards.csv";
    static String resultPath = FILE_PATH;

    public static void main(String[] args){

        //readRequest();
        RequestParser parsedData = new RequestParser(args);
        try {
            parsedData.parse();
            System.out.println(parsedData);
            productsPath = parsedData.getPathToFile();
            resultPath = parsedData.getSaveToFile();

            //readProductData();
            ProductParser productsData = new ProductParser(productsPath, parsedData.getRequest().getItems().keySet() );
            productsData.parse();
            System.out.println(productsData);

            //readDiscountCard();
            DiscountParser discountParser = new DiscountParser(discountCardsPath, parsedData.getRequest().getDiscountCard());
            discountParser.parse();
            System.out.println(discountParser);

            //formCheck();
            CheckFormer checkFormer = new CheckFormer();
            checkFormer.formCheck(parsedData.getRequest(), productsData.getPositions(), discountParser.getDiscountPercentage());
            //writeCheck();

            CheckWriter checkWriter = new CheckWriter(resultPath);
            checkWriter.writeToCsv(checkFormer.getCheckDate(), checkFormer.getCheckTime(), checkFormer.getAccountedPositions(),
                    discountParser.getDiscountCard(), parsedData.getRequest());
        }
        catch (ServerException serverException){
            CheckWriter checkWriter;
            //write error message to file
            if(serverException.getResultPath().isEmpty()) {
                checkWriter = new CheckWriter(resultPath);
            }
            else{
                checkWriter = new CheckWriter(serverException.getResultPath());
            }
            checkWriter.writeException(serverException.getMessage());
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            CheckWriter checkWriter = new CheckWriter(resultPath);
            checkWriter.writeException(exception.getMessage());
        }

        System.out.println("Executed successfully");
    }



}
