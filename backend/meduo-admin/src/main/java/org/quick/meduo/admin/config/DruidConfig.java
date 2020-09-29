/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.sql.SQLException;
import javax.servlet.Filter;

/**
  *@Description:
  * Druid Datasource configuration
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/12
  *@Since: 1.0
  */
 @Configuration
 @EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DruidConfig {
 @Autowired
 private DruidDataSourceProperties properties;

 @Bean
 @ConditionalOnMissingBean
 public DataSource druidDataSource() {
  DruidDataSource druidDataSource = new DruidDataSource();
  druidDataSource.setDriverClassName(properties.getDriverClassName());
  druidDataSource.setUrl(properties.getUrl());
  druidDataSource.setUsername(properties.getUsername());
  druidDataSource.setPassword(properties.getPassword());
  druidDataSource.setInitialSize(properties.getInitialSize());
  druidDataSource.setMinIdle(properties.getMinIdle());
  druidDataSource.setMaxActive(properties.getMaxActive());
  druidDataSource.setMaxWait(properties.getMaxWait());
  druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
  druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
  druidDataSource.setValidationQuery(properties.getValidationQuery());
  druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
  druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
  druidDataSource.setTestOnReturn(properties.isTestOnReturn());
  druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
  druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

  try {
   druidDataSource.setFilters(properties.getFilters());
   druidDataSource.init();
  } catch (SQLException e) {
   e.printStackTrace();
  }

  return druidDataSource;
 }

 /**
  * Register view
  * @return
  */
 @Bean
 @ConditionalOnMissingBean
 public ServletRegistrationBean<Servlet> druidServlet() {
  ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");

  //white list：
  servletRegistrationBean.addInitParameter("allow",properties.getWhiteList());
  //Sorry, you are not permitted to view this page.
  servletRegistrationBean.addInitParameter("deny",properties.getBlackList());
  //account to login backend
  servletRegistrationBean.addInitParameter("loginUsername", properties.getManname());
  servletRegistrationBean.addInitParameter("loginPassword", properties.getManword());
  //whether to reset.
  servletRegistrationBean.addInitParameter("resetEnable", "true");
  return servletRegistrationBean;
 }

 /**
  * Register filter
  *
  * @return
  */
 @Bean
 @ConditionalOnMissingBean
 public FilterRegistrationBean<Filter> filterRegistrationBean() {
  FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
  filterRegistrationBean.setFilter(new WebStatFilter());
  filterRegistrationBean.addUrlPatterns("/*");
  filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
  return filterRegistrationBean;
 }
}
