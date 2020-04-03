package restapi.pojo.request.in;

/**
 * @author Максим Зеленский
 * @since 04.04.2020
 */
public class RequestInPojo {
    private String first_name;
    private String last_name;
    private String patronymic;
    private String gender;
    private Long birth_date;
    private Long request_date;
    private Long response_date;
    private String snils;

    private String place_type;
    private String settlement;
    private String district;
    private String region;
    private String country;

    private String series;
    private String number;
    private Long issue_date;
    private String issuer;

    public RequestInPojo() {
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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(Long issue_date) {
        this.issue_date = issue_date;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
