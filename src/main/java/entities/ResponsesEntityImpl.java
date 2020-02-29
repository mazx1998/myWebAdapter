package entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "responses", schema = "public", catalog = "adapterDataBase")
public class ResponsesEntityImpl extends MainEntity {
    private int id;
    private String snils;
    private String code;
    private String message;
    private String status;
    private Timestamp respdate;
    private RequestsEntityImpl requestsByReqId;

    public ResponsesEntityImpl() {}

    public ResponsesEntityImpl(String snils, String code, String message, String status,
                               Timestamp respdate, RequestsEntityImpl requestsByReqId) {
        this.snils = snils;
        this.code = code;
        this.message = message;
        this.status = status;
        this.respdate = respdate;
        this.requestsByReqId = requestsByReqId;
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
    @Column(name = "snils")
    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "respdate")
    public Timestamp getRespdate() {
        return respdate;
    }

    public void setRespdate(Timestamp respdate) {
        this.respdate = respdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponsesEntityImpl that = (ResponsesEntityImpl) o;

        if (id != that.id) return false;
        if (snils != null ? !snils.equals(that.snils) : that.snils != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (respdate != null ? !respdate.equals(that.respdate) : that.respdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (snils != null ? snils.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (respdate != null ? respdate.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "req_id", referencedColumnName = "id")
    public RequestsEntityImpl getRequestsByReqId() {
        return requestsByReqId;
    }

    public void setRequestsByReqId(RequestsEntityImpl requestsByReqId) {
        this.requestsByReqId = requestsByReqId;
    }

    @Override
    public String toString() {
        return "ResponsesEntityImpl{" +
                "id=" + id +
                ", snils='" + snils + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", respdate=" + respdate +
                ", reqId=" + requestsByReqId.getId() +
                '}';
    }
}
