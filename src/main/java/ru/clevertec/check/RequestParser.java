package main.java.ru.clevertec.check;

import java.util.HashMap;
import java.util.regex.Pattern;

class RequestParser implements Parser {
    String idRegex="\\d+-\\d+";
    String cardRegex="discountCard=\\d{4}";
    String balanceRegex="balanceDebitCard=-?\\d+(.\\d+)?";

    String pathRegex="pathToFile=.*";
    String saveRegex="saveToFile=.*";
    private String pathToFile="";
    private String saveToFile="";
    String[] input;
    private Request request;

    RequestParser(String[] input ){
         this.input=input;
    }

    public Request getRequest() {
        return request;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getSaveToFile() {
        return saveToFile;
    }

    @Override
    public void parse() throws Exception {

        int i=0;
        HashMap<Integer,Integer> items = new HashMap<>();
        Integer discountCard = null;
        Double balanceDebitCard = null;
        while( i<input.length ){

            if(Pattern.matches(idRegex,input[i])){
                String[] idQuantity = input[i].split("-");
                Integer id = Integer.parseInt(idQuantity[0]);
                Integer quantity = Integer.parseInt(idQuantity[1]);
                if(items.containsKey(id)){
                    items.put(id, items.get(id)+ quantity);
                }
                else{
                    items.put(id, quantity);
                }
                i++;
                continue;
            }
            if(Pattern.matches(cardRegex, input[i])){
                discountCard = Integer.parseInt(input[i].substring(input[i].length()-4));
                i++;
                continue;
            }
            if(Pattern.matches(balanceRegex, input[i])){
                String[] balance = input[i].split("=");
                balanceDebitCard = Double.parseDouble(balance[1]);
                i++;
                continue;
            }
            if(Pattern.matches(pathRegex, input[i])){
                String[] path = input[i].split("=");
                pathToFile = path[1];
                i++;
                continue;
            }
            if(Pattern.matches(saveRegex, input[i])){
                String[] save = input[i].split("=");
                saveToFile = save[1];
                i++;
                continue;
            }
            i++;

        }
        if(items.isEmpty() || saveToFile.isEmpty() || pathToFile.isEmpty()){
            if(!saveToFile.isEmpty()){
                throw new ServerException("BAD REQUEST", saveToFile);
            }
            throw new ServerException("BAD REQUEST");
        }
        if(balanceDebitCard != null) {
            request = new Request(items, discountCard, balanceDebitCard);
        }
        else {
            throw new ServerException("NOT ENOUGH MONEY");
        }
    }

    @Override
    public String toString() {
        return "RequestParser{" +
                "request=" + request +
                '}';
    }
}
