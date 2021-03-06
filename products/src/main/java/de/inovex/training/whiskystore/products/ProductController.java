package de.inovex.training.whiskystore.products;

import de.inovex.training.whiskystore.products.images.ImageLoader;
import de.inovex.training.whiskystore.products.images.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository repository;

    private final ImageLoader imageLoader;

    @Autowired
    public ProductController(ProductRepository repository, ImageLoader imageLoader) {
        this.repository = repository;
        this.imageLoader = imageLoader;
    }

    @RequestMapping(path = "/products",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> listAllProducts() {
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(path = "/products/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> findProduct(@PathVariable("id") long id) {
        Product product = repository.findOne(id);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(product);
    }

    @RequestMapping(path = "/products/{id}/images",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductImage>> findImagesOfProduct(@PathVariable("id") long id) {
        Product product = repository.findOne(id);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(imageLoader.loadImagesForProduct(id));
    }

}
