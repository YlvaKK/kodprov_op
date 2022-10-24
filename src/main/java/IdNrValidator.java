

public abstract class IdNrValidator {
    public IdNrValidator() {
    }

    public boolean isValid(String idNumber) {
        if (validateFormat(idNumber)) {
            String[] parts = splitParts(idNumber);
            String prefix = parts[0];
            String divider = parts[1];
            String suffix = parts[2];

            if (validatePrefix(prefix, divider)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateFormat(String idNumber) {
        return idNumber.matches("(\\d{6}|\\d{8})[-+]?\\d{4}");
    }

    private String[] splitParts(String idNumber){
        String[] parts = new String[]{"", "", ""};

        parts[2] = idNumber.substring(idNumber.length() - 4);
        idNumber = idNumber.substring(0, idNumber.length() - 4);

        if (idNumber.matches("\\d*[-+]")) {
            parts[1] = idNumber.substring(idNumber.length() - 1);
            idNumber = idNumber.substring(0, idNumber.length() - 1);
        }

        parts[0] = idNumber;
        return parts;
    }

    //private boolean validatePrefix(String prefix, String divider) { return false; }

    private boolean validateControl(String prefix, String suffix) {
        String number = stripCentury(prefix) + suffix.substring(0, suffix.length() - 1);
        int controlNumber = Character.getNumericValue(suffix.charAt(suffix.length() - 1));
        int sum = 0;

        for (int i = 0; i < number.length(); i++) {
            int product = Character.getNumericValue(number.charAt(i));

            if (i % 2 == 0) {
                product = product * 2;
            }

            int digitSum = (product / 10) + (product % 10);
            sum += digitSum;
        }
        int stepTwo = (10 - sum % 10) % 10;

        return stepTwo == controlNumber;
    }

    protected String stripCentury(String prefix) {
        if (prefix.length() == 8) {
            prefix = prefix.substring(2);
        }
        return prefix;
    }

    protected abstract boolean validatePrefix(String prefix, String divider);
}
