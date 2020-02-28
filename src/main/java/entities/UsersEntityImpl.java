package entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "adapterDataBase")
public class UsersEntityImpl extends MainEntity {
    private int id;
    private String login;
    private String hash;

    public UsersEntityImpl() {}

    public UsersEntityImpl(String login, String hash) {
        this.login = login;
        this.hash = hash;
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
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntityImpl that = (UsersEntityImpl) o;

        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsersEntityImpl{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
