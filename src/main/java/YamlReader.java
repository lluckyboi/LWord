import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YamlReader {
    public int Idx;
    public List<Integer> FavList;
    // 读取yaml配置文件 获取单词索引
    public  YamlReader() throws FileNotFoundException {
        File dumpFile=new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath() + "/conf.yaml");
        Map mp = Yaml.loadType(dumpFile, HashMap.class);
//        System.out.println(mp.get("FavList").hashCode());
        Idx = (int) mp.get("IDX");
        FavList =(List<Integer>) mp.get("FavList");
    }

    public void setData(int cIDX,List<Integer> cFavList) {
        Idx = cIDX;
        FavList = cFavList;
    }

    public void WriteDataToYaml() {
        File dumpFile=new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath() + "/conf.yaml");
        Map mp = new HashMap();
        mp.put("IDX",Idx);
        mp.put("FavList",FavList);
        try {
            Yaml.dump(mp,dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
