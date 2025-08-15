package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${application.custom.jwt.clientId:user}")
	private String clientId;

	@Value("${application.custom.jwt.client-secret:password}")
	private String clientSecret;

	@Value("${application.custom.jwt.private-key:privateKey}")
	private String privateKey;

	@Value("${application.custom.jwt.public-key:publicKey}")
	private String publicKey;

	@Value("${application.custom.jwt.accessTokenValidititySeconds:43200}") // 12 hours
	private int accessTokenValiditySeconds;

	@Value("${application.custom.jwt.authorizedGrantTypes:password}")
	private String[] authorizedGrantTypes;

	@Value("${application.custom.jwt.refreshTokenValiditySeconds:2592000}") // 30 days
	private int refreshTokenValiditySeconds;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		// converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(tokenEnhancer());
	}
    
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(new BCryptPasswordEncoder().encode(clientSecret))
				.authorizedGrantTypes(authorizedGrantTypes).accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds).scopes("all");
	}

}
