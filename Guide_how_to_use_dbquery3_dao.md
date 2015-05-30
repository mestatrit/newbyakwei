创建一个 实体类


```

package demo.haloweb.dev3g.model;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

import java.util.Date;

@Table(name = "user")
public class User {

    @Id
    private int userid;

    @Column
    private String name;

    @Column
    private int sex;

    @Column("createtime")
    private Date createTime;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}


```

第二个实体类

```

package demo.haloweb.dev3g.model;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

@Table(name = "userinfo")
public class UserInfo {

    @Id
    private int userid;

    @Column
    private String content;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

```

创建一个UserDao，进行数据操作

```

package demo.haloweb.dev3g.model;

import halo.dao.query.SimpleQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component("userDao")
public class UserDao {

    @Autowired
    private SimpleQuery query;

    public User insert(User user) {
        user.setUserid(query.insertForNumber(user).intValue());
        return user;
    }

    public void update(User user) {
        query.update(user);
    }

    public void updateName(int userid, String name) {
        query.updateBySQL(User.class, "name=?", "userid=?", new Object[] {
                name, userid });
    }

    public void deleteById(int userid) {
        query.deleteById(User.class, userid);
    }

    public void deleteBySex(int sex) {
        query.delete(User.class, "sex=?", new Object[] { sex });
    }

    public User getById(int userid) {
        return query.getById(User.class, userid);
    }

    public List<User> getListBySex(int sex, int begin, int size) {
        return query.getList(User.class, "sex=?", new Object[] { sex },
                "name desc", begin, size);
    }

    /**
     * join 操作，不建议使用join方式读取多张表
     * 
     * @param begin
     * @param size
     * @return
     */
    public List<User> getListForJoin(int begin, int size) {
        return query.getList(new Class[] { User.class, UserInfo.class },
                "user.userid=userinfo.userid", null, "userid asc",
                new RowMapper<User>() {

                    @Override
                    public User mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        User user = query.getHkObjQuery()
                                .getRowMapper(User.class).mapRow(rs, arg1);
                        UserInfo userInfo = query.getHkObjQuery()
                                .getRowMapper(UserInfo.class).mapRow(rs, arg1);
                        user.setUserInfo(userInfo);
                        return user;
                    }
                }, begin, size);
    }
}

```