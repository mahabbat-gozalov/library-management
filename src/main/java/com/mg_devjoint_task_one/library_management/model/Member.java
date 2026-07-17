package com.mg_devjoint_task_one.library_management.model;

import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "MEMBERS")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private MemberStatus memberStatus;

    @Column
    LocalDate membershipDate;

    @OneToMany(mappedBy = "member")
    private Set<Loan> loans;

}
