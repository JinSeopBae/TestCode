package me.seop.thejavatest.member;

import me.seop.thejavatest.domain.Member;
import me.seop.thejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
