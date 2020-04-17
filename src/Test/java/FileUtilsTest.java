import com.trd.oecms.utils.FileUtils;
import org.junit.Test;

/**
 * @author tanruidong
 * @date 2020-04-16 09:36
 */
public class FileUtilsTest {

    @Test
    public void test1(){
        System.out.println(FileUtils.getFileSuffix("dasdsa.dsada"));
        System.out.println(FileUtils.getFileSuffix("doc.txt.mp4"));
        System.out.println(FileUtils.getFileSuffix("dasdsa"));
        System.out.println(FileUtils.makeNonRepeatName());
}
}
