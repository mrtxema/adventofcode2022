import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SnafuNumberTest {

    @ParameterizedTest
    @ValueSource(longs = {30813621291635L, 6162724258327L, 1232544851665L, 246508970333L, 49301794066L, 9860358813L, 1972071762L, 8L})
    public void testFromLong(long decimalValue) {
        SnafuNumber result = SnafuNumber.fromLong(decimalValue);
        assertThat(result.longValue()).isEqualTo(decimalValue);
    }
}
