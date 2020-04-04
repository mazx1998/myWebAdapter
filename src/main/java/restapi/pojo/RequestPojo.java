package restapi.pojo;

import database.entities.BirthPlacesEntity;
import database.entities.PassportsEntity;
import database.entities.RequestsEntity;

/**
 * @author Максим Зеленский
 * @since 04.04.2020
 */
public class RequestPojo {
    private Integer id;
    private String first_name;
    private String last_name;
    private String patronymic;  // nullable
    private String gender;
    private Long birth_date;
    private Long request_date;
    private Long response_date; // nullable
    private String snils;       // nullable

    private String place_type;
    private String settlement;
    private String district;    // nullable
    private String region;      // nullable
    private String country;     // nullable

    private String passport_series;
    private String passport_number;
    private Long passport_issue_date;
    private String passport_issuer;      // nullable

    public RequestPojo() {
    }

    public RequestPojo(RequestsEntity requestsEntity) {
        id = requestsEntity.getId();
        first_name = requestsEntity.getFirstName();
        last_name = requestsEntity.getLastName();
        if (requestsEntity.getPatronymic() != null)
            patronymic = requestsEntity.getPatronymic();
        gender = requestsEntity.getGender();
        birth_date = requestsEntity.getBirthDate().getTime();
        request_date = requestsEntity.getReqDate().getTime();
        if (requestsEntity.getRespDate() != null)
            response_date = requestsEntity.getRespDate().getTime();
        if (requestsEntity.getSnils() != null)
            snils = requestsEntity.getSnils();

        // If request have birth place data
        if (requestsEntity.getBirthplacesByBirthPlaceId() != null) {
            BirthPlacesEntity birthPlaceData = requestsEntity.getBirthplacesByBirthPlaceId();
            place_type = birthPlaceData.getPlaceType();
            settlement = birthPlaceData.getSettlement();
            if (birthPlaceData.getDistrict() != null)
                district = birthPlaceData.getDistrict();
            if (birthPlaceData.getRegion() != null)
                region = birthPlaceData.getRegion();
            if (birthPlaceData.getCountry() != null)
                country = birthPlaceData.getCountry();
        }

        // If request have passport data
        if (requestsEntity.getPassportsByPassportId() != null) {
            PassportsEntity passportData = requestsEntity.getPassportsByPassportId();
            passport_series = passportData.getSeries();
            passport_number = passportData.getNumber();
            passport_issue_date = passportData.getIssueDate().getTime();
            if (passportData.getIssuer() != null)
                passport_issuer = passportData.getIssuer();
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Long birth_date) {
        this.birth_date = birth_date;
    }

    public Long getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Long request_date) {
        this.request_date = request_date;
    }

    public Long getResponse_date() {
        return response_date;
    }

    public void setResponse_date(Long response_date) {
        this.response_date = response_date;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
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

    public String getPassport_series() {
        return passport_series;
    }

    public void setPassport_series(String passport_series) {
        this.passport_series = passport_series;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    public Long getPassport_issue_date() {
        return passport_issue_date;
    }

    public void setPassport_issue_date(Long passport_issue_date) {
        this.passport_issue_date = passport_issue_date;
    }

    public String getPassport_issuer() {
        return passport_issuer;
    }

    public void setPassport_issuer(String passport_issuer) {
        this.passport_issuer = passport_issuer;
    }
}
