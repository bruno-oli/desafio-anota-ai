package com.brunomax.desafioanotaai.services;

import com.brunomax.desafioanotaai.domain.category.Category;
import com.brunomax.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.brunomax.desafioanotaai.domain.products.Product;
import com.brunomax.desafioanotaai.domain.products.ProductDTO;
import com.brunomax.desafioanotaai.domain.products.exceptions.ProductNotFoundException;
import com.brunomax.desafioanotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product create(ProductDTO product) {
        Category category = this.categoryService.getById(product.categoryId()).orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(product);
        newProduct.setCategory(category);

        this.productRepository.save(newProduct);

        return newProduct;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (!productData.title().isEmpty()) product.setTitle(productData.title());
        if (!productData.description().isEmpty()) product.setDescription(productData.description());
        if (!(productData.price() == null)) product.setPrice(productData.price());
        if (!productData.categoryId().isEmpty()) this.categoryService.getById(productData.categoryId()).ifPresent(product::setCategory);

        this.productRepository.save(product);

        return  product;
    }

    public void delete(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
    }
}
