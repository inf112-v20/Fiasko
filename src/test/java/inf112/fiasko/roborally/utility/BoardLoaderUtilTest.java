package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.objects.Robot;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardLoaderUtilTest {
    @Test
    public void loadTestBoard() throws IOException {
        List<Robot> robotList = new ArrayList<>();
        BoardLoaderUtil.loadBoard("boards/test_board.txt", robotList);
    }
}
