package boot.ehcache.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NumberService {

    private static final Logger LOG = LogManager.getLogger();

    @Cacheable(
            value = "squareCache",
            key = "#number",
            condition = "#number>10")
    public BigDecimal square(Long number) {
        BigDecimal square = BigDecimal.valueOf(number)
                .multiply(BigDecimal.valueOf(number));
        LOG.info("square of {} is {}", number, square);
        return square;
    }
}
