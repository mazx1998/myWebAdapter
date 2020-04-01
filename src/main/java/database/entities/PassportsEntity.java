package database.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Objects;

/**
 * @author Максим Зеленский
 * @since 11.03.2020
 */
@Entity
@Table(name = "passports", schema = "public", catalog = "adapterDataBase")
public class PassportsEntity extends MainEntity {
    private int id;
    private String series;
    private String number;
    private Date issueDate;
    private String issuer;

    public PassportsEntity() {
    }

    public PassportsEntity(String series, String number,
                           Date issueDate, String issuer) {
        this.series = series;
        this.number = number;
        this.issueDate = issueDate;
        this.issuer = issuer;
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
    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "issue_date")
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Basic
    @Column(name = "issuer")
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassportsEntity that = (PassportsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(series, that.series)) return false;
        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(issueDate, that.issueDate)) return false;
        if (!Objects.equals(issuer, that.issuer)) return false;

        return true;
    }
}
