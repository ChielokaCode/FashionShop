//package com.chielokacodes.ec.utils;
//
//import com.chielokacodes.ec.entity.Product;
//import com.chielokacodes.ec.repository.ProductRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.math.BigDecimal;
//
//@Component
//public class CSVUtils {
//
//    private ProductRepository productRepository;
//
//    @Autowired
//    public CSVUtils(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    /////IF YOU ARE USING THE READER TO READ PRODUCT DETAILS IN CSV OR EXCEL FORMAT, WRITE A METHOD TO CHECK
//    ////THAT THE PRODUCTS ARE NOT DOUBLING ITSELF INTO THE DATABASE ANYTIME YOU RUN THE SERVER
//    ////OR WRITE METHOD TO MAKE THE BUFFERED READER ONLY RUN ONCE TO POPULATE DATABASE AND IF
//    ////ITS RUN AGAIN IT CHECKS THAT NO TWO PRODUCTS HAVE SAME PRODUCT NAME IN DATABASE AND SO DOESNT READ THE READ
//    /////ON CONCURRENT RUNS, IF IT FINDS A PRODUCT WITH PRODUCT NAME DIFFERENT FROM THOSE IN DATABASE, IT SAVES THAT ONE ONLY
//
//
//    @PostConstruct
//    public void readProductDetailsCSV() {
//        try
//                (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/chielokacodes/ec/utils/products.csv"))) {
//            String line;
//            boolean isFirstLine = false;
//            while ((line = reader.readLine()) != null) {
//                String[] product = line.split(",");
//                if (isFirstLine) {
//                    Product product1 = Product.builder()
//                            .productName(product[0])
//                            .price(new BigDecimal(product[1]))
//                            .quantity(Long.parseLong(product[2]))
//                            .category(product[3])
//                            .image(product[4])
//                            .description(product[5])
//                            .build();
//                    productRepository.save(product1);
//                }
//                isFirstLine = true;
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
