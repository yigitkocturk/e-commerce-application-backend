package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.SizeStock;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.ProductCreateRequest;
import com.example.demo.response.ProductResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final String FOLDER_PATH =  "C:\\Users\\iknow\\Desktop\\Photos\\";
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResponse> getAllProducts(Optional<Long> barcodeNo) {
        List<Product> productList;
        if (barcodeNo.isPresent()) {
            productList = productRepository.findByBarcodeNo(barcodeNo.get());
        } else {
            productList = productRepository.findAll();
        }
        return productList.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
    public ProductResponse getOneProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            return new ProductResponse(product);
        }
        return null;
    }
    public ProductResponse createOneProduct(ProductCreateRequest newProductRequest) throws IOException {
        Product product = new Product();
        product.setBarcodeNo(newProductRequest.getBarcodeNo());
        product.setProductName(newProductRequest.getProductName());
        product.setBrand(newProductRequest.getBrand());
        product.setPrice(newProductRequest.getPrice());
        product.setGender(newProductRequest.getGender());
        product.setCategory(newProductRequest.getCategory());
        product.setSizeStocks(newProductRequest.getSizeStocks());
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile imageFile : newProductRequest.getImageFiles()) {
            String imageExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
            String uniqueImageName = UUID.randomUUID().toString() + "." + imageExtension;
            String imagePath = FOLDER_PATH + uniqueImageName;
            imagePaths.add(imagePath);
            imageFile.transferTo(new File(imagePath));
        }
        product.setImagePaths(imagePaths);

        Product savedProduct = productRepository.save(product);

        if (savedProduct != null) {
            return new ProductResponse(savedProduct);
        }

        return null;
    }

    public ProductResponse updateProduct(Long productId, ProductCreateRequest updatedProductRequest) throws IOException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        product.setPrice(updatedProductRequest.getPrice());
        product.setSizeStocks(updatedProductRequest.getSizeStocks());
        Product savedProduct = productRepository.save(product);
        if (savedProduct != null) {
            return new ProductResponse(savedProduct);
        }
        return null;
    }
    public boolean decrementStock(Long productId, String size) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            List<SizeStock> sizeStocks = product.getSizeStocks();
            Optional<SizeStock> targetSizeStock = sizeStocks.stream()
                    .filter(ss -> ss.getSize().equals(size))
                    .findFirst();

            if (targetSizeStock.isPresent()) {
                SizeStock sizeStock = targetSizeStock.get();
                int currentStock = sizeStock.getStock();
                if (currentStock > 0) {
                    sizeStock.setStock(currentStock - 1);
                    productRepository.save(product);
                    return true; // Stock decremented successfully
                }
            }
        }
        return false; // Stock decrement failed
    }
    public boolean deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
