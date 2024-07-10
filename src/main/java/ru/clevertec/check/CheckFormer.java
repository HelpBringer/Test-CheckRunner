package main.java.ru.clevertec.check;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CheckFormer {

    private LocalDate checkDate;
    private LocalTime checkTime;

    private List<CashPosition> accountedPositions;

    void formCheck(Request request, List<Position> positions, Double percentage) throws ServerException {

        if(request.getItems().size() != positions.size()){
            throw new ServerException("BAD REQUEST");
        }
        else{

            checkDate = LocalDate.now();
            checkTime = LocalTime.now();

            //check if the quantity required meets the stored one
            try {
                List<CashPosition> list = new ArrayList<>();
                for (Position position : positions) {
                    CashPosition cashPosition = new CashPosition(position, request.getItems().get(position.getId()));
                    list.add(cashPosition);
                }
                accountedPositions = list;
            }
            catch (ServerException serverException){
                throw new ServerException(serverException.getMessage());
            }
        }
        countTotal(accountedPositions, percentage/100);

    }

    void countTotal(List<CashPosition> accountedPositions, Double percentage){
        for (CashPosition position : accountedPositions){
            position.totalCalculation(percentage);
        }
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public LocalTime getCheckTime() {
        return checkTime;
    }

    public List<CashPosition> getAccountedPositions() {
        return accountedPositions;
    }
}
