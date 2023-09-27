package com.inn.cafe.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

//capital User in User.find.. bc referring to class name
//u is the alias, using JPQL
@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")

//uses lombok to generate getters and setters & default constructor
@Data
@Entity

// if you have an entity with several columns, but you only set values for a subset of those columns,
// Hibernate will generate an INSERT statement that includes only the columns with non-null values
@DynamicInsert

// if you load an entity from the database, make changes to some of its properties, and then save it back
// to the database, Hibernate will generate an UPDATE statement that includes only the columns that have changed.
@DynamicUpdate
@Table(name= "user")
public class User implements Serializable {
    private static final long  serialVersionUID= 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name="password")
    private String password;

    @Column (name="status")
    private String status;

    @Column(name= "role")
    private String role;

}
