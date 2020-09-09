package model.entities;

import com.google.gson.Gson;
import lib.ServletHelper;
import lib.dataBase.IsEntityInDB;

import java.util.HashMap;

public class User implements IsEntityInDB {
    private static final String ENTITY_NAME = "User";
    private Integer id;
    private String cpf;
    private String password;
    private Double lat;
    private Double lng;
    private String name;

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

    @Override
    public HashMap<String, String> getNotNullFields() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("cpf", cpf);
        fields.put("password", password);
        fields.put("lat", lat.toString());
        fields.put("lng", lng.toString());
        fields.put("name", name);
        return fields;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void setFromJson(String json) {
        User user = new Gson().fromJson(json, User.class);
        setCpf(user.cpf);
        setId(user.id);
        setLat(user.lat);
        setLng(user.lng);
        setName(user.name);
        setPassword(user.password);
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", name: " + name +
                ", lat: " + lat +
                ", lng: " + lng +
                ", cpf: " + cpf +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean havePassword(){
        return ServletHelper.haveStringField(password);
    }

    public boolean haveLat(){
        return lat != null;
    }

    public boolean haveLng(){
        return lng != null;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public User setLng(Double lng) {
        this.lng = lng;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

}
