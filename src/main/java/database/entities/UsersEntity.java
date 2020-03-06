package database.entities;

import database.entities.MainEntity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "adapterDataBase")
public class UsersEntity extends MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "login")
    private String login;

    @Basic
    @Column(name = "hash")
    private String hash;

    public UsersEntity() {}

    public UsersEntity(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
