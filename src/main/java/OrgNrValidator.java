import java.util.logging.Level;

public class OrgNrValidator extends IdNrValidator {
    @Override
    protected boolean validatePrefix(String prefix, String divider) {

        int middle = Integer.parseInt(prefix.substring(prefix.length() - 4, prefix.length() - 2));

        if (prefix.length() == 8 && Integer.parseInt(prefix.substring(0, 2)) != 16) {
            logger.log(Level.INFO, "(5) Invalid prefix: first two digits of 8-digit prefix must be 16");
            return false;
        } else if (middle < 20) {
            logger.log(Level.INFO, "(6) Invalid prefix: middle digits must be at least 20");
            return false;
        } else {
            return true;
        }
    }
}
