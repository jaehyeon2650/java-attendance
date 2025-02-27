package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    public String getSelection(){
        Scanner scan = new Scanner(System.in);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN);
        String today = formatter.format(now);
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n",today);
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        return scan.nextLine();
    }

    public String getUsernameForAddHistory(){
        System.out.println("닉네임을 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public LocalDateTime getAttendanceTime(){
        System.out.println("등교 시간을 입력해 주세요.");
        LocalDate now  = LocalDate.now();
        LocalDate newDate = LocalDate.of(2024,12,now.getDayOfMonth());
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(input, formatter);
        return LocalDateTime.of(newDate,time);
    }

    public String getUsernameForEditHistory(){
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public LocalDate getEditDay(){
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int day = Integer.parseInt(input);
        return LocalDate.of(2024,12,day);
    }

    public LocalTime getEditTime(){
        System.out.println("언제로 변경하겠습니까?");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);
        return LocalTime.parse(input, formatter);
    }
}
