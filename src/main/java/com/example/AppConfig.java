package com.example;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SimpleDataSource;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;


/**
 * Springの設定用クラス
 */
@Configuration
@EnableTransactionManagement
public class AppConfig {

    // ---------------------------
    //  thymeleaf用の設定
    // ---------------------------

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CharacterEncodingFilter characterEncodingFilter(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        return filter;
    }

    @Bean
    InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }


    // ---------------------------
    //  Doma用の設定
    // ---------------------------

    @Autowired
    DataSourceProperties dataSourceProperties;

    DataSource realDataSource(){
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUrl( dataSourceProperties.getUrl());
        dataSource.setUser( dataSourceProperties.getUsername());
        dataSource.setPassword( dataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean
    DataSource dataSource(){
        /*
            TransactionAwareDataSourceProxyでラップしないとDomaのコネクションが
            Springの管理外になって実行時例外発生時にRollbackされない
          */
        return new TransactionAwareDataSourceProxy(
                    new Log4jdbcProxyDataSource(
                            this.realDataSource()
                    )
        );
    }

    @Bean
    Dialect dialect(){
        return new H2Dialect();
    }

    @Bean
    SqlFileRepository sqlFileRepository(){
        return new NoCacheSqlFileRepository();
    }

    @Bean
    Config config(){
        return new Config() {
            @Override
            public DataSource getDataSource() {
                return dataSource();
            }

            @Override
            public Dialect getDialect() {
                return dialect();
            }

            @Override
            public SqlFileRepository getSqlFileRepository(){
                return sqlFileRepository();
            }
        };
    }
}
