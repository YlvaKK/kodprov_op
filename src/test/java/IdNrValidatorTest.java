import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IdNrValidatorTest {

    @ParameterizedTest
    @MethodSource("provideStringsForIsValid")
    public void test_isValid_trueFormat(String input, boolean expected) {
        IdNrValidator v = new PNrValidator(LocalDate.now());
        assertEquals(expected, v.isValid(input));
    }

    private static Stream<Arguments> provideStringsForIsValid() {
        return Stream.of(
                Arguments.of("201701102384", true),
                Arguments.of("20170110-2384", true),
                Arguments.of("20170110+2384", true),
                Arguments.of("1701102384", true),
                Arguments.of("170110-2384", true),
                Arguments.of("170110+2384", true),
                Arguments.of("200170110+2384", false),
                Arguments.of("70110+2384", false),
                Arguments.of("170110?2384", false),
                Arguments.of("170110-384", false),
                Arguments.of("170110-23845", false)
        );
    }

    @Test
    public void test_isValid_prefix() {
        IdNrValidator v = new PNrValidator(LocalDate.now());
        assertTrue(v.isValid("1701102384"));
        assertTrue(v.isValid("170110-2384"));
        assertTrue(v.isValid("170110+2384"));
        assertTrue(v.isValid("000229-0000"));
        assertFalse(v.isValid("000229+0000"));
        assertTrue(v.isValid("221025-2384"));
        assertTrue(v.isValid("201701102384"));
        assertTrue(v.isValid("20170110-2384"));
        assertTrue(v.isValid("19170110+2384"));
        assertTrue(v.isValid("20000229-0000"));
        assertFalse(v.isValid("19000229+0000"));
        assertTrue(v.isValid("19221025-2384"));
        assertTrue(v.isValid("18221025-2384"));
        assertFalse(v.isValid("20221025-2384"));
    }
}
