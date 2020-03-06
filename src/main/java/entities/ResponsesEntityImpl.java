package entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "responses", schema = "public", catalog = "adapterDataBase")
public class ResponsesEntityImpl extends MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "snils")
    private String snils;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "message")
    private String message;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "respdate")
    private Timestamp respdate;

    @OneToOne
    @JoinColumn(name = "req_id", referencedColumnName = "id")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRespdate() {
        return respdate;
    }

    public void setRespdate(Timestamp respdate) {
        this.respdate = respdate;
    }

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
