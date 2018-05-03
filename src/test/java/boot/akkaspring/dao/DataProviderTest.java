package boot.akkaspring.dao;

import boot.akkaspring.model.DataItem;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class DataProviderTest {

    @Test
    public void verifyRandomWorksOnceForMaxSize() throws Exception {
        Dao provider = new DataProvider();

        List<DataItem> items = provider.retrieveItems(19);
        assertThat(items.size()).isBetween(1, 19);
    }

    @Test
    public void verifyRandomWorksWithZero() throws Exception {
        assertThatCode(() -> new DataProvider().retrieveItems(0))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bound must be greater than origin");
    }
}