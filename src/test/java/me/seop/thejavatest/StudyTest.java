package me.seop.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// 인스턴스 생명주기 지정
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 메서드 실행순서 제어
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    @BeforeAll
    void beforeAll(){}


    @AfterAll
    void afterAll(){

    }

    @Order(2)
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    @FastTest
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

    @Order(1)
    @Test
    @DisplayName("해당 메서드가 10초안에 끝나야함")
    void timeout() {
        assertAll(
                () -> assertTimeout(Duration.ofMillis(1000), () -> {
                    new Study(10);
                }),
                // ThreadLocal 사용 시 문제 발생 여
                () -> assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
                    new Study(10);
                    Thread.sleep(300);
                })
        );
    }

    @Test
    void assume() {
//        String test_env = System.getenv("TEST_ENV");
//        System.out.println("test_env : " + test_env);
//        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
//        assertTrue(true);
        assumingThat(true, () -> {
            System.out.println("true");
        });

        assumingThat(false, () -> {
            System.out.println("false");
        });
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("repeat " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터 반복")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10,'자바 스터디'","20, 스프링"})
    void parameterized(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0),argumentsAccessor.getString(1));
        }
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }
}