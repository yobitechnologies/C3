package tech.yobi.c3;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    public int id;
    private String name;
    private double latitude;
    private double longitude;
    private String simNumber;
    private String carrier;

    public ConfigurationManager() {
//        name = "Not set";
//        latitude = 0;
//        longitude = 0;
//        simNumber = "Not set";
//        carrier = "Not set";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }
    
    public Map<String,String> getParams() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("id", Integer.toString(getId()));
        params.put("name", getName());
        params.put("latitude", Double.toString(getLatitude()));
        params.put("longitude", Double.toString(getLongitude()));
        params.put("service_provider", getCarrier());
        params.put("sim_number", getSimNumber());

        return params;
    }
}
