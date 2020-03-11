package database.entities;

import javax.persistence.*;

/**
 * @author Максим Зеленский
 * @since 11.03.2020
 */
@Entity
@Table(name = "birthplaces", schema = "public", catalog = "adapterDataBase")
public class BirthPlacesEntity extends MainEntity{
    private int id;
    private String placeType;
    private String settlement;
    private String district;
    private String region;
    private String country;

    public BirthPlacesEntity() {
    }

    public BirthPlacesEntity(String placeType, String settlement,
                             String district, String region,
                             String country) {
        this.placeType = placeType;
        this.settlement = settlement;
        this.district = district;
        this.region = region;
        this.country = country;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "place_type")
    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    @Basic
    @Column(name = "settlement")
    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BirthPlacesEntity that = (BirthPlacesEntity) o;

        if (id != that.id) return false;
        if (placeType != null ? !placeType.equals(that.placeType) : that.placeType != null) return false;
        if (settlement != null ? !settlement.equals(that.settlement) : that.settlement != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

}
