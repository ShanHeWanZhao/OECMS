import org.junit.Test;

/**
 * @author tanruidong
 * @date 2020-05-10 20:44
 */
public class RegexTest {
    @Test
    public void test(){
        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
        String position = "3aaqq12a312";
        boolean matches = position.matches(reg);
        System.out.println(matches);
    }
}
