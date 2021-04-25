package visma.library.api.Utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class Utils {

    public String dateToday() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String dueDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
