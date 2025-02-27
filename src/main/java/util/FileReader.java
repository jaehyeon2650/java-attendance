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

public class FileReader {

    public static Map<String, List<LocalDateTime>> getCrewsAttendanceHistories() {
        Map<String,List<LocalDateTime>> allAttendanceResult = new HashMap<>();
        try(Scanner scanner = new Scanner(new File("src/main/resources/attendances.csv"))){
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String input = scanner.nextLine();
                String[] splitResult = input.split(",");
                String username = splitResult[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime attendanceTime = LocalDateTime.parse(splitResult[1], formatter);
                addHistories(allAttendanceResult, username, attendanceTime);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("[ERROR] 파일 경로가 잘못 되었습니다.");
        }
        return allAttendanceResult;
    }

    private static void addHistories(Map<String, List<LocalDateTime>> allAttendanceResult, String username,
                                  LocalDateTime attendanceTime) {
        if(allAttendanceResult.containsKey(username)){
            List<LocalDateTime> histories = allAttendanceResult.get(username);
            histories.add(attendanceTime);
            return;
        }
        List<LocalDateTime> histories = new ArrayList<>(List.of(attendanceTime));
        allAttendanceResult.put(username,histories);
    }
}
