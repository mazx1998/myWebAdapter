package restapi.pojo;

import database.entities.BirthPlacesEntity;

/**
 * @author Максим Зеленский
 * @since 30.03.2020
 */
public class BirthPlacesPojo {
    private int id;
    private String place_type;
    private String settlement;
    private String district;
    private String region;
    private String country;

    public BirthPlacesPojo() {
    }

    public BirthPlacesPojo(BirthPlacesEntity birthPlacesEntity) {
        id = birthPlacesEntity.getId();
        place_type = birthPlacesEntity.getPlaceType();
        settlement = birthPlacesEntity.getSettlement();
        district = birthPlacesEntity.getDistrict();
        region = birthPlacesEntity.getRegion();
        country = birthPlacesEntity.getCountry();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
