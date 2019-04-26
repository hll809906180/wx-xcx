package cn.threegiants.sprusermanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 初始化类
 * 
 * @author billy
 * @description TODO
 * @date 2018/2/23 9:52
 */
@Configuration
@EnableSwagger2 // 启用Swagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {// 创建API基本信息
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("cn.threegiants.sprusermanager"))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {// 创建API的基本信息，这些信息会在Swagger UI中进行显示
		return new ApiInfoBuilder().title("leadeon流量平台订单服务API")// API 标题
				.description("提供超市员工管理服务RESTful APIs")// API描述
				.contact(new Contact("李旭明", "http://www.leadeon.cn", "lixuming@leadeon.cn"))// 联系人
				.version("2.0-SNAPSHOT")// 版本号
				.build();
	}

}
