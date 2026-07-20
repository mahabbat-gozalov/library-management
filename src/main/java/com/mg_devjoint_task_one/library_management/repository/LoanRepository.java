package com.mg_devjoint_task_one.library_management.repository;

import com.mg_devjoint_task_one.library_management.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    @Query(
            value = """
                    SELECT COUNT(*)
                    FROM loans l
                    WHERE l.member_id = :memberId
                      AND l.return_date IS NULL
                    """,
            nativeQuery = true
    )
    long countActiveLoansByMemberId(@Param("memberId") UUID memberId);

    @Query(
            value = """
                    SELECT EXISTS (
                        SELECT 1
                        FROM loans l
                        WHERE l.member_id = :memberId
                          AND l.due_date < :date
                          AND l.return_date IS NULL
                    )
                    """,
            nativeQuery = true
    )
    boolean existsOverdueLoanByMemberId(
            @Param("memberId") UUID memberId,
            @Param("date") LocalDate date
    );

}
