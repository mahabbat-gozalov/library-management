package com.mg_devjoint_task_one.library_management.model.enums;

public enum LoanPeriod {
    STANDARD(14),
    SHORT(7),
    EXTENDED(21);

    private final int days;

    LoanPeriod(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

}
