package ${basePackageValue};

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"${basePackageValue}","com.cgr.common"})
public class Application {

	/*代码分界head TODO*/
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/*代码分界end TODO*/

}

