
package com.hakerton.ecoBytes.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                headers().frameOptions().sameOrigin() // (1)
                .and()
                .csrf().disable()        // (2)
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(Arrays.asList("http://localhost:5501")); // 특정 도메인 허용
                            // or
                            // configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 도메인 허용 (패턴 이용)
                            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE","OPTIONS")); // 특정 HTTP 메서드 허용
                            configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
                            configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));
                            configuration.setAllowCredentials(true);
                            configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin",
                                    "Access-Control-Allow-Methods",
                                    "Access-Control-Allow-Headers")); // CORS 관련 헤더 포함
                            return configuration;
                        }))
//                .cors().disable()
//                .and()
                .formLogin().disable()   // (4)
                .httpBasic().disable()   // (5)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/post").permitAll()
                        .antMatchers("/get").permitAll()
//                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll() // OPTIONS 요청 허
                        .anyRequest().authenticated())
                .apply(new CustomFilterConfigurer());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {  // (2-1)
        @Override
        public void configure(HttpSecurity builder) throws Exception {  // (2-2)
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);  // (2-3)

        }
    }
}



