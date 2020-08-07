package com.snailtraveller.learningspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true) // to ignore fullName and date of birth
public class User {
    private final UUID userUid;
    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;

    @NotNull
    private final Gender gender;

    @NotNull
    @Max(120)
    @Min(0)
    private final Integer age;

    @NotNull
    @Email
    private final String email;

    public  enum Gender{
        MALE,
        FEMALE
    }

    public User(
            @JsonProperty("userUid") UUID userUid,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("gender") Gender gender,
            @JsonProperty("age") Integer age,
            @JsonProperty("email") String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    //@JsonProperty("id")
    public UUID getUserUid() {
        return userUid;
    }

   //@JsonIgnore
    public String getFirstName() {
        return firstName;
    }
    //@JsonIgnore
    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public LocalDate getDateOfBirth(){
       return LocalDate.now().minusYears(age);
    }

    public static User newUser(UUID userUid, User user){
        return new User(userUid,user.getFirstName(), user.getLastName(), user.getGender(), user.getAge(), user.getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid=" + userUid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
