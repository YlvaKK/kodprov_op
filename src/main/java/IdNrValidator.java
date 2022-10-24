public abstract class IdNrValidator {

    private static final String[] VALID_DIVIDERS = new String[]{"+", "-"};
    private static final String DIVIDERS_REGEX = "[-+]";
    private static final String VALID_FORMAT = "(\\d{6}|\\d{8})" + DIVIDERS_REGEX + "?\\d{4}";

    public IdNrValidator() {
    }

    public boolean isValid(String idNumber) {
        if (validateFormat(idNumber)) {
            String[] parts = splitParts(idNumber);
            String prefix = parts[0];
            String divider = parts[1];
            String suffix = parts[2];

            if (validatePrefix(prefix, divider)) {
                return validateControl(prefix, suffix);
            }
        }
        return false;
    }

    private boolean validateFormat(String idNumber) {
        return idNumber.matches(VALID_FORMAT);
    }

    private String[] splitParts(String idNumber){
        String prefix;
        String suffix;
        String divider = "";

        for (String option : VALID_DIVIDERS) {
            if (idNumber.contains(option)) {
                divider = option;
                idNumber = idNumber.replaceAll(DIVIDERS_REGEX, "");
                break;
            }
        }

        suffix = idNumber.substring(idNumber.length() - 4);
        prefix = idNumber.substring(0, idNumber.length() - 4);
        return new String[]{prefix, divider, suffix};
    }

    private boolean validateControl(String prefix, String suffix) {
        String number = stripCentury(prefix) + suffix.substring(0, suffix.length() - 1);
        int controlNumber = Character.getNumericValue(suffix.charAt(suffix.length() - 1));
        int sum = 0;

        //step one
        for (int i = 0; i < number.length(); i++) {
            int product = Character.getNumericValue(number.charAt(i));

            if (i % 2 == 0) { product = product * 2; }

            int digitSum = (product / 10) + (product % 10);
            sum += digitSum;
        }

        //step two
        int singleDigitResult = (10 - sum % 10) % 10;

        return singleDigitResult == controlNumber;
    }

    private String stripCentury(String prefix) {
        if (prefix.length() == 8) {
            prefix = prefix.substring(2);
        }
        return prefix;
    }

    protected abstract boolean validatePrefix(String prefix, String divider);
}
