package dto;

import java.time.LocalDateTime;

public record HistoryDto(
        LocalDateTime time,
        String attendanceResult
) {
}
