package cn.threegiants.sprusermanager.comm.config;

import cn.threegiants.sprusermanager.comm.filter.DomainFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 过滤器配置类
 * @author: he.l
 * @create: 2019-04-25 11:34
 **/
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        DomainFilter domainFilter = new DomainFilter();
        registrationBean.setFilter(domainFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
