package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

}
