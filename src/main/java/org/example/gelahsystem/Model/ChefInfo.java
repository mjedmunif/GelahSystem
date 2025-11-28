package org.example.gelahsystem.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChefInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "gelah id must be not empty")
    @Column(nullable = false)
    private Integer gelahId;

    @NotEmpty(message = "name must be not empty")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "speciality must be not empty")
    @Column(nullable = false)
    private String speciality;

    @NotNull(message = "price must be not empty")
    @Column(nullable = false)
    private Integer price;

    @NotNull(message = "experience must be not empty")
    @Column(nullable = false)
    private Integer experience;

    @NotEmpty(message = "phone number must be not empty")
    @Column(nullable = false)
    private String phoneNumber;


}
