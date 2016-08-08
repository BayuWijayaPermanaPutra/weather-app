package id.co.codelabs.weatherapp.model.entity;

/**
 * Created by bayu_ on 8/5/2016.
 */
public class Cuaca {
    private String namaKota;
    private String idNegara;
    private String lastUpdate;
    private String humidity;
    private String description;
    private String pressure;
    private String temperature;

    public Cuaca(String namaKota, String idNegara, String lastUpdate, String humidity, String description, String pressure, String temperature) {
        this.namaKota = namaKota;
        this.idNegara = idNegara;
        this.lastUpdate = lastUpdate;
        this.humidity = humidity;
        this.description = description;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
