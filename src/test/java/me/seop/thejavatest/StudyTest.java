package me.seop.thejavatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create() {
        //Study study = new Study(5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertAll(
                () -> assertEquals("0보다 커야 한다",exception.getMessage())
//                () -> assertNotNull(study),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다"),
//                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 가능 인원은 0보다 커야 한다")
        );
    }

    @Test
    @DisplayName("해당 메서드가 10초안에 끝나야함")
    void timeout() {
        assertAll(
                () -> assertTimeout(Duration.ofMillis(100), () -> {
                    new Study(10);
                    Thread.sleep(300);
                }),
                // ThreadLocal 사용 시 문제 발생 여
                () -> assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
                    new Study(10);
                    Thread.sleep(300);
                })
        );
    }
}