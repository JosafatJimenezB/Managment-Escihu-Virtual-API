package com.escihu.apiescihuvirtual.Dto.Attendance;

import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Enum.TypeAttendace;
import lombok.Builder;

@Builder
public class AttendaceDtoRequest {

    private Long userId;

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
