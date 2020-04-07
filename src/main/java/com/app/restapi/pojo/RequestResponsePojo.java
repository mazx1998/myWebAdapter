package com.app.restapi.pojo;

/**
 * @author Максим Зеленский
 * @since 04.04.2020
 */
public class RequestResponsePojo {
    private String author;

    private Integer id;
    private String first_name;
    private String family_name;
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
    private String passport_issuer;

    public RequestResponsePojo() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
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

    @Override
    public String toString() {
        return "RequestResponsePojo{" +
                "author='" + author + '\'' +
                ", id=" + id +
                ", first_name='" + first_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", gender='" + gender + '\'' +
                ", birth_date=" + birth_date +
                ", request_date=" + request_date +
                ", response_date=" + response_date +
                ", snils='" + snils + '\'' +
                ", place_type='" + place_type + '\'' +
                ", settlement='" + settlement + '\'' +
                ", district='" + district + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", passport_series='" + passport_series + '\'' +
                ", passport_number='" + passport_number + '\'' +
                ", passport_issue_date=" + passport_issue_date +
                ", passport_issuer='" + passport_issuer + '\'' +
                '}';
    }
}
