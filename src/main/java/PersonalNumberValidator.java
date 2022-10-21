import java.time.DateTimeException;
import java.time.LocalDateTime;

public class PersonalNumberValidator implements IdentificationNumber {

    private String dateString;
    private char separator;
    private int birthNumber;
    private int controlNumber;

    private LocalDateTime date;

    public PersonalNumberValidator() {
    }

    public boolean validatePersonalNumber(String pNr){
        if (validateFormat(pNr)) {
            parseDate(pNr);
            if (validateDate(dateString)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateFormat(String pNr) {
        return pNr.matches("(\\d{6}|\\d{8})[-+]?\\d{4}");
    }

    private void parseDate(String pNr) {
        controlNumber = Integer.parseInt(pNr.substring(pNr.length() - 1));
        pNr = pNr.substring(0, pNr.length() -1);

        birthNumber = Integer.parseInt(pNr.substring(pNr.length() - 3));
        pNr = pNr.substring(0, pNr.length() -3);

        char tempSeparator = pNr.charAt(pNr.length() - 1);
        if (tempSeparator == '+' || tempSeparator == '-') {
            separator = tempSeparator;
            pNr = pNr.substring(0, pNr.length() -1);
        }
        dateString = pNr;
    }

    private boolean validateDate(String dateString) {
        int day = Integer.parseInt(dateString.substring(dateString.length() -2));
        dateString = dateString.substring(0, dateString.length() -2);
        int month = Integer.parseInt(dateString.substring(dateString.length() -2));
        dateString = dateString.substring(0, dateString.length() -2);
        int year = Integer.parseInt(dateString);

        try  {
            date = LocalDateTime.of(year, month, day, 0, 0);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

}


















