import com.melnikyura.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on May 17, 2020
 */
@Slf4j
public class Test {
    private Stage stage;

    @Before
    public void generateRandomTree() {
        final int treeSize = 16000;
        final int updates = 0; //<--- some bug with calculating init size of the arrays. Keep it 0 for now

        int[] events = new int[treeSize+updates];

        final Random rnd = new Random();

        for (int i = 0; i < treeSize; i++) {
            events[i] = rnd.nextInt();
        }

        for (int i = treeSize; i < treeSize+updates; i++) {
            int ind = rnd.nextInt(treeSize);
            events[i] = ind;
        }

        stage = new Stage();
        log.info("Test start");
        log.info("Init tree: BEGIN");
        stage.initTree(events, treeSize);
        log.info("Init tree: END");
    }


    @org.junit.Test
    public void initTest() { }


    @org.junit.Test
    public void diff() {
        final Random rnd = new Random();

        List<String> versions = stage.getVersions();
//        log.info("Available versions: [{}]", versions);

        String comparedVersion = versions.get(rnd.nextInt(versions.size()));
        log.info("Compared version: [{}]", comparedVersion);
        String[] rv = comparedVersion.split("\\.");

        log.info("Compare: BEGIN");
        List<Integer> diff = stage.diff(Integer.parseInt(rv[0]), Integer.parseInt(rv[1]));
        log.info("Compare: END");

        log.info("diff: [{}]", diff);
    }
}
