package com.mg_devjoint.library_management.model;

import com.mg_devjoint.library_management.model.enums.MemberStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.*;
import static com.mg_devjoint.library_management.model.validation.MemberValidationUtils.validateMemberStatus;
import static com.mg_devjoint.library_management.model.validation.MemberValidationUtils.validateMembershipDate;

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
    private LocalDate membershipDate;

    @OneToMany(mappedBy = "member")
    private Set<Loan> loans = new HashSet<>();

    protected Member() {
    }

    public static Member create(String firstName, String lastName, String email, String phone) {

        validateName(firstName);
        validateSurname(lastName);
        validateEmail(email);
        validatePhoneNumber(phone);

        Member member = new Member();

        member.firstName = firstName;
        member.lastName = lastName;
        member.email = email;
        member.phone = phone;
        member.status = MemberStatus.ACTIVE;
        member.membershipDate = LocalDate.now();

        return member;
    }

    public static Member createWithId(UUID id, String firstName, String lastName, String email, String phone) {

        validateIdCannotBeNull(id);

        Member member = create(firstName, lastName, email, phone);

        member.id = id;

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
        validateName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateSurname(lastName);
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void setPhone(String phone) {
        validatePhoneNumber(phone);
        this.phone = phone;
    }

    public void setStatus(MemberStatus status) {
        validateMemberStatus(status);
        this.status = status;
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
