package indi.df.fmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "indi.df.fmall.order.mapper")
public class FmallOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallOrderServiceApplication.class, args);
    }

}
