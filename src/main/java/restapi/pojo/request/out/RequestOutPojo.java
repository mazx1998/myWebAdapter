package restapi.pojo.request.out;

import database.entities.RequestsEntity;

/**
 * @author Максим Зеленский
 * @since 30.03.2020
 */
public class RequestOutPojo {
    private int id;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String gender;
    private Long birth_date;
    private Long request_date;
    private Long response_date;
    private String snils;
    private BirthPlacesPojo birth_places;
    private PassportsPojo passport_data;

    public RequestOutPojo() {
    }

    public RequestOutPojo(RequestsEntity requestsEntity) {
        id = requestsEntity.getId();
        first_name = requestsEntity.getFirstName();
        last_name = requestsEntity.getLastName();
        patronymic = requestsEntity.getPatronymic();
        gender = requestsEntity.getGender();
        birth_date = requestsEntity.getBirthDate().getTime();
        request_date = requestsEntity.getReqDate().getTime();
        response_date = requestsEntity.getRespDate().getTime();
        snils = requestsEntity.getSnils();
        if (requestsEntity.getBirthplacesByBirthPlaceId() != null)
            birth_places = new BirthPlacesPojo(requestsEntity.getBirthplacesByBirthPlaceId());
        else
            birth_places = null;
        if (requestsEntity.getPassportsByPassportId() != null)
            passport_data = new PassportsPojo(requestsEntity.getPassportsByPassportId());
        else
            passport_data = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public BirthPlacesPojo getBirth_places() {
        return birth_places;
    }

    public void setBirth_places(BirthPlacesPojo birth_places) {
        this.birth_places = birth_places;
    }

    public PassportsPojo getPassport_data() {
        return passport_data;
    }

    public void setPassport_data(PassportsPojo passportsPojo) {
        this.passport_data = passportsPojo;
    }
}
