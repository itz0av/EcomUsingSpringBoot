package com.ecombackend.ecom.controller;

import com.ecombackend.ecom.model.Product;
import com.ecombackend.ecom.service.ProductService;
import jakarta.servlet.annotation.HttpConstraint;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin // it will help to connect to fetch api cause by default you cant allow to do that it will give a
@RequestMapping("/api")
public class ProductController {
    @Autowired //you should create constructor of controller or setter of controller;
    private ProductService service;

    @RequestMapping("/")
    public String greeting(){
        return "Hello! It working";
    }
    @GetMapping("/product")
    public List<Product> getAllProduct(){
        return service.getAllProduct();
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = service.getProductById(id);
        if(product != null){
        return new ResponseEntity<>(product,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try{
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable Long productId){
       Product product = service.getProductById(productId);
       byte[] imageFile = product.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable Long id, @RequestPart Product product,
                                               @RequestPart MultipartFile imageFile) {
        Product product1;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (Exception e) {
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        if (product1 != null)
            return new ResponseEntity<>("updated", HttpStatus.OK);
        else return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String>deleteProduct(@PathVariable Long id){
        Product product = service.getProductById(id);
        if(product != null){
            service.deleteProductByID(id);
            return new ResponseEntity<>("Product is deleted", HttpStatus.OK);
        }else return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {

        List<Product> products = service.searchProducts(keyword);
        System.out.println("searching with " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
