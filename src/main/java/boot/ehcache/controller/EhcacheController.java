package boot.ehcache.controller;

import boot.ehcache.service.NumberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/number", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EhcacheController {

    private static final Logger LOG = LogManager.getLogger();

    private final NumberService numberService;

    @Autowired
    public EhcacheController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("/square/{number}")
    public String getSquare(@PathVariable Long number) {
        LOG.info("call numberService to square {}", number);
        return String.format("{\"square\": %s}", numberService.square(number));
    }
}
