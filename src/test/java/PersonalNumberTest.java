import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalNumberTest {

    public static Stream<Arguments> provideStringsForValidateFormat() {
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
                Arguments.of("1899122998165", false),
                Arguments.of("0", false),
                Arguments.of("12345678_1234", false),
                Arguments.of("190302299813", false),
                Arguments.of("199309310000", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForValidateFormat")
    public void test_constructor_valid(String input, boolean expected){
        PersonalNumberValidator p = new PersonalNumberValidator();
        assertEquals(expected, p.validatePersonalNumber(input));
    }
}
