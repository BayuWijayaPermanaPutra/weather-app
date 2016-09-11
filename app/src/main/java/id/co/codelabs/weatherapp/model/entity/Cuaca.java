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
    private int actualId;
    private long sunrise;
    private long sunset;

    public Cuaca(String namaKota, String idNegara, String lastUpdate, String humidity, String description, String pressure, String temperature, int actualId, long sunrise, long sunset) {
        this.namaKota = namaKota;
        this.idNegara = idNegara;
        this.lastUpdate = lastUpdate;
        this.humidity = humidity;
        this.description = description;
        this.pressure = pressure;
        this.temperature = temperature;
        this.actualId = actualId;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Cuaca() {

    }

    public String getNamaKota() {
        return namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }

    public String getIdNegara() {
        return idNegara;
    }

    public void setIdNegara(String idNegara) {
        this.idNegara = idNegara;
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

    public int getActualId() {
        return actualId;
    }

    public void setActualId(int actualId) {
        this.actualId = actualId;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
