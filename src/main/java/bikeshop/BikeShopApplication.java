package bikeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BikeShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeShopApplication.class, args);
    }

}
