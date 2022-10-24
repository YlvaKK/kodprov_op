import java.time.LocalDate;

public class SamNrValidator extends PNrValidator{
    public SamNrValidator(LocalDate currentDate) {
        super(currentDate);
    }

    @Override
    protected boolean validatePrefix(String prefix, String divider) {
        String prefixMinusSixty = String.valueOf((Integer.parseInt(prefix) - 60));
        return super.validatePrefix(prefixMinusSixty, divider);
    }
}
