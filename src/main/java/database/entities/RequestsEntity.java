package database.entities;

import restapi.pojo.request.out.RequestOutPojo;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Максим Зеленский
 * @since 11.03.2020
 */
@Entity
@Table(name = "requests", schema = "public", catalog = "adapterDataBase")
public class RequestsEntity extends MainEntity{
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String gender;
    private Date birthDate;
    private Timestamp reqDate;
    private Timestamp respDate;
    private String snils;
    private BirthPlacesEntity birthplacesByBirthPlaceId;
    private PassportsEntity passportsByPassportId;

    public RequestsEntity() { }

    public RequestsEntity(String firstName, String lastName,
                          String patronymic, String gender,
                          Date birthDate, Timestamp reqDate,
                          Timestamp respDate,
                          String snils,
                          BirthPlacesEntity birthplacesByBirthPlaceId,
                          PassportsEntity passportsByPassportId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.reqDate = reqDate;
        this.respDate = respDate;
        this.snils = snils;
        this.birthplacesByBirthPlaceId = birthplacesByBirthPlaceId;
        this.passportsByPassportId = passportsByPassportId;
    }

    public RequestsEntity(RequestOutPojo requestOutPojo) {
        this.firstName = requestOutPojo.getFirst_name();
        this.lastName = requestOutPojo.getLast_name();
        this.patronymic = requestOutPojo.getPatronymic();
        this.gender = requestOutPojo.getGender();
        this.birthDate.setTime(requestOutPojo.getBirth_date());
        this.reqDate.setTime(requestOutPojo.getRequest_date());
        this.respDate.setTime(requestOutPojo.getResponse_date());
        this.snils = requestOutPojo.getSnils();
        this.birthplacesByBirthPlaceId = new BirthPlacesEntity(requestOutPojo.getBirth_places());
        this.passportsByPassportId = new PassportsEntity(requestOutPojo.getPassport_data());
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
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "req_date")
    public Timestamp getReqDate() {
        return reqDate;
    }

    public void setReqDate(Timestamp reqDate) {
        this.reqDate = reqDate;
    }

    @Basic
    @Column(name = "resp_date")
    public Timestamp getRespDate() {
        return respDate;
    }

    public void setRespDate(Timestamp respDate) {
        this.respDate = respDate;
    }

    @Basic
    @Column(name = "snils")
    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "birth_place_id", referencedColumnName = "id")
    public BirthPlacesEntity getBirthplacesByBirthPlaceId() {
        return birthplacesByBirthPlaceId;
    }

    public void setBirthplacesByBirthPlaceId(BirthPlacesEntity birthplacesByBirthPlaceId) {
        this.birthplacesByBirthPlaceId = birthplacesByBirthPlaceId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    public PassportsEntity getPassportsByPassportId() {
        return passportsByPassportId;
    }

    public void setPassportsByPassportId(PassportsEntity passportsByPassportId) {
        this.passportsByPassportId = passportsByPassportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestsEntity that = (RequestsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(patronymic, that.patronymic)) return false;
        if (!Objects.equals(gender, that.gender)) return false;
        if (!Objects.equals(birthDate, that.birthDate)) return false;
        if (!Objects.equals(reqDate, that.reqDate)) return false;
        if (!Objects.equals(respDate, that.respDate)) return false;

        return true;
    }
}
