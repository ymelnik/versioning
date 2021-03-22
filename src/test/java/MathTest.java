import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@Slf4j
public class MathTest {
    @Test
    public void test() {
        log.info("{}", 100/(double)16);
        log.info("{}", Math.ceil(100/(double)16));
    }
}
