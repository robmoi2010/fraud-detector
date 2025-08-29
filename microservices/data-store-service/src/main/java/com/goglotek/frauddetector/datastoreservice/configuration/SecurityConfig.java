/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice.configuration;

import com.goglotek.frauddetector.datastoreservice.service.UsersService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private String[] apiAccessTopLevelRoles = new String[]{
      "ROLE_USER", "ROLE_ADMIN"
  };

  @Autowired
  Config config;

  @Autowired
  UsersService usersService;

  @Autowired
  CustomJwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer();
    http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .with(authorizationServerConfigurer, Customizer.withDefaults())
        .authorizeHttpRequests((authorize) ->
            //authorize.anyRequest().hasAnyAuthority(apiAccessTopLevelRoles)
            authorize.anyRequest().authenticated()
        )
        .exceptionHandling((exceptions) -> exceptions
            .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        );
    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
      throws Exception {
    http.httpBasic(Customizer.withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        )
        .formLogin(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable());
    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient dataExtractor = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(config.getDataextractorClientId())
        .clientSecret(getPasswordEncoder().encode(config.getDataextractorClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofSeconds(config.getAccessTokenValiditySeconds()))
            .build())
        .scope("READ")
        .scope("WRITE")
        .scope(config.getMachineRoleName())
        .build();
    RegisteredClient dataProcessor = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(config.getDataprocessorClientId())
        .clientSecret(getPasswordEncoder().encode(config.getDataprocessorClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofSeconds(config.getAccessTokenValiditySeconds()))
            .build())
        .scope("READ")
        .scope("WRITE")
        .scope(config.getMachineRoleName())
        .build();
    RegisteredClient ui = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(config.getUiClientId())
        .clientSecret(getPasswordEncoder().encode(config.getUiClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(config.getRedirectUrl())
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofSeconds(config.getAccessTokenValiditySeconds()))
            .build())
        .scope("READ")
        .scope("WRITE")
        .build();
    RegisteredClient test = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(config.getTestClientId())
        .clientSecret(getPasswordEncoder().encode(config.getTestClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(config.getRedirectUrl())
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofSeconds(config.getAccessTokenValiditySeconds()))
            .build())
        .scope("READ")
        .scope(config.getMachineRoleName())
        .scope("WRITE")
        .build();

    return new InMemoryRegisteredClientRepository(dataExtractor, dataProcessor, ui, test);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource()
      throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    RSAPublicKey publicKey =
        (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey =
        (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
    return context -> {
      if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
        Authentication principal = context.getPrincipal();
        if (principal.getPrincipal() instanceof UserDetails user) {
          JwtClaimsSet.Builder claims = context.getClaims();
          claims.claim("username", user.getUsername());
          claims.claim("roles",
              user.getAuthorities().stream()
                  .map(GrantedAuthority::getAuthority)
                  .toList());
        }
        JwtClaimsSet.Builder claims = context.getClaims();
        claims.claim("username", principal.getPrincipal());
        claims.claim("roles",
            context.getAuthorizedScopes()
        );

      }
    };
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthoritiesClaimName("roles"); // match claim added in Auth Server
    authoritiesConverter.setAuthorityPrefix(""); // don’t prepend "ROLE_"

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    return converter;
  }


}