import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IdNrValidatorTest {
    
    public static final LocalDate TEST_DATE = LocalDate.of(2022, 11, 20);

    //equivalence class testing of format validation
    @Test
    public void test_validateFormat_wrongLengthSuffix() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("170110-23840"));
        assertFalse(v.isValid("170110+238"));
    }

    @Test
    public void test_validateFormat_nonDigitInSuffix() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("20170110238B"));
    }

    @Test
    public void test_validateFormat_wrongLengthPrefix() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("020170110-2384"));
        assertFalse(v.isValid("70110-2384"));
        assertFalse(v.isValid("0170110-2384"));
    }

    @Test
    public void test_validateFormat_nonDigitInPrefix() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("17011B-2384"));
    }

    @Test
    public void test_validateFormat_invalidDivider() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("170110&2384"));
    }

    //equivalence class testing of date validation
    @Test
    public void test_validateDate_afterTestDate() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("202301301236"));
    }

    @Test
    public void test_validateDate_notWithinCentury() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertTrue(v.isValid("900118+9811"));
    }

    @Test
    public void test_validateDate_withinCentury() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertTrue(v.isValid("19900118-9811"));
    }

    @Test
    public void test_validateDate_nonExistingDate() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("030229-2396"));
    }

    @Test
    public void test_validateDate_mismatchYearDivider() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("19900118+9811"));
    }

    //equivalence class testing of control number validation
    @Test
    public void test_validateControl_incorrectControlNumber() {
        IdNrValidator v = new PNrValidator(TEST_DATE);
        assertFalse(v.isValid("19900118-9812"));
    }

    //testing that samordningsnummer are different from personnummer
    @Test
    public void test_isValidSamordning_withPNr() {
        IdNrValidator vP = new PNrValidator(TEST_DATE);
        IdNrValidator vS = new SamNrValidator(TEST_DATE);
        String number = "19900118-9811";
        assertTrue(vP.isValid(number));
        assertFalse(vS.isValid(number));
    }

    @Test
    public void test_isValidSamordning_withSamNr() {
        IdNrValidator vP = new PNrValidator(TEST_DATE);
        IdNrValidator vS = new SamNrValidator(TEST_DATE);
        String number = "19900178-9818";
        assertFalse(vP.isValid(number));
        assertTrue(vS.isValid(number));
    }

    // test validation of all provided examples
    @ParameterizedTest
    @MethodSource("providedPersonalNumbers")
    public void test_isValid_providedPNr(String input, boolean expected) {
        IdNrValidator v = new PNrValidator(TEST_DATE);
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
        IdNrValidator v = new SamNrValidator(TEST_DATE);
        assertTrue(v.isValid("190910799824"));
    }

    @ParameterizedTest
    @MethodSource("providedOrganizationNumbers")
    public void test_isValid_providedOrgNr(String input, boolean expected) {
        IdNrValidator v = new OrgNrValidator();
        assertEquals(expected, v.isValid(input));
    }

    private static Stream<Arguments> providedOrganizationNumbers() {
        return Stream.of(
                Arguments.of("556614-3185", true),
                Arguments.of("16556601-6399", true),
                Arguments.of("262000-1111", true)
        );
    }
}
