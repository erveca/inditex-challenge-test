package com.inditex.prices.helper;

import com.inditex.prices.repository.ProductRepository;

/**
 * Helper class implementing the Singleton design pattern.
 */
public class ProductHelper {
    /**
     * The only one instance of this class.
     */
    private static ProductHelper INSTANCE;

    /**
     * Default constructor.
     */
    private ProductHelper() {
        // Nothing to do here
    }

    /**
     * Gets the singleton instance of this helper class.
     * It will create the instance the first time it is invoked.
     *
     * @return the singleton instance.
     */
    public synchronized static ProductHelper getInstance() {
        if (ProductHelper.INSTANCE == null) {
            ProductHelper.INSTANCE = new ProductHelper();
        }

        return ProductHelper.INSTANCE;
    }

    /**
     * Searches the product with the given ID in the product repository.
     *
     * @param productRepository The product repository.
     * @param productId         The product ID to search.
     * @return true if the product exists in the product repository.
     */
    public boolean productExists(final ProductRepository productRepository, final Long productId) {
        return productRepository.existsById(productId);
    }
}
