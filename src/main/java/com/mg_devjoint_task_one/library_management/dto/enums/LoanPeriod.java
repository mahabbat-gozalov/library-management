package com.mg_devjoint_task_one.library_management.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Loan duration period in days: SHORT (7 days), STANDARD (14 days), EXTENDED (21 days)")
public enum LoanPeriod {
    SHORT(7),
    STANDARD(14),
    EXTENDED(21);

    private final int days;

    LoanPeriod(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

}
