# Introduction #

创建一个实体对象
```


package demo.haloweb.dev3g.model;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

import java.util.Date;

@Table(name = "bbs", partitionClass = BbsDbPartitionHelper.class)
public class Bbs {

    @Id
    private int bbsid;

    @Column
    private String title;

    @Column
    private int bbstype;

    @Column
    private String content;

    @Column
    private Date createtime;

    public int getBbsid() {
        return bbsid;
    }

    public void setBbsid(int bbsid) {
        this.bbsid = bbsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBbstype() {
        return bbstype;
    }

    public void setBbstype(int bbstype) {
        this.bbstype = bbstype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}


```


创建bbsDao，进行数据操作

```

package demo.haloweb.dev3g.model;

import halo.dao.query.DeleteParam;
import halo.dao.query.HkObjQuery;
import halo.dao.query.QueryParam;
import halo.dao.query.UpdateParamBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * 数据访问采用分布式存储
 * 
 * @author akwei
 */
@Component("bbsDao")
public class BbsDao {

    @Autowired
    private HkObjQuery hkObjQuery;

    public Bbs insert(Bbs bbs) {
        bbs.setBbsid(hkObjQuery.insertObjForNumber("bbstype", bbs.getBbstype(),
                bbs).intValue());
        return bbs;
    }

    public void update(Bbs bbs) {
        hkObjQuery.updateObj("bbstype", bbs.getBbstype(), bbs);
    }

    public void updateTitleByBbstype(int bbstype, String title) {
        UpdateParamBuilder builder = new UpdateParamBuilder();
        builder.addKeyAndValue("bbstype", bbstype);
        builder.update(Bbs.class);
        builder.set("title", title);
        builder.where("bbstype", bbstype);
        hkObjQuery.update(builder.create());
    }

    public void deleteById(int bbstype, int bbsid) {
        hkObjQuery.deleteById("bbstype", bbstype, Bbs.class, bbsid);
    }

    public void deleteByBbstype(int bbstype) {
        DeleteParam deleteParam = new DeleteParam("bbstype", bbstype);
        deleteParam.setClazz(Bbs.class);
        deleteParam.set("bbstype=?", new Object[] { bbstype });
        hkObjQuery.delete(deleteParam);
    }

    public Bbs getById(int bbstype, int bbsid) {
        return hkObjQuery.getObjectById("bbstype", bbstype, Bbs.class, bbsid);
    }

    public List<Bbs> getListByBbsType(int bbstype) {
        QueryParam queryParam = new QueryParam();
        queryParam.addClass(Bbs.class);
        queryParam.addKeyAndValue("bbstype", bbstype);
        queryParam.set("bbstype=?", new Object[] { bbstype });
        queryParam.setRange(0, 100);
        return hkObjQuery.getList(queryParam, Bbs.class);
    }

    public Bbs getByBbstype(int bbstype) {
        QueryParam queryParam = new QueryParam();
        queryParam.addKeyAndValue("bbstype", bbstype);
        queryParam.set("bbstype=?", new Object[] { bbstype });
        return hkObjQuery.getObject(queryParam, Bbs.class);
    }

    public List<Bbs> getListJoin(int bbstype) {
        QueryParam queryParam = new QueryParam("bbstype", bbstype);
        queryParam.addClass(Bbs.class);
        queryParam.addClass(BbsContent.class);
        queryParam.set("bbs.bbsid=bbscontent.bbsid", null);
        queryParam.setRange(0, 10);
        queryParam.setOrder("bbs.bbsid desc");
        return hkObjQuery.getList(queryParam, new RowMapper<Bbs>() {

            @Override
            public Bbs mapRow(ResultSet arg0, int arg1) throws SQLException {
                Bbs bbs = hkObjQuery.getRowMapper(Bbs.class).mapRow(arg0, arg1);
                BbsContent bbsContent = hkObjQuery.getRowMapper(
                        BbsContent.class).mapRow(arg0, arg1);
                bbs.setBbsContent(bbsContent);
                return bbs;
            }
        });
    }
}

```

# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages