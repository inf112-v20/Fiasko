package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.objects.Robot;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class BoardLoaderUtilTest {
    @Test
    public void loadTestBoard() {
        List<Robot> robotList = new ArrayList<>();
        try {
            BoardLoaderUtil.loadBoard("boards/test_board.txt", robotList);
        } catch (IOException e) {
            fail();
        }
    }
}
