package restapi.pojo.request.out;

import database.entities.PassportsEntity;

/**
 * @author Максим Зеленский
 * @since 30.03.2020
 */
public class PassportsPojo {
    private int id;
    private String series;
    private String number;
    private Long issue_date;
    private String issuer;

    public PassportsPojo() {
    }

    public PassportsPojo(PassportsEntity passportsEntity) {
        id = passportsEntity.getId();
        series = passportsEntity.getSeries();
        number = passportsEntity.getNumber();
        issue_date = passportsEntity.getIssueDate().getTime();
        issuer = passportsEntity.getIssuer();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
