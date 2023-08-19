package com.example.demo.request;

import com.example.demo.model.SizeStock;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductCreateRequest {
    private Long barcodeNo;
    private String brand;
    private String productName;
    private Long price;
    private String gender;
    private String category;
    private List<SizeStock> sizeStocks;
    private List<MultipartFile> imageFiles;
}