package visma.library.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @JsonProperty("Client")
    private String client;
    @JsonProperty("Date taken")
    private String taking;
    @JsonProperty("Expected to return")
    private String returning;
    @JsonProperty("GUID")
    private String guid;

    public Client() {
    }

    public Client(String client, String taking, String returning, String guid) {
        this.client = client;
        this.taking = taking;
        this.returning = returning;
        this.guid = guid;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTaking() {
        return taking;
    }

    public void setTaking(String taking) {
        this.taking = taking;
    }

    public String getReturning() {
        return returning;
    }

    public void setReturning(String returning) {
        this.returning = returning;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public JSONObject toJSONObject () {

        JSONObject json = new JSONObject();
        json.put("Client", client);
        json.put("AuthDate taken:or", taking);
        json.put("Expected to return:", returning);
        json.put("GUID", guid);

        return json;
    }
}
