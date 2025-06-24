package com.abhishek.ecommerce.service;

import com.abhishek.ecommerce.model.Product;
import com.abhishek.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts()
    {
        return repository.findAll();
    }

    public Product getProduct(int id)
    {
        return repository.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException
    {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repository.save(product);
    }

    public Product deleteProduct(int id)
    {
        Product p = repository.findById(id).orElse(null);

        if(p != null)
        {
            repository.deleteById(id);
            return p;
        }

        return null;
    }


    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException
    {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        repository.save(product);
        return product;

    }
}
