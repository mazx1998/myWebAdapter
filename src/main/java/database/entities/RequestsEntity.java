package database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Максим Зеленский
 * @since 05.04.2020
 */
@Entity
@Table(name = "requests", schema = "public", catalog = "adapterDataBase")
public class RequestsEntity extends MainEntity {
    private int id;
    private String requestXml;
    private String responseXml;
    private Timestamp requestDate;
    private Timestamp responseDate;
    private UsersEntity userId;

    public RequestsEntity() {
    }

    public RequestsEntity(String requestXml, String responseXml,
                          Timestamp requestDate, Timestamp responseDate,
                          UsersEntity userId) {
        this.requestXml = requestXml;
        this.responseXml = responseXml;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "request_xml", nullable = false, length = -1)
    public String getRequestXml() {
        return requestXml;
    }

    public void setRequestXml(String requestXml) {
        this.requestXml = requestXml;
    }

    @Basic
    @Column(name = "response_xml", nullable = true, length = -1)
    public String getResponseXml() {
        return responseXml;
    }

    public void setResponseXml(String responseXml) {
        this.responseXml = responseXml;
    }

    @Basic
    @Column(name = "request_date", nullable = false)
    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    @Basic
    @Column(name = "response_date", nullable = true)
    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestsEntity that = (RequestsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(requestXml, that.requestXml)) return false;
        if (!Objects.equals(responseXml, that.responseXml)) return false;
        if (!Objects.equals(requestDate, that.requestDate)) return false;
        if (!Objects.equals(responseDate, that.responseDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (requestXml != null ? requestXml.hashCode() : 0);
        result = 31 * result + (responseXml != null ? responseXml.hashCode() : 0);
        result = 31 * result + (requestDate != null ? requestDate.hashCode() : 0);
        result = 31 * result + (responseDate != null ? responseDate.hashCode() : 0);
        return result;
    }
}
