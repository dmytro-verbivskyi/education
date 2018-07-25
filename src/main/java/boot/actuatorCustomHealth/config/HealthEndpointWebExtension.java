package boot.actuatorCustomHealth.config;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.stereotype.Component;

@Component
@EndpointWebExtension(endpoint = HealthEndpoint.class)
public class HealthEndpointWebExtension {

    @ReadOperation
    public WebEndpointResponse<Health> getHealth(SecurityContext securityContext) {
        Health health = Health.down().withDetail("myKEy", "some value").build(); //this.delegate.health();
        Integer status = 200;
        return new WebEndpointResponse<>(health, status);
    }
}
