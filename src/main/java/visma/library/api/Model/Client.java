package visma.library.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Date taken:")
    private Date taking;
    @JsonProperty("Expected to return:")
    private Date returning;
    @JsonProperty("GUID")
    private String guid;

    public Client() {
    }

    public Client(String name, Date taking, Date returning, String guid) {
        this.name = name;
        this.taking = taking;
        this.returning = returning;
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTaking() {
        return taking;
    }

    public void setTaking(Date taking) {
        this.taking = taking;
    }

    public Date getReturning() {
        return returning;
    }

    public void setReturning(Date returning) {
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
        json.put("Name", name);
        json.put("AuthDate taken:or", taking);
        json.put("Expected to return:", returning);
        json.put("GUID", guid);

        return json;
    }
}
