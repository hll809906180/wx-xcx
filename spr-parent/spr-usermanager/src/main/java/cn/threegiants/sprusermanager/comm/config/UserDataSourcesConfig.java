package cn.threegiants.sprusermanager.comm.config;

import cn.threegiants.sprusermanager.comm.util.RSAUtils;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description: 用户管理数据源配置
 * @author: he.l
 * @create: 2019-04-25 16:57
 **/
@Configuration
@MapperScan(basePackages = {"cn.threegiants.sprusermanager.business.text.mapper"}, sqlSessionFactoryRef = "userSqlSessionFactory")
public class UserDataSourcesConfig {
    private static final String MAPPER_LOCAL = "classpath:mybatis/usermanger/*.xml";

    @ConfigurationProperties("spring.datasource.usermanger")
    @Primary
    @Bean(name = "userDataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource ;
    }

    @Bean(name = "userSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("userDataSource") DruidDataSource dataSource) throws Exception {
        dataSource.setPassword(RSAUtils.decrypt(dataSource.getPassword()));
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCAL));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "userTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("userDataSource") DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
