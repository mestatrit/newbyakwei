示例代码：
```

package demo.haloweb.dev3g.model;

import halo.dao.partition.DbPartitionHelper;
import halo.dao.query.PartitionTableInfo;

import java.util.Map;

public class BbsDbPartitionHelper extends DbPartitionHelper {

    private static final String DS_HALOWEB = "mysql_haloweb";

    private static final String TABLE_BBS = "bbs";

    @Override
    public PartitionTableInfo parse(String tableLogicName,
            Map<String, Object> ctxMap) {
        // 获取分区的条件
        int bbstype = (Integer) ctxMap.get("bbstype");
        PartitionTableInfo info = new PartitionTableInfo();
        // 设置表的别名
        info.setAliasName(TABLE_BBS);
        // 根据条件判断表所属的数据源与真实的表名
        if (bbstype % 2 == 0) {
            info.setDsKey(DS_HALOWEB + "0");
            info.setTableName(TABLE_BBS + "0");
        }
        else {
            info.setDsKey(DS_HALOWEB + "1");
            info.setTableName(TABLE_BBS + "1");
        }
        return info;
    }
}


```