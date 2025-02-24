package controller;

import static util.Convertor.changeStandardLocalDate;
import static util.Convertor.changeStandardLocalDateTime;

import constants.SelectionOption;
import domain.AttendanceHistory;
import domain.AttendanceRecord;
import domain.Crew;
import domain.Crews;
import dto.AbsenceCrewsDto;
import dto.HistoriesDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import util.CsvReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final OutputView outputVIew;
    private final InputView inputView;

    public AttendanceController(OutputView outputVIew, InputView inputView) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
    }

    private static Crews intializeCrews() {
        LocalDate now = LocalDate.now();
        return new Crews(CsvReader.loadAttendanceData(), changeStandardLocalDate(now));
    }

    public void start() {
        Crews crews = intializeCrews();
        SelectionOption answer;
        do {
            answer = inputView.getMenu();
            startMenu(answer, crews);
        } while (answer != SelectionOption.QUIT);
    }

    private void startMenu(SelectionOption answer, Crews crews) {
        if (answer == SelectionOption.ADD_ATTENDANCE) {
            addAttendance(crews);
        }
        if (answer == SelectionOption.EDIT_ATTENDANCE) {
            editAttendance(crews);
        }
        if (answer == SelectionOption.GET_ATTENDANCE_HISTORY) {
            getAllAttendance(crews);
        }
        if (answer == SelectionOption.CHECK_ABSENCE_USERS) {
            getAbsenceUsers(crews);
        }
    }

    private void addAttendance(Crews crews) {
        String name = inputView.getName();
        LocalDateTime time = inputView.getAttendanceTime();
        crews.recordAttendance(name, time);
        String historyResult = crews.getRecordedAttendanceResult(name, time);
        outputVIew.printAttendanceConfirmation(time, historyResult);
    }

    private void editAttendance(Crews crews) {
        String editName = inputView.getEditName();
        LocalDateTime editHistory = inputView.getEditAttendanceTime();
        LocalDateTime beforeHistory = crews.getAttendanceHistory(editName, editHistory);
        String beforeResult = crews.getRecordedAttendanceResult(editName, editHistory);
        String editResult = crews.editAttendanceHistory(editName, editHistory);
        outputVIew.printEditAttendance(beforeHistory, beforeResult, editHistory, editResult);
    }

    private void getAllAttendance(Crews crews) {
        String username = inputView.getName();
        LocalDateTime newDate = changeStandardLocalDateTime(LocalDateTime.now());
        List<AttendanceHistory> beforeAttendanceHistory = crews.getBeforeAttendanceHistories(username, newDate);
        AttendanceRecord attendanceRecord = crews.getAttendanceAllResult(username, newDate);
        HistoriesDto historiesDto = HistoriesDto.of(username, beforeAttendanceHistory, attendanceRecord);
        outputVIew.printHistories(historiesDto);
    }

    private void getAbsenceUsers(Crews crews) {
        LocalDateTime newDate = changeStandardLocalDateTime(LocalDateTime.now());
        Map<Crew, AttendanceRecord> highAbsenceLevelCrews = crews.getHighAbsenceLevelCrews(newDate);
        AbsenceCrewsDto crewsDto = new AbsenceCrewsDto(highAbsenceLevelCrews);
        outputVIew.printDangerous(crewsDto);
    }

}
