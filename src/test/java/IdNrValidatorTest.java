import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IdNrValidatorTest {

    @ParameterizedTest
    @MethodSource("providedPersonalNumbers")
    public void test_isValid_providedPNr(String input, boolean expected) {
        IdNrValidator v = new PNrValidator(LocalDate.now());
        assertEquals(expected, v.isValid(input));
    }

    private static Stream<Arguments> providedPersonalNumbers() {
        return Stream.of(
                Arguments.of("201701102384", true),
                Arguments.of("141206-2380", true),
                Arguments.of("20080903-2386", true),
                Arguments.of("7101169295", true),
                Arguments.of("198107249289", true),
                Arguments.of("19021214-9819", true),
                Arguments.of("190910199827", true),
                Arguments.of("191006089807", true),
                Arguments.of("192109099180", true),
                Arguments.of("4607137454", true),
                Arguments.of("194510168885", true),
                Arguments.of("900118+9811", true),
                Arguments.of("189102279800", true),
                Arguments.of("189912299816", true),
                Arguments.of("201701272394", false),
                Arguments.of("190302299813", false)
        );
    }

    @Test
    public void test_isValid_providedSNr() {
        IdNrValidator v = new SamNrValidator(LocalDate.now());
        assertTrue(v.isValid("190910799824"));
    }
}
