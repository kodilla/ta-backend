package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.products.Product;
import com.kodilla.ecommercee.domain.products.ProductDto;
import com.kodilla.ecommercee.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductService;
import com.opencsv.CSVReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productMapper.mapToProductDtoList(productService.getAllProducts());
    }

    @GetMapping(value = "/{id}")
    public ProductDto getProduct(@PathVariable("id") long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productService.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId)));
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productMapper.mapToProduct(productDto));
    }

    @PatchMapping(value = "/{id}")
    public ProductDto updateProduct(@PathVariable("id") long id, @RequestBody ProductDto productDto) throws ProductNotFoundException{
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(productDto.getProductId()));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDeleted(productDto.isDeleted());
        productService.saveProduct(product);

        return productMapper.mapToProductDto(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProduct(productId);
    }

    @PostMapping(value = "addNewProducts")
    public void addNewsFromCSV(@RequestParam String header)throws IOException {

        try (CSVReader csvReader = new CSVReader(new FileReader("resources/products.csv"));) {
            String[] values = null;

            if(header.equalsIgnoreCase("yes")){
                csvReader.readNext();
            }

            while ((values = csvReader.readNext()) != null) {

                values = values[0].split(";");
                productService.saveProduct(new Product.ProductBuilder().name(values[0])
                        .description(values[1])
                        .price(new BigDecimal(values[2]))
                        .quantity(0).build());

            }
        }catch (IOException e){
            LOGGER.error("Failed to process import form .CSV file: ", e.getMessage(), e);
        }
    }
}