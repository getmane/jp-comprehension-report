package jp.comprehesion.report.jpcomprehensionreport;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JpComprehensionReportApplication {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(JpComprehensionReportApplication.class);
        builder.headless(false);

        builder.run(args);
    }
}
