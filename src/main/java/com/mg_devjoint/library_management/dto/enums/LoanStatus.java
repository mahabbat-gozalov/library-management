package com.mg_devjoint.library_management.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = """
        Status of the loan: ACTIVE (currently borrowed), RETURNED (book brought back), OVERDUE (past due date)
        """)
public enum LoanStatus {
    ACTIVE,
    RETURNED,
    OVERDUE
}
