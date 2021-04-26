package visma.library.api.Utils;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Utils {

    public String dateToday() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String dueDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void saveFile (String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
