package database.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

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
    private BirthPlacesEntity birthplacesByBirthPlaceId;
    private PassportsEntity passportsByPassportId;

    public RequestsEntity() { }

    public RequestsEntity(String firstName, String lastName,
                          String patronymic, String gender,
                          Date birthDate, Timestamp reqDate,
                          Timestamp respDate,
                          BirthPlacesEntity birthplacesByBirthPlaceId,
                          PassportsEntity passportsByPassportId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.reqDate = reqDate;
        this.respDate = respDate;
        this.birthplacesByBirthPlaceId = birthplacesByBirthPlaceId;
        this.passportsByPassportId = passportsByPassportId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestsEntity that = (RequestsEntity) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (reqDate != null ? !reqDate.equals(that.reqDate) : that.reqDate != null) return false;
        if (respDate != null ? !respDate.equals(that.respDate) : that.respDate != null) return false;

        return true;
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
}
