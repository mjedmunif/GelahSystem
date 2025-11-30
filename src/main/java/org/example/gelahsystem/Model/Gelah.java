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
public class Gelah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "location must be not empty")
    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "int default 0")
    private Double rating;

    @Column(columnDefinition = "boolean default false")
    private Boolean hasChef;

    @NotNull(message = "price must be not empty")
    @Column(nullable = false)
    private Integer price;

    @Column(columnDefinition = "varchar(20) default 'available'")
    private String status;

    @NotNull(message = "ownerId must be not empty")
    @Column(nullable = false)
    private Integer ownerId;

}
