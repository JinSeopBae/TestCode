package me.seop.thejavatest.study;

import me.seop.thejavatest.domain.Member;
import me.seop.thejavatest.domain.Study;
import me.seop.thejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    @DisplayName("스터디 생성 메서드")
    @Test
    void createStudyService() {

        StudyService studyService = new StudyService(memberService,studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("hazard@email.com");

        Study study = new Study(10,"test");

        // Mockito Function
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);

        // BDD Style Mockito Function
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        studyService.createNewStudy(1L,study);

        assertEquals(member, study.getOwner());
        // Mockito Function
        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);

        // BDD Style Mockito Function
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
//        verify(memberService, times(1)).notify(member);
//        verify(memberService, never()).validate(any());

    }
}