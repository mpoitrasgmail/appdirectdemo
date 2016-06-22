package com.mathieupoitras.demo.appdirect.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureVerificationFilter.BYPASS_PROFILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class OAuthSignatureVerificationFilterTest {
    private OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = mock(OAuthSignatureAuthenticationProvider.class);
    private OAuthSignatureParserService oAuthSignatureParserService = mock(OAuthSignatureParserService.class);

    private ApplicationContext applicationContext = mock(ApplicationContext.class);
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    private FilterChain chain = mock(FilterChain.class);

    @Test
    public void whenVerifyingOAuthSignatureAndCredentialsAreValidThenChainDoFilterIsCalled() throws IOException, ServletException {
        OAuthSignatureVerificationFilter oAuthSignatureVerificationFilter = buildClassUnderTest();

        OAuthSignature oAuthSignature = mock(OAuthSignature.class);
        when(oAuthSignatureParserService.parse(request)).thenReturn(oAuthSignature);
        when(oAuthSignatureAuthenticationProvider.authenticate(oAuthSignature)).thenReturn(true);


        oAuthSignatureVerificationFilter.doFilter(request, response, chain);


        verify(chain).doFilter(request, response);
    }

    @Test
    public void whenVerifyingOAuthSignatureAndCredentialsAreUnparseableThenChainDoFilterIsNeverCalled() throws IOException, ServletException {
        OAuthSignatureVerificationFilter oAuthSignatureVerificationFilter = buildClassUnderTest();

        when(oAuthSignatureParserService.parse(request)).thenThrow(UnparseableOAuthSignatureException.class);


        oAuthSignatureVerificationFilter.doFilter(request, response, chain);


        verify(chain, never()).doFilter(request, response);
        assertThat(response.getStatus(), is(HttpServletResponse.SC_FORBIDDEN));
    }

    @Test
    public void whenVerifyingOAuthSignatureAndCredentialsAreInvalidThenChainDoFilterIsNeverCalled() throws IOException, ServletException {
        OAuthSignatureVerificationFilter oAuthSignatureVerificationFilter = buildClassUnderTest();

        OAuthSignature oAuthSignature = mock(OAuthSignature.class);
        when(oAuthSignatureParserService.parse(request)).thenReturn(oAuthSignature);
        when(oAuthSignatureAuthenticationProvider.authenticate(oAuthSignature)).thenReturn(false);


        oAuthSignatureVerificationFilter.doFilter(request, response, chain);


        verify(chain, never()).doFilter(request, response);
    }

    private OAuthSignatureVerificationFilter buildClassUnderTest() {
        OAuthSignatureVerificationFilter oAuthSignatureVerificationFilter = new OAuthSignatureVerificationFilter(oAuthSignatureAuthenticationProvider, oAuthSignatureParserService);
        oAuthSignatureVerificationFilter.setApplicationContext(applicationContext);
        Environment environment = mock(Environment.class);
        when(applicationContext.getEnvironment()).thenReturn(environment);

        when(environment.acceptsProfiles(BYPASS_PROFILE)).thenReturn(false);
        return oAuthSignatureVerificationFilter;
    }

}