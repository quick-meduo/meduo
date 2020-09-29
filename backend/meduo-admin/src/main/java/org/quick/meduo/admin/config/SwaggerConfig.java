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
import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableSwagger2
//@EnableKnife4j
//@Import(BeanValidatorPluginsConfiguration.class)
//public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi(){
//        // 添加请求参数，我们这里把token作为请求头部参数传入后端
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        List<Parameter> parameters = new ArrayList<Parameter>();
//        parameterBuilder.name("token").description("用户令牌")
//                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        parameters.add(parameterBuilder.build());
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
//                .build().globalOperationParameters(parameters);
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder().build();
//    }
//}




@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean(value = "MEDUO管理中心")
    public Docket defaultApi() {
        ParameterBuilder parameterBuilder=new ParameterBuilder();
        List<Parameter> parameters= Lists.newArrayList();
//        parameterBuilder.name("token").description("token令牌").modelRef(new ModelRef("String"))
//                .parameterType("header")
//                .required(true).build();
//        parameters.add(parameterBuilder.build());

        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("MEDUO管理中心")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.quick.meduo.admin.controller"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters)
                .securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
        return docket;
    }
//    @Bean(value = "groupRestApi")
//    public Docket groupRestApi() {
//        List<ResolvedType> list=Lists.newArrayList();
//
//        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(groupApiInfo())
//                .groupName("分组接口")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.swagger.bootstrap.ui.demo.group"))
//                .paths(PathSelectors.any())
//                .build()
//                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(),securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(),apiKey1()));
//    }
//
//    private ApiInfo groupApiInfo(){
//        DeveloperApiInfoExtension apiInfoExtension=new DeveloperApiInfoExtension();
//
//        apiInfoExtension.addDeveloper(new DeveloperApiInfo("张三","zhangsan@163.com","Java"))
//                .addDeveloper(new DeveloperApiInfo("李四","lisi@163.com","Java"));
//
//
//        return new ApiInfoBuilder()
//                .title("swagger-bootstrap-ui很棒~~~！！！")
//                .description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
//                .termsOfServiceUrl("http://www.group.com/")
//                .contact("group@qq.com")
//                .version("1.0")
//                .extensions(Lists.newArrayList(apiInfoExtension))
//                .build();
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MEDUO PRO RESTful APIs")
                .description("# MEDUO PRO RESTful APIs")
                .termsOfServiceUrl("http://www.meduo.io/")
                .contact("meduo@foxmail.com")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Access-Token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}
