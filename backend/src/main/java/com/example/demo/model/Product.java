package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Long barcodeNo;
    private String productName;
    private String brand;
    private Long price;
    private String gender;
    private String category;
    @ElementCollection
    @CollectionTable(name = "product_size_stock", joinColumns = @JoinColumn(name = "product_id"))
    private List<SizeStock> sizeStocks;
    @ElementCollection
    @CollectionTable(name = "product_image_paths", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> imagePaths;
}