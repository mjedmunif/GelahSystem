package org.example.gelahsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "customerId must be not empty")
    @Column(nullable = false)
    private Integer clientId;

    @NotNull(message = "gelah id must be not Empty")
    @Column(nullable = false)
    private Integer gelahId;

    @NotNull(message = "rating must be not empty")
    @Column(nullable = false)
    private Double rating;

    private String comment;

    private LocalDate createdAt;


}
