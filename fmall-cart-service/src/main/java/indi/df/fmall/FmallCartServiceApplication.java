package indi.df.fmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "indi.df.fmall.cart.mapper")
public class FmallCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallCartServiceApplication.class, args);
    }

}
