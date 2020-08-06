package indi.df.fmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "indi.df.fmall.payment.mapper")
public class FmallPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallPaymentApplication.class, args);
    }

}
