package com.informatorio.storeinformatorio.controller;

import com.informatorio.storeinformatorio.entity.Product;
import com.informatorio.storeinformatorio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(ProductController.URL)
public class ProductController {

    public static final String URL = "api/v1/product";

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>( productService.getAllProducts(), HttpStatus.OK );
    }
    @PostMapping
    public ResponseEntity<?> postProduct(@Valid @RequestBody Product product){
        return new ResponseEntity<>( productService.createProduct(product), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity<?> putProduct(@RequestParam(name = "id") Long id,
                                     @RequestBody @Valid Product product){

        Product productDB = productService.putProduct(id, product);

        return (productDB != null) ?
                ResponseEntity.ok(productDB) : ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<?> patchProduct(@RequestParam (name = "id") Long id,
                                          @RequestBody Map<String, ?> product){
        Product productDB = productService.patchProduct(id, product);
        return (productDB != null) ?
                ResponseEntity.ok(productDB) : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam (name = "id") Long id){
        Product productDeleted = productService.deleteProduct(id);
        return (productDeleted != null) ?
                new ResponseEntity<>(HttpStatus.OK) : ResponseEntity.notFound().build();
    }

}
