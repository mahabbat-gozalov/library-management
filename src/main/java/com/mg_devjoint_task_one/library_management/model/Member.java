package com.mg_devjoint_task_one.library_management.model;

import com.mg_devjoint_task_one.library_management.exception.InvalidEntityDataException;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Column(name = "membership_date")
    LocalDate membershipDate;

    @OneToMany(mappedBy = "member")
    private Set<Loan> loans = new HashSet<>();

    protected Member() {
    }

    public static Member create(String firstName, String lastName, String email, String phone) {

        if (firstName == null)
            throw new InvalidEntityDataException("first_name cannot be null!");
        if (lastName == null)
            throw new InvalidEntityDataException("last_name cannot be null!");
        if (email == null)
            throw new InvalidEntityDataException("email cannot be null!");
        if (phone == null)
            throw new InvalidEntityDataException("phone cannot be null!");


        Member member = new Member();

        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setStatus(MemberStatus.ACTIVE);
        member.setMembershipDate(LocalDate.now());

        return member;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public Set<Loan> getLoans() {
        return Collections.unmodifiableSet(loans);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return id != null && id.equals(member.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
