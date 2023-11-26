package tictactoe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BoardUtil {

    public static List<List<String>> createEmpty(int numberOfRows) {
        List<List<String>> rows = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            List<String> row = new ArrayList<>();
            for (int columnIndex = 0; columnIndex < numberOfRows; columnIndex++) {
                row.add(BoardTile.EMPTY.toString());

            }
            rows.add(row);
        }

        return rows;
    }

    public static String getRandomAvailableTile(List<List<String>> rows) {
        List<String> available = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            List<String> row = rows.get(rowIndex);

            for (int columnIndex = 0; columnIndex < rows.size(); columnIndex++) {
                String tileValue = row.get(columnIndex);
                if (tileValue.isEmpty()) {
                    available.add(rowIndex + "-" + columnIndex);
                }
            }
        }

        if (available.isEmpty()) {
            return null;
        }

        int randomNum = new Random().nextInt(available.size());
        return available.get(randomNum);

    }

    public static List<List<String>> getAllLines(int numberOfRows, List<List<String>> rows) {
        List<List<String>> lines = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            lines.add(rows.get(rowIndex));
        }

        for (int columnIndex = 0; columnIndex < numberOfRows; columnIndex++) {
            List<String> columnLine = new ArrayList<>();
            for (List<String> row : rows) {
                columnLine.add(row.get(columnIndex));
            }
            lines.add(columnLine);
        }

        List<String> diagonal1 = new ArrayList<>();
        List<String> diagonal2 = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < numberOfRows; columnIndex++) {
                if (rowIndex == columnIndex) {
                    diagonal1.add(rows.get(rowIndex).get(columnIndex));
                }

                if (rowIndex + columnIndex == numberOfRows - 1) {
                    diagonal2.add(rows.get(rowIndex).get(columnIndex));
                }
            }
        }

        lines.add(diagonal1);
        lines.add(diagonal2);

        return lines;
    }
}
