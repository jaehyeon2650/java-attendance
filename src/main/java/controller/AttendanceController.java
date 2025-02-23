package controller;

import static util.Convertor.changeStandardDate;

import constants.SelectionOption;
import domain.AbsencePenalty;
import domain.AttendanceHistory;
import domain.Crew;
import domain.Crews;
import dto.AbsenceCrewDto;
import dto.AbsenceCrewsDto;
import dto.HistoriesDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import util.CsvReader;
import view.InputView;
import view.OutputVIew;

public class AttendanceController {

    private final OutputVIew outputVIew;
    private final InputView inputView;

    public AttendanceController(OutputVIew outputVIew, InputView inputView) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
    }

    public void start() {
        Crews crews = intializeCrews();
        SelectionOption answer;
        do {
            answer = inputView.getMenu();
            startMenu(answer, crews);
        } while (answer != SelectionOption.QUIT);
    }

    private static Crews intializeCrews() {
        LocalDate now = LocalDate.now();
        return new Crews(CsvReader.loadAttendanceData(), LocalDate.of(2024, 12, now.getDayOfMonth()));
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
        crews.addHistory(name, time);
        String historyResult = crews.getHistoryResult(name, time);
        outputVIew.printAttendanceConfirmation(time, historyResult);
    }

    private void editAttendance(Crews crews) {
        String editName = inputView.getEditName();
        LocalDateTime editHistory = inputView.getEditAttendanceTime();
        LocalDateTime beforeHistory = crews.getHistory(editName, editHistory);
        String beforeResult = crews.getHistoryResult(editName, editHistory);
        String editResult = crews.editHistory(editName, editHistory);
        outputVIew.printEditAttendance(beforeHistory, beforeResult, editHistory, editResult);
    }

    private void getAllAttendance(Crews crews) {
        String username = inputView.getName();
        LocalDateTime newDate = changeStandardDate(LocalDateTime.now());
        List<AttendanceHistory> beforeAttendanceHistory = crews.getBeforeHistory(username, newDate);
        Map<String, Integer> attendanceAllResult = crews.getAttendanceAllResult(username, newDate);
        AbsencePenalty classifyAbsencePenalty = crews.getClassifyAbsenceLevel(username, newDate);
        HistoriesDto historiesDto = HistoriesDto.of(username, beforeAttendanceHistory, attendanceAllResult,
                classifyAbsencePenalty);
        outputVIew.printHistories(historiesDto);
    }

    private void getAbsenceUsers(Crews crews) {
        LocalDateTime newDate = changeStandardDate(LocalDateTime.now());
        List<Crew> members = crews.getHighAbsenceLevelCrews(newDate);
        List<AbsenceCrewDto> crewDtos = members.stream().map(member -> {
            Map<String, Integer> results = crews.getAttendanceAllResult(member.getUserName(), newDate);
            AbsencePenalty classifyAbsencePenalty = crews.getClassifyAbsenceLevel(member.getUserName(), newDate);
            return new AbsenceCrewDto(member.getUserName(), results, classifyAbsencePenalty);
        }).collect(Collectors.toList());
        AbsenceCrewsDto crewsDto = new AbsenceCrewsDto(crewDtos);
        outputVIew.printDangerous(crewsDto);
    }

}
