package controller;

import static util.Convertor.changeStandardDate;

import constants.SelectionOption;
import domain.AbsencePenalty;
import domain.Crew;
import domain.Crews;
import domain.AttendanceHistory;
import dto.AbsenceCrewDto;
import dto.AbsenceCrewsDto;
import dto.HistoriesDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.InputView;
import view.OutputVIew;

public class AttendanceController {

    private final OutputVIew outputVIew;
    private final InputView inputView;
    private final Crews crews;

    public AttendanceController(OutputVIew outputVIew, InputView inputView, Crews crews) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
        this.crews = crews;
    }

    public void start() {
        SelectionOption answer;
        do {
            answer = inputView.getMenu();
            startMenu(answer);
        } while (answer != SelectionOption.QUIT);
    }

    private void startMenu(SelectionOption answer) {
        if (answer == SelectionOption.ADD_ATTENDANCE) {
            addAttendance();
        }
        if (answer == SelectionOption.EDIT_ATTENDANCE) {
            editAttendance();
        }
        if (answer == SelectionOption.GET_ATTENDANCE_HISTORY) {
            getAllAttendance();
        }
        if (answer == SelectionOption.CHECK_ABSENCE_USERS) {
            getAbsenceUsers();
        }
    }

    private void addAttendance() {
        String name = inputView.getName();
        LocalDateTime time = inputView.getAttendanceTime();
        crews.addHistory(name, time);
        String historyResult = crews.getHistoryResult(name, time);
        outputVIew.printAttendanceConfirmation(time, historyResult);
    }

    private void editAttendance() {
        String editName = inputView.getEditName();
        LocalDateTime editHistory = inputView.getEditAttendanceTime();
        LocalDateTime beforeHistory = crews.getHistory(editName, editHistory);
        String beforeResult = crews.getHistoryResult(editName, editHistory);
        String editResult = crews.editHistory(editName, editHistory);
        outputVIew.printEditAttendance(beforeHistory, beforeResult, editHistory, editResult);
    }

    private void getAllAttendance() {
        String username = inputView.getName();
        LocalDateTime newDate = changeStandardDate(LocalDateTime.now());
        List<AttendanceHistory> beforeAttendanceHistory = crews.getBeforeHistory(username, newDate);
        Map<String, Integer> attendanceAllResult = crews.getAttendanceAllResult(username, newDate);
        AbsencePenalty classifyAbsencePenalty = crews.getClassifyAbsenceLevel(username, newDate);
        HistoriesDto historiesDto = HistoriesDto.of(username, beforeAttendanceHistory, attendanceAllResult,
                classifyAbsencePenalty);
        outputVIew.printHistories(historiesDto);
    }

    private void getAbsenceUsers() {
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
