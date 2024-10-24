package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application,
 * allowing requests from the frontend running at 'http://localhost:4200'.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Adds CORS mappings to allow requests from
     * specific origins with certain methods.
     *
     * @param registry the CorsRegistry to configure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
