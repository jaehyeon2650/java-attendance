package util;

import java.io.File;
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
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String DELIMITER = ",";

    public static Map<String, List<LocalDateTime>> loadAttendanceData() {
        Map<String, List<LocalDateTime>> crewsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(FILE))) {
            readLines(scanner, crewsMap);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return crewsMap;
    }

    private static void readLines(Scanner scanner, Map<String, List<LocalDateTime>> crewsMap) {
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            processLine(line, crewsMap);
        }
    }

    private static void processLine(String line, Map<String, List<LocalDateTime>> crewsMap) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식입니다: " + line);
        }
        String username = parts[0].trim();
        LocalDateTime dateTime = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        if (!crewsMap.containsKey(username)) {
            crewsMap.put(username, new ArrayList<>());
        }
        crewsMap.get(username).add(dateTime);
    }
}
