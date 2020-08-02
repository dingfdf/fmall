package indi.df.fmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "indi.df.fmall.manage.mapper")
public class FmallManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallManageServiceApplication.class, args);
    }

}
