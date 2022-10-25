import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class IdNrValidator {

    private static final String[] VALID_DIVIDERS = new String[]{"+", "-"};
    private static final String DIVIDERS_REGEX = "[-+]";
    private static final String VALID_FORMAT = "(\\d{6}|\\d{8})" + DIVIDERS_REGEX + "?\\d{4}";

    protected static Logger logger = Logger.getLogger(IdNrValidator.class.getName());

    public IdNrValidator() {
    }

    public boolean isValid(String idNumber) {
        if (validateFormat(idNumber)) {
            IdNrParts parts = splitParts(idNumber);
            if (validatePrefix(parts.getPrefix(), parts.getDivider())) {
                if (validateControl(parts.getPrefix(), parts.getSuffix())) {
                    return true;
                } else {
                    logger.log(Level.INFO, "(7) Incorrect control number in " + idNumber);
                }
            }
        } else {
            logger.log(Level.INFO, "(1) Invalid format in " + idNumber);
        }
        return false;
    }

    private boolean validateFormat(String idNumber) {
        return idNumber.matches(VALID_FORMAT);
    }
    
    private IdNrParts splitParts(String idNumber){
        IdNrParts parts = new IdNrParts();

        for (String divider : VALID_DIVIDERS) {
            if (idNumber.contains(divider)) {
                parts.setDivider(divider);
                idNumber = idNumber.replaceAll(DIVIDERS_REGEX, "");
            }
        }

        parts.setPrefix(idNumber.substring(0, idNumber.length() - 4));
        parts.setSuffix(idNumber.substring(idNumber.length() - 4));
        return parts;
    }

    private boolean validateControl(String prefix, String suffix) {
        String number = stripCentury(prefix) + suffix.substring(0, suffix.length() - 1);
        int controlNumber = Character.getNumericValue(suffix.charAt(suffix.length() - 1));

        return luhnsAlorithm(number) == controlNumber;
    }

    private int luhnsAlorithm(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int product = Character.getNumericValue(number.charAt(i));

            if (i % 2 == 0) {
                product = product * 2;
            }

            sum += (product / 10) + (product % 10);
        }
        return (10 - sum % 10) % 10;
    }

    private String stripCentury(String prefix) {
        if (prefix.length() == 8) {
            prefix = prefix.substring(2);
        }
        return prefix;
    }

    protected abstract boolean validatePrefix(String prefix, String divider);
}
