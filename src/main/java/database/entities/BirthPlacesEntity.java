package database.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

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
        if (!Objects.equals(placeType, that.placeType)) return false;
        if (!Objects.equals(settlement, that.settlement)) return false;
        if (!Objects.equals(district, that.district)) return false;
        if (!Objects.equals(region, that.region)) return false;
        if (!Objects.equals(country, that.country)) return false;

        return true;
    }

}
