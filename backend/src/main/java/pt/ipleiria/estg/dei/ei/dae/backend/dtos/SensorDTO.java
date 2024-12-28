package pt.ipleiria.estg.dei.ei.dae.backend.dtos;


import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    private int id;
    private String type;
    private double temperature;
    private double atmosphere_pressure;
    private double acceleration;
    private double latitude;
    private double longitude;
    private String alert_message;


    public SensorDTO() {}

    public SensorDTO(int id,String type,double temperature,double atmosphere_pressure,double acceleration,double latitude,double longitude,String alert_message) {
        this.id = id;
        this.type = type;
        this.temperature = temperature;
        this.atmosphere_pressure = atmosphere_pressure;
        this.acceleration = acceleration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alert_message = alert_message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getAtmosphere_pressure() {
        return atmosphere_pressure;
    }

    public void setAtmosphere_pressure(double atmosphere_pressure) {
        this.atmosphere_pressure = atmosphere_pressure;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAlert_message() {
        return alert_message;
    }

    public void setAlert_message(String alert_message) {
        this.alert_message = alert_message;
    }

    public static SensorDTO from(Sensor sensor){
        return new SensorDTO(
//                sensor.getId()
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }


}

