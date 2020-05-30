package me.seop.thejavatest.member;

import me.seop.thejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);
}
