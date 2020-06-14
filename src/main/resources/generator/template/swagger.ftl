package ${basePackageValue};

import java.util.ArrayList;
import java.util.List;

//swagger2的配置文件，在项目的启动类的同级文件建立

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicates;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {

	@Bean
	public Docket createRestApi() {
		 
		ParameterBuilder ticketPar = new ParameterBuilder();
		ticketPar.name("Authorization").description("登录校验")
		.modelRef(new ModelRef("string")).parameterType("header")
		.required(false).defaultValue("Bearer ").build();
		
		List<Parameter> pars = new ArrayList<Parameter>();    
		pars.add(ticketPar.build());
	        
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		 
		com.google.common.base.Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("${basePackageValue}.base.controller");
		com.google.common.base.Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("${basePackageValue}.kernel.controller");
		return new Docket(DocumentationType.SWAGGER_2)
		.globalResponseMessage(RequestMethod.POST, responseMessageList)
		.apiInfo(apiInfo())
		.select()
		//此处为controller包路径
		.apis(Predicates.or(selector1,selector2))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	    .title("世纪金源ITSSC项目API-DOC")
	    .description("${businessComment}接口在线文档")
	    .termsOfServiceUrl("http://*****/")
	    .contact(new Contact("世纪金源", "", ""))
	    .version("1.0")
	    .build();
	}
}

