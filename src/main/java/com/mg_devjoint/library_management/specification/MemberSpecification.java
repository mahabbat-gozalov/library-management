package com.mg_devjoint.library_management.specification;

import com.mg_devjoint.library_management.model.Member;
import com.mg_devjoint.library_management.model.enums.MemberStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class MemberSpecification {
    private MemberSpecification() {
    }

    public static Specification<Member> hasFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            return null;
        }

        String normalizedFirstName = firstName.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + normalizedFirstName + "%");
    }

    public static Specification<Member> hasLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            return null;
        }

        String normalizedLastName = lastName.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + normalizedLastName + "%");
    }

    public static Specification<Member> hasEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        String normalizedEmail = email.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + normalizedEmail + "%");
    }


    public static Specification<Member> hasPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.like(root.get("phone"), "%" + phone + "%");
    }


    public static Specification<Member> hasStatus(MemberStatus status) {
        if (status == null) return null;

        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }


    public static Specification<Member> hasMembershipDateBetween(LocalDate since, LocalDate until) {
        if (since == null && until == null) return null;

        if (until == null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("membershipDate"), since);
        }
        if (since == null) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("membershipDate"), until);
        }

        return (root, query, cb) -> cb.between(root.get("membershipDate"), since, until);
    }
}
