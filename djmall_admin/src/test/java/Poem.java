import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Poem {
    public static void main(String[] args) {
        Map<String, PoemEntity> map = new HashMap<>();
        map.put("春晓",new PoemEntity("孟浩然,春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。"));
        map.put("静夜思",new PoemEntity("李白,床前明月光，疑是地上霜。举头望明月，低头思故乡。"));
        map.put("咏鹅 ",new PoemEntity("骆宾王,鹅鹅鹅，曲项向天歌。白毛浮绿水，红掌拨清波。"));
        Scanner scanner=new Scanner(System.in);
        System.out.print("诗名：");
        String poem=scanner.next();
        System.out.println(map.get(poem));
    }
}
