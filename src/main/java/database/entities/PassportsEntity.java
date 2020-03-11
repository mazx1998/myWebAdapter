package database.entities;

import javax.persistence.*;
import java.sql.Date;

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
        if (series != null ? !series.equals(that.series) : that.series != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (issueDate != null ? !issueDate.equals(that.issueDate) : that.issueDate != null) return false;
        if (issuer != null ? !issuer.equals(that.issuer) : that.issuer != null) return false;

        return true;
    }

}
