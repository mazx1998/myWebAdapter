package database.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "requests", schema = "public", catalog = "adapterDataBase")
public class RequestsEntity extends MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "firstname")
    private String firstname;

    @Basic
    @Column(name = "lastname")
    private String lastname;

    @Basic
    @Column(name = "patronymic")
    private String patronymic;

    @Basic
    @Column(name = "gender")
    private String gender;

    @Basic
    @Column(name = "birthdate")
    private Date birthdate;

    @Basic
    @Column(name = "reqdate")
    private Date reqdate;

    @OneToOne(mappedBy = "requestsByReqId")
    private ResponsesEntity responsesById;

    public RequestsEntity() {}

    public RequestsEntity(String firstname, String lastname, String patronymic,
                          String gender, Date birthdate, Date reqdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthdate = birthdate;
        this.reqdate = reqdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getReqdate() {
        return reqdate;
    }

    public void setReqdate(Date reqdate) {
        this.reqdate = reqdate;
    }

    public ResponsesEntity getResponsesById() {
        return responsesById;
    }

    public void setResponsesById(ResponsesEntity responsesById) {
        this.responsesById = responsesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestsEntity that = (RequestsEntity) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "RequestsEntityImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", reqdate=" + reqdate +
                ", responsesById=" + responsesById +
                '}';
    }
}
