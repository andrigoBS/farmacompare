package model.entities.request;

import com.google.gson.Gson;
import lib.dataBase.IsEntityInDB;
import java.util.HashMap;

public class Request implements IsEntityInDB {
    private static final String ENTITY_NAME = "Request";
    private Integer id;
    private Integer userId;
    private Double totalValue;

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

    @Override
    public HashMap<String, String> getNotNullFields() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("userId", userId.toString());
        fields.put("totalValue", totalValue.toString());
        return fields;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void setFromJson(String json) {
        Request request = new Gson().fromJson(json, Request.class);
        setId(request.id);
        setUserId(request.userId);
        setTotalValue(request.totalValue);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalValue=" + totalValue +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Request setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Request setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public Request setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
        return this;
    }
}
