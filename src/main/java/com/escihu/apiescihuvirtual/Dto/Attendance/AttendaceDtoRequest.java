package com.escihu.apiescihuvirtual.Dto.Attendance;

import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Enum.TypeAttendace;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class AttendaceDtoRequest {

    private Long userId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeAttendace typeAttendace;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TypeAttendace getTypeAttendace() {
        return typeAttendace;
    }

    public void setTypeAttendace(TypeAttendace typeAttendace) {
        this.typeAttendace = typeAttendace;
    }
}
