package com.job.application.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.job.application.entity.Product;
import com.job.application.repository.CategoryRepository;
import com.job.application.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${setup.data.quantity}")
    private Integer addQuantity;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static Logger logger = LoggerFactory.getLogger(SetupDataLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String resultJson = restTemplate.getForObject("https://gorest.co.in/public-api/products", String.class);
            Iterator<JsonNode> iterator = new ObjectMapper().readValue(resultJson, ObjectNode.class).get("data").iterator();
            Integer productsAdded = 0;

            while(iterator.hasNext()) {
                if (productsAdded >= addQuantity) {
                    break;
                }

                createProduct(iterator.next());
                productsAdded ++;
            };

        } catch (JsonProcessingException ex) {
            logger.error("error parsing json", ex);
        }
    }

    private void createProduct(JsonNode productNode) {
        try {
            Product product = new ObjectMapper().treeToValue(productNode, Product.class);

            if (product.getCategories() != null) {
                product.getCategories().forEach((category -> {
                    categoryRepository.save(category);
                }));
            }

            productRepository.save(product);

        } catch (JsonProcessingException ex) {
            logger.error("error parsing json", ex);
        }
    }
}
