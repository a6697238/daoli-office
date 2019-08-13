package com.daoli.office.server.datasource;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Level4DatasourceConfig
 *
 * @author hl162981
 * @date 2018/7/2
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.daoli"}, sqlSessionFactoryRef =
        "sqlSessionFactory")
@Slf4j
public class DatasourceConfig {


    @Bean
    @ConfigurationProperties("spring.datasource")
    @ConditionalOnMissingBean
    public SpringDataSourceProperties dataSourceProperties() {
        return new SpringDataSourceProperties();
    }


    @Bean
    public DataSource daoliDataSource(
            @Qualifier("dataSourceProperties") SpringDataSourceProperties properties) {
        return DataSourceBuilder.create().username(properties.getUsername())
                .driverClassName(properties.getDriverClassName())
                .password(properties.getPassword()).url(properties.getUrl())
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("daoliDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        Resource[] resource1 = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/*.xml");
        Resource[] resource2 = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mybatis/**/*.xml");
        sessionFactoryBean.setMapperLocations(ArrayUtils.addAll(resource1, resource2));
        return sessionFactoryBean.getObject();
    }


}
