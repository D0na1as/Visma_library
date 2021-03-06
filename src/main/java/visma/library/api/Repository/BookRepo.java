package visma.library.api.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import visma.library.api.Model.Book;
import visma.library.api.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepo {

    @Value("${library}")
    private  String path;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private Utils utils;

    //Read all books from json file
    public List<Book> getLibrary() throws JSONException, IOException, ParseException {

        JSONParser parser = new JSONParser();
        if ((new File(path)).length()>0) {
            String response = parser.parse(new FileReader(path)).toString();

            JSONArray array = new JSONArray(response);
            String dataArray = array.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<Book>>() {
            });
            return objectReader.readValue(response);
        }
        else {
            return new ArrayList<>();
        }
    }

    //Write back to json file
    public void saveToFile(List<Book> library) throws JsonProcessingException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library);
        utils.saveFile(path, json);
    }
}
