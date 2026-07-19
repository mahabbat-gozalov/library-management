package com.mg_devjoint_task_one.library_management.model.enums;

public enum BookStatus {

    ACTIVE("The book is active and available for circulation."),

    INACTIVE("""
            The book has been added to the system but has not yet been reviewed by an admin.
            Books in this state are not available for circulation until activated.
            """),

    SUSPENDED("""
            The book is temporarily unavailable for borrowing.
            Used when the book is intended to be archived but cannot be archived because one or more copies are still on loan.
            """),

    DELETED("The book has been permanently removed from active circulation.");

    private final String description;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}