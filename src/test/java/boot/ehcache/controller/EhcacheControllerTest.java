package boot.ehcache.controller;

import boot.ehcache.service.CacheEventLogger;
import boot.ehcache.service.NumberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles("ehcache")
public class EhcacheControllerTest {

//    @Autowired
//    private MockMvc mvc;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private EhcacheController controller;

    @SpyBean
    private NumberService numberService;

    @SpyBean
    private CacheEventLogger cacheEventLogger;

    @Value("${spring.cache.jcache.config:#{null}}")
    private String ehcacheConfigDetails;

    @Test
    public void getSquareUsesCacheAndDoNotCallServiceEachTime() throws Exception {
        getSquareAndAssert(99L, 9801L);
        getSquareAndAssert(99L, 9801L);
        getSquareAndAssert(99L, 9801L);

        verify(numberService, times(1)).square(99L);
        verify(cacheEventLogger, times(1)).onEvent(any());
    }

    @Test
    public void getSquareUsesCacheWithConditionWhichValuesAreNotCacheable() throws Exception {
        getSquareAndAssert(9L, 81L);
        getSquareAndAssert(9L, 81L);
        getSquareAndAssert(9L, 81L);

        verify(numberService, times(3)).square(9L);
        verify(cacheEventLogger, never()).onEvent(any());
    }

    private void getSquareAndAssert(Long number, Long expected) throws Exception {
        assertThat(controller.getSquare(number)).isEqualTo("{\"square\": " + expected + "}");

        /*mvc.perform(MockMvcRequestBuilders.get("/number/square/" + number).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"square\": " + expected + "}")));*/
    }

}