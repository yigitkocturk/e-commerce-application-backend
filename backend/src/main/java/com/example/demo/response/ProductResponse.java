package com.example.demo.response;

import com.example.demo.model.Product;
import com.example.demo.model.SizeStock;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
public class ProductResponse {
    private final ArrayList<Object> imageBase64List;
    private Long id;
    private Long barcodeNo;
    private String brand;
    private String productName;
    private Long price;
    private String gender;
    private String category;
    private List<SizeStock> sizeStocks;
    private List<String> imagePaths;

    public ProductResponse(Product entity) {
        this.id = entity.getId();
        this.barcodeNo = entity.getBarcodeNo();
        this.brand = entity.getBrand();
        this.productName = entity.getProductName();
        this.price = entity.getPrice();
        this.gender = entity.getGender();
        this.category = entity.getCategory();
        this.sizeStocks = entity.getSizeStocks();
        this.imageBase64List = new ArrayList<>();
        for (String imagePath : entity.getImagePaths()) {
            String base64Image = convertImageToBase64(imagePath);
            this.imageBase64List.add(base64Image);
        }
    }

    private String convertImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
