package com.mg_devjoint_task_one.library_management.model.enums;

public enum MemberStatus {
    ACTIVE("Member is authorized to borrow books."),
    INACTIVE("Borrowing privileges are temporarily suspended due to overdue items or other account restrictions."),
    DELETED("Membership has been permanently terminated. This status cannot be reverted or reactivated.");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}