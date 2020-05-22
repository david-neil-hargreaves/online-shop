
package sainsburys.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for the Online Shop application.
 */
@Configuration
@PropertySource("classpath:online-shop.properties")
public class EnvironmentConfiguration {

}
