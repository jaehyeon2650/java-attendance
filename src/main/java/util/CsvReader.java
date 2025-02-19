package util;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvReader {
    private static final String FILE = "src/main/resources/attendances.csv";

    public static Map<String, List<LocalDateTime>>  loadAttendanceData() {
        Map<String, List<LocalDateTime>> crewsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new java.io.File(FILE))) {
            boolean isFirstLine = true;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식입니다: " + line);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String username = parts[0].trim();
                LocalDateTime dateTime = LocalDateTime.parse(parts[1].trim(),formatter);
                List<LocalDateTime> value = crewsMap.getOrDefault(username, new ArrayList<>());
                value.add(dateTime);
                if(!crewsMap.containsKey(username)){
                   crewsMap.put(username,value);
               }
            }
            return crewsMap;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
