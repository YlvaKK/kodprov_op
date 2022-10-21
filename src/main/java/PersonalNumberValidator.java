import java.time.LocalDateTime;

public class PersonalNumber implements IdentificationNumber {

    private LocalDateTime date;
    private int fourLastDigits;

    public PersonalNumber(String personalNumber) {
        if (!validateFormat(personalNumber)) {
            throw new IllegalArgumentException("Illegal formatting of pnr");
        } else if (!)
    }

    boolean validateFormat(String personalNumber){
        return personalNumber.matches("(\\d{6}|\\d{8})[-+]?\\d{4}");
    }


}
