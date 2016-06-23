package com.mathieupoitras.demo.appdirect.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
@Component
public class OAuthSignatureVerificationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(OAuthSignatureVerificationFilter.class);
    public static final String BYPASS_PROFILE = "bypassAppDirectOAuthFilter";

    private final OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider;
    private final OAuthSignatureParserService oAuthSignatureParserService;
    private ApplicationContext applicationContext;

    @Autowired
    public OAuthSignatureVerificationFilter(OAuthSignatureAuthenticationProvider authenticationManager,
                                            OAuthSignatureParserService oAuthSignatureParserService) {
        this.oAuthSignatureAuthenticationProvider = authenticationManager;
        this.oAuthSignatureParserService = oAuthSignatureParserService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("Entering OAuth Sigature Verification Filter");
        if(applicationContext.getEnvironment().acceptsProfiles(BYPASS_PROFILE)){
            logger.warn("Bypassing the OAuthSignatureVerificationFilter");
            chain.doFilter(request, response);
        }else{
            //The following casts are safe as long as the container used implements web compliant servlet. This is our case.
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;


            try{
                OAuthSignature oAuthSignature = oAuthSignatureParserService.parse(httpServletRequest);
                if (oAuthSignatureAuthenticationProvider.authenticate(oAuthSignature)) {
                    logger.debug("OAuth 1.0 Signature Verification successful");
                    chain.doFilter(request, response);
                }
                else{
                    logger.info("OAuth 1.0 Signature Verification Failed!");
                    buildForbiddenResponse(httpServletResponse);
                }
            }
            catch(UnparseableOAuthSignatureException uoase){
                logger.info("OAuth 1.0 Signature Parsing Failed!");
                buildForbiddenResponse(httpServletResponse);
            }

            logger.debug("Leaving OAuth Sigature Verification Filter");
        }
    }

    private void buildForbiddenResponse(HttpServletResponse response) throws IOException {
        HttpServletResponseWrapper httpServletResponseWrapper = new HttpServletResponseWrapper(response);
        httpServletResponseWrapper.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
