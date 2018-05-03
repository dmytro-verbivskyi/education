package boot.akkaspring.dao;

import boot.akkaspring.model.DataItem;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class DataProviderTest {

    Dao provider = new DataProvider();

    @Test
    public void verifyRandomWorksOnceForMaxSize() throws Exception {
        List<DataItem> items = provider.retrieveItems(19);
        assertThat(items.size()).isBetween(1, 19);
    }

    @Test
    public void verifyRandomWorksWithZero() throws Exception {
        assertThatCode(() -> provider.retrieveItems(0))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bound must be greater than origin");
    }

    @Test
    public void verifyValuesAreRandomForDataItems() throws Exception {
        List<DataItem> items = provider.retrieveItems(19);

        Set<Double> setOfRandomValues = items.stream()
                .map(DataItem::getValue)
                .collect(Collectors.toSet());

        assertThat(setOfRandomValues.size()).as("All are unique").isEqualTo(items.size());
    }
}