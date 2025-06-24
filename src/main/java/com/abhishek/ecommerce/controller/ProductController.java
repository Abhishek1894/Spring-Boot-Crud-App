package com.abhishek.ecommerce.controller;

import com.abhishek.ecommerce.model.Product;
import com.abhishek.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductController
{

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {
        List<Product> list = service.getProducts();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id)
    {
        Product product = service.getProduct(id);

        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        try
        {
            Product p = service.addProduct(product, imageFile);
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/products/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable  int id)
    {
        Product p = service.getProduct(id);
        byte[] image = p.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(p.getImageType())).body(image);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable  int id)
    {
        Product p = service.deleteProduct(id);

        return p == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        try
        {
            System.out.println("UPDATE INITIATED");
            System.out.println("updating : " + id);
            Product p = service.updateProduct(id, product, imageFile);
            return p == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(p, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
