package com.mg_devjoint_task_one.library_management.model.enums;

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
