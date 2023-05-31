import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class YamlReader {
    public int Idx;
    // 读取yaml配置文件 获取单词索引
    public  YamlReader() throws FileNotFoundException {
        File dumpFile=new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath() + "/conf.yaml");
        Map mp = Yaml.loadType(dumpFile, HashMap.class);
//        System.out.println(mp.get("IDX").hashCode());
        Idx =mp.get("IDX").hashCode();
    }
}
