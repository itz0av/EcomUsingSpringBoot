package com.ecombackend.ecom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
//@Column(name = "release_date")  // Match SQL column name
//@JsonFormat(pattern = "yyyy-MM-dd")  // Match SQL format
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd--MM-yyyy")
    private Date releaseDate;
    private boolean available;
    private int quantity;
    //    image ?
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}