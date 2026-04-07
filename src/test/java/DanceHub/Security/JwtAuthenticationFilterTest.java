package DanceHub.Security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class JwtAuthenticationFilterTest {

    @Test
    void invalidTokenReturnsUnauthorized() throws Exception {
        JwtService jwtService = mock(JwtService.class, invocation -> {
            throw new io.jsonwebtoken.JwtException("invalid");
        });
        CustomUserDetailsService customUserDetailsService = mock(CustomUserDetailsService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, customUserDetailsService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(401);
    }
}
