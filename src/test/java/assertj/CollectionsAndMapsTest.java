package assertj;

import com.google.common.collect.ImmutableSortedMap;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class CollectionsAndMapsTest {

    @Test
    public void withIterableAsserts() {
        List<String> list = Lists.newArrayList("A", "B", "C");

        assertThat(list).isNotNull()
                .isNotEmpty().hasSize(3)
                .hasOnlyElementsOfType(String.class)
                .hasSameSizeAs(new ArrayList<>(list))
                .startsWith("A").endsWith("B", "C")
                .containsExactlyInAnyOrder("C", "A", "B")
                .containsOnlyOnce("A")
                .doesNotHaveDuplicates()
                .doesNotContainNull()
                .isSorted()
                .first().isEqualTo("A");
    }

    @Test
    public void withMapAsserts() {
        SortedMap<String, String> map = ImmutableSortedMap.of("A", "one", "B", "two", "C", "three");

        assertThat(map).isNotNull().hasSize(3)
                .containsKeys("A")
                .containsOnlyKeys("A", "B", "C")
                .doesNotContainKeys("D", "E")
                .containsValues("one", "two")
                .containsEntry("A", "one")
                .containsExactly(entry("A", "one"), entry("B", "two"), entry("C", "three"))
        ;
    }

}
