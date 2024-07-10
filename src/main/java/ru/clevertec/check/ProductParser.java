package main.java.ru.clevertec.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProductParser implements Parser{

    String inputPath;
    //set of products from request
    Set positionsToCheck;
    private List<Position> positions;

    public ProductParser(String inputPath, Set positionsToCheck) throws Exception {
        if(inputPath != null) {
            this.inputPath = inputPath;
            this.positionsToCheck = positionsToCheck;
        }
        else{
            throw new Exception("BAD REQUEST");
        }
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public void parse() throws Exception {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath))) {
            positions = reader.lines()
                    .filter(line -> line.matches("\\d+.*"))
                    .filter(line -> positionsToCheck.contains(Integer.parseInt( line.substring(0, line.indexOf(";")))))
                    .map(line -> new Position(line.split(";")))
                    .toList();
        } catch (IOException e) {
            throw new ServerException("INTERNAL SERVER ERROR");
        }

        System.out.println();
    }

    @Override
    public String toString() {
        return "ProductParser{" +
                "positions=\n" + positions.toString() +
                ", inputPath='" + inputPath + '\'' +
                '}';
    }
}
