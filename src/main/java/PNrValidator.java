import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.logging.Level;

public class PNrValidator extends IdNrValidator{

    LocalDate currentDate;

    public PNrValidator(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    protected boolean validatePrefix(String prefix, String divider) {
        int day = Integer.parseInt(prefix.substring(prefix.length() - 2));
        int month = Integer.parseInt(prefix.substring(prefix.length() - 4, prefix.length() - 2));
        int year = Integer.parseInt(prefix.substring(0, prefix.length() - 4));

        if (year <= 99) {
            return validateSixDigitDate(year, month, day, divider);
        } else {
            return validateEightDigitDate(year, month, day, divider);
        }
    }

    protected boolean validateSixDigitDate(int year, int month, int day, String divider) {
        int century = currentDate.getYear() / 100 * 100;
        if (isAfterDate(century + year, month, day, currentDate) || divider.equals("+")) {
            century -= 100;
        }

        return validateDate(century + year, month, day);
    }

    protected boolean validateEightDigitDate(int year, int month, int day, String divider) {
        if (isAfterDate(year, month, day, currentDate)) {
            logger.log(Level.INFO, String.format("(2) Invalid date: %d-%d-%d is after current date", year, month, day));
            return false;
        }
        if (divider.equals("+") && isAfterDate(year, month, day, currentDate.minusYears(100))) {
            logger.log(Level.INFO, String.format("(3) Invalid date: divider is '+', and %d-%d-%d is less than 100 years before current date", year, month, day));
            return false;
        }
        return validateDate(year, month, day);
    }

    private boolean validateDate (int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            logger.log(Level.INFO, String.format("(4) Invalid date: %d-%d-%d does not exist", year, month, day));
            return false;
        }
    }

    private boolean isAfterDate(int year, int month, int day, LocalDate date) {
        if (year > date.getYear()) {
            return true;
        } else if (year == date.getYear() && month > date.getMonthValue()) {
            return true;
        } else if (year == date.getYear() && month == date.getMonthValue() && day > date.getDayOfMonth()) {
            return true;
        } else {
            return false;
        }
    }
}