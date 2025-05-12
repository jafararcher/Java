package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchController(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * Handles search requests by querying the product database.
     *
     * @param query The search term entered by the user.
     * @param page  The page number for pagination (default is 0).
     * @param size  The size of the page for pagination (default is 10).
     * @return A paginated list of ProductItem objects that match the search query.
     */
    @GetMapping("/api/products/search")
    public Page<ProductItem> search(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        logger.info("Received search query: {}", query);

        // Validate query
        if (query == null || query.trim().isEmpty()) {
            logger.error("Invalid search query: query cannot be null or empty");
            throw new IllegalArgumentException("Search query cannot be null or empty");
        }

        try {
            Pageable pageable = PageRequest.of(page, size);
            return productItemRepository.findByNameContainingIgnoreCase(query, pageable);
        } catch (Exception e) {
            logger.error("An error occurred while processing the search request", e);
            throw new RuntimeException("An error occurred while processing the search request", e);
        }
    }
}
