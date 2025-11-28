package org.example.gelahsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "fistName must be not empty")
    @Column(nullable = false)
    private String firstName;

    @NotEmpty(message = "lastName must be not empty")
    @Column(nullable = false)
    private String lastName;

    @NotEmpty(message = "username must be not empty")
    @Column(nullable = false)
    private String username;

    @NotEmpty(message = "phone number must be not empty")
    @Column(nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "email must be not empty")
    @Column(nullable = false , unique = true)
    private String email;

    @NotEmpty(message = "password must be not empty")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "city must be not empty")
    @Column(nullable = false)
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birth date must be not empty")
    @Column(nullable = false)
    private LocalDate birthDate;

    private LocalDate createdAt;

}
