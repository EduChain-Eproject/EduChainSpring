package aptech.project.educhain.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import aptech.project.educhain.data.serviceImpl.accounts.OurUserDetailService;

@Configuration
@EnableWebSecurity
public class SercurityConfig {

    @Autowired
    private JWTAuthFilter jwtAuthFilter;
    @Autowired
    private OurUserDetailService ourUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request
                .requestMatchers("/Auth/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/home/**").permitAll()
                // fix spring security for other rout down here:
                .requestMatchers("/ADMIN/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/STUDENT/**").hasAnyAuthority("STUDENT")
                .requestMatchers("/COMMON/**").authenticated()
                .anyRequest().permitAll())
                // .formLogin(
                // form -> form
                // .loginProcessingUrl("/Auth/login")
                // .permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        // cut down the hashpassword for easier test check
        daoAuthenticationProvider.setPasswordEncoder(passWordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passWordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
