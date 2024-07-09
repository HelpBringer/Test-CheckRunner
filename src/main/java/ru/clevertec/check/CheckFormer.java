package main.java.ru.clevertec.check;

import java.time.LocalDate;
import java.time.LocalTime;
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

            accountedPositions = positions.stream()
                    .map( position -> new CashPosition(position, request.getItems().get(position.getId())))
                    .toList();

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
