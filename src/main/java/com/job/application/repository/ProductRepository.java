package com.job.application.repository;

import com.job.application.ProductDTO;
import com.job.application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p.id," +
            "              p.name," +
            "              p.description," +
            "              p.amount AS price," +
            "              p.discount_amount AS discountAmount," +
            "              p.status" +
            "         FROM product AS p" +
            "        WHERE :quantity IS NULL" +
            "           OR (SELECT count(sub.category_id)" +
            "                 FROM product_category AS sub" +
            "                WHERE sub.product_id = p.id) = :quantity",
           nativeQuery = true)
    List<ProductDTO> findByQuantityCategories(Long quantity);

}
