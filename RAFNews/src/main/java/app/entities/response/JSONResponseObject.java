package app.entities.response;

import com.google.gson.JsonElement;

public class JSONResponseObject {

    /*
        Struktura JSON odgovora koje cemo slati
        na frontend, radi lakse citljivosti
        Status moze biti: SUCCESS i ERROR
     */
    private String status;
    private String message;
    private JsonElement data;

    public JSONResponseObject() {
    }

    public JSONResponseObject(String status) {
        this.status = status;
    }

    public JSONResponseObject(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public JSONResponseObject(String status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
