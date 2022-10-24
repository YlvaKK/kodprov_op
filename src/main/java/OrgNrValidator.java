public class OrgNrValidator extends IdNrValidator {
    @Override
    protected boolean validatePrefix(String prefix, String divider) {

        int middle = Integer.parseInt(prefix.substring(prefix.length() - 4, prefix.length() - 2));

        if (prefix.length() == 8 && Integer.parseInt(prefix.substring(0, 2)) != 16) {
            return false;
        } else if (middle < 20) {
            return false;
        } else {
            return true;
        }
    }
}
