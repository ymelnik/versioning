import com.melnikyura.Stage;
import com.melnikyura.model.TreeLevel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@Slf4j
public class NewTest {
    private final Stage stage = new Stage();
    private TreeLevel[] treeA;
    private TreeLevel[] treeB;

    @Before
    public void before() {
        treeA = stage.initTree1(new byte[]{1, 2, 1}, 2);
//        treeB = stage.initTree1(new byte[]{4, 2, 3, 2, 2, 2, 1}, 4);
        treeB = stage.initTree1(new byte[]{2,1,2,2,1,2,0}, 3);
    }


    public void deserializeTree() {
        final Random rnd = new Random();
    }


    @Test
    public void diffTest() {
        log.info("treeA:", treeA);
        for (int i = 0; i < treeA.length; i++) {
            log.info(" {}: {}", i, treeA[i]);
        }
        log.info("treeB:", treeB);
        for (int i = 0; i < treeB.length; i++) {
            log.info(" {}: {}", i, treeB[i]);
        }

        List<Integer> diff = stage.diff1(treeA, treeB);
        log.info("diff: [{}]", diff);
    }


//    private byte[] generate(int amount) {
//        byte[] arr = new byte[amount];
//
//    }
}
