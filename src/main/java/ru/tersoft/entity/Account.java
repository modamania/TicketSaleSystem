package ru.tersoft.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "account")
@ApiModel(value = "Account")
public class Account implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "firstname", nullable = false)
    @ApiModelProperty(value = "firstname", required = true)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @ApiModelProperty(value = "lastname", required = true)
    private String lastname;

    @Column(name = "mail", nullable = false, unique = true)
    @ApiModelProperty(value = "mail", required = true)
    private String mail;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "password", required = true)
    private String password;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "admin")
    private Boolean admin;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
