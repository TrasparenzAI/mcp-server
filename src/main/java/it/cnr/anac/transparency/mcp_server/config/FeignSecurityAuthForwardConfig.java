/*
 * Copyright (C) 2025 Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package it.cnr.anac.transparency.mcp_server.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Feign RequestInterceptor that forwards the inbound Bearer token (if present)
 * from Spring Security's SecurityContext to all outbound Feign requests.
 */
@Configuration
public class FeignSecurityAuthForwardConfig {

    @Bean
    public RequestInterceptor securityContextAuthForwardingInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    // Support both JwtAuthenticationToken and BearerTokenAuthentication
                    if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                        Jwt jwt = jwtAuth.getToken();
                        if (jwt != null && jwt.getTokenValue() != null && !jwt.getTokenValue().isEmpty()) {
                            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
                            return;
                        }
                    } else if (authentication instanceof BearerTokenAuthentication bearerAuth) {
                        AbstractOAuth2Token tokenObj = bearerAuth.getToken();
                        if (tokenObj != null && tokenObj.getTokenValue() != null && !tokenObj.getTokenValue().isEmpty()) {
                            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenObj.getTokenValue());
                            return;
                        }
                    }
                }

                // Fallback: if SecurityContext is not populated, try the raw HTTP request header
                var attrs = RequestContextHolder.getRequestAttributes();
                if (attrs instanceof ServletRequestAttributes sra) {
                    String auth = sra.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                    if (auth != null && !auth.isEmpty()) {
                        template.header(HttpHeaders.AUTHORIZATION, auth);
                    }
                }
            }
        };
    }
}
