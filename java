package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchController(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * Handles search requests by querying the product database.
     *
     * @param query The search term entered by the user.
     * @return A list of ProductItem objects that match the search query.
     */
    @GetMapping("/api/products/search")
    public List<ProductItem> search(@RequestParam("query") String query) {
        return productItemRepository.findByNameContainingIgnoreCase(query);
    }
}
