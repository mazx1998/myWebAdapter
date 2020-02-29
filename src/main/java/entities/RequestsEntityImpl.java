package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "requests", schema = "public", catalog = "adapterDataBase")
public class RequestsEntityImpl extends MainEntity {
    private int id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String gender;
    private Date birthdate;
    private Date reqdate;
    private ResponsesEntityImpl responsesById;

    public RequestsEntityImpl() {}

    public RequestsEntityImpl(String firstname, String lastname,String patronymic,
                              String gender, Date birthdate, Date reqdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthdate = birthdate;
        this.reqdate = reqdate;
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
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
    @Column(name = "birthdate")
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "reqdate")
    public Date getReqdate() {
        return reqdate;
    }

    public void setReqdate(Date reqdate) {
        this.reqdate = reqdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestsEntityImpl that = (RequestsEntityImpl) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (birthdate != null ? !birthdate.equals(that.birthdate) : that.birthdate != null) return false;
        if (reqdate != null ? !reqdate.equals(that.reqdate) : that.reqdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (reqdate != null ? reqdate.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "requestsByReqId")
    public ResponsesEntityImpl getResponsesById() {
        return responsesById;
    }

    public void setResponsesById(ResponsesEntityImpl responsesById) {
        this.responsesById = responsesById;
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
