package dev.urmat.taskmega;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // authorize requests
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // cors
                .cors(AbstractHttpConfigurer::disable)
                // csrf
                .csrf(AbstractHttpConfigurer::disable)
                // basic auth
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails developer = User.withUsername("urmat")
                .password(passwordEncoder.encode("urmat1234"))
                .roles("DEVELOPER")
                .build();

        return new InMemoryUserDetailsManager(developer);
    }
}