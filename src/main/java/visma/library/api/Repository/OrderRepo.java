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
import visma.library.api.Model.Order;
import visma.library.api.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepo {

    @Value("${orders}")
    private  String path;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    private Utils utils;

    //Read all orders from json file
    public List<Order> getTakings() throws JSONException, IOException, ParseException {

        JSONParser parser = new JSONParser();
        if ((new File(path)).length()>0) {
            String response = parser.parse(new FileReader(path)).toString();

            JSONArray array = new JSONArray(response);
            String dataArray = array.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<Order>>() {
            });
            return objectReader.readValue(dataArray);
        }
        else {
            return new ArrayList<>();
        }
    }

    public void saveToFile(List<Order> clients) throws JsonProcessingException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clients);
        utils.saveFile(path, json);
    }
}
