package com.mathieupoitras.demo;

import com.mathieupoitras.demo.appdirect.security.OAuthSignatureVerificationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */

@SpringBootApplication
public class Application{

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }
    @Bean
    public FilterRegistrationBean registerOAuthSignatureVerificationFilter(OAuthSignatureVerificationFilter oAuthSignatureVerificationFilter) {
        if(oAuthSignatureVerificationFilter != null){
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(oAuthSignatureVerificationFilter);
            registration.addUrlPatterns("/appdirect/subscription/notification/*");
            registration.setName("OAuthSignature Filter");
            registration.setOrder(1);
            return registration;
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}