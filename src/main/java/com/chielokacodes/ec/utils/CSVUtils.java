//package com.chielokacodes.ec.utils;
//
//import com.chielokacodes.ec.entity.Admin;
//import com.chielokacodes.ec.entity.Product;
//import com.chielokacodes.ec.entity.User;
//import com.chielokacodes.ec.repository.AdminRepository;
//import com.chielokacodes.ec.repository.ProductRepository;
//import com.chielokacodes.ec.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.math.BigDecimal;
//
//@Component
//public class CSVUtils {
//
//    private ProductRepository productRepository;
//    private UserRepository userRepository;
//    private AdminRepository adminRepository;
//
//    @Autowired
//    public CSVUtils(ProductRepository productRepository, UserRepository userRepository, AdminRepository adminRepository){
//        this.productRepository = productRepository;
//        this.userRepository = userRepository;
//        this.adminRepository = adminRepository;
//
//    }
//
//    @PostConstruct
//    public void readUserCSV(){
//
//
////        try
////                (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/chielokacodes/ec/utils/users.csv")))
////        {
////            String line;
////            boolean isFirstLine = false;
////            while ((line = reader.readLine()) != null){
////                String[] users = line.split(",");
////                if(isFirstLine) {
////                    User user1 = User.builder()
////                            .email(users[0])
////                            .username(users[1])
////                            .password(users[2])
////                            .image(users[3])
////                            .build();
////                    userRepository.save(user1);
////                }
////                isFirstLine = true;
////            }
////
////        }catch(IOException e){
////        throw new RuntimeException(e);
////    }
//
//
////        try
////                (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/chielokacodes/ec/utils/products.csv")))
////        {
////            String line;
////            boolean isFirstLine = false;
////            while ((line = reader.readLine()) != null){
////                String[] product = line.split(",");
////                if(isFirstLine) {
////                    Product product1 = Product.builder()
////                            .category(product[0])
////                            .price(new BigDecimal(product[1]))
////                            .productName(product[2])
////                            .quantity(Long.parseLong(product[3]))
////                            .image(product[4])
////                            .build();
////                    productRepository.save(product1);
////                }
////                isFirstLine = true;
////            }
////
////        }catch(IOException e){
////            throw new RuntimeException(e);
////        }
//
////        try
////                (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/chielokacodes/ec/utils/admin.csv")))
////        {
////            String line;
////            boolean isFirstLine = false;
////            while ((line = reader.readLine()) != null){
////                String[] admin = line.split(",");
////                if(isFirstLine) {
////                    Admin admin1 = Admin.builder()
////                            .email(admin[0])
////                            .username(admin[1])
////                            .password(admin[2])
////                            .image(admin[3])
////                            .build();
////                    adminRepository.save(admin1);
////                }
////                isFirstLine = true;
////            }
////
////        }catch(IOException e){
////            throw new RuntimeException(e);
////        }
//
//    }
//}
