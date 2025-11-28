package org.example.gelahsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderGelah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "customerId must be not empty")
    @Column(nullable = false)
    private Integer clientId;

    @NotNull(message = "GelahId must be not empty")
    @Column(nullable = false)
    private Integer gelahId;

    @NotNull(message = "timeFrom must be not empty")
    @Column(nullable = false)
    private LocalTime timeFrom;

    @NotNull(message = "timeTo time must be not empty")
    @Column(nullable = false)
    private LocalTime timeTo;

    @NotEmpty(message = "location must be not empty")
    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "varchar(20) default 'pending'")
    private String status;

    @NotNull(message = "date must be not empty")
    @Column(nullable = false)
    private LocalDate date;

    private Integer price;

    private LocalDate createdAt;
}
