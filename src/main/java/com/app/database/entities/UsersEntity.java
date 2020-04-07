package com.app.database.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Максим Зеленский
 * @since 05.04.2020
 */
@Entity
@Table(name = "users", schema = "public", catalog = "adapterDataBase")
public class UsersEntity extends MainEntity{
    private int id;
    private String login;
    private String password;
    private String role;
    private List<RequestsEntity> requests;

    public UsersEntity() {
    }

    public UsersEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
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
    @Column(name = "login", nullable = false, length = 50)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 50)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RequestsEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestsEntity> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(login, that.login)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(role, that.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
