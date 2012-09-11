package iwant.bean;

import halo.util.DesUtil;
import halo.util.P;
import iwant.bean.exception.NoAdminUserException;

/**
 * 管理员，后台内容管理
 * 
 * @author akwei
 */
public class AdminUser {

    private int adminid;

    private String name;

    private String pwd;

    /**
     * 能登录的城市
     */
    private int cityid;

    /**
     * 级别:0普通管理员:1超级管理员
     */
    private int level;

    private static final String key = "adminuserkey";

    private static final DesUtil desUtil = new DesUtil(key);

    public AdminUser() {
    }

    /**
     * 通过加密字符来创建对象,不会对密码字段赋值
     * 
     * @param secretKey
     * @throws NoAdminUserException
     */
    public AdminUser(String secretKey) throws NoAdminUserException {
        String decode_key = desUtil.decrypt(secretKey);
        if (decode_key == null || decode_key.length() == 0) {
            throw new NoAdminUserException("no admin user");
        }
        // adminid:name:level:cityid
        String[] v = decode_key.split(":");
        if (v.length != 4) {
            throw new NoAdminUserException("no admin user");
        }
        try {
            this.adminid = Integer.valueOf(v[0]);
            this.name = v[1];
            this.level = Integer.valueOf(v[2]);
            this.cityid = Integer.valueOf(v[3]);
        }
        catch (Exception e) {
            throw new NoAdminUserException("no admin user");
        }
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getSecretKey() {
        return desUtil.encrypt(this.adminid + ":" + this.name + ":"
                + this.level + ":" + this.cityid);
    }

    /**
     * 验证用户登录
     * 
     * @param inputPwd
     * @return true:登录成功
     */
    public boolean isLogin(String inputPwd) {
        String enc_key = desUtil.encrypt(inputPwd);
        if (this.pwd == null || this.pwd.length() == 0) {
            return false;
        }
        if (this.pwd.equals(enc_key)) {
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSuperAdmin() {
        if (this.level == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // P.println("guilinadmin");
        // P.println(desUtil.encrypt("adminekgjegke"));
        // P.println("liuzhouadmin");
        // P.println(desUtil.encrypt("adminelgcdifj"));
        // P.println("wuzhouadmin");
        // P.println(desUtil.encrypt("adminefdkvefg"));
        // P.println("beihaiadmin");
        // P.println(desUtil.encrypt("adminejgcxeid"));
        // P.println("fangchenggangadmin");
        // P.println(desUtil.encrypt("admingpeitoew"));
        // P.println("qinzhouadmin");
        // P.println(desUtil.encrypt("admindjetiwow"));
        // P.println("guigangadmin");
        // P.println(desUtil.encrypt("admineurnchew"));
        // P.println("yulinadmin");
        // P.println(desUtil.encrypt("adminowpppppe"));
        // P.println("baiseadmin");
        // P.println(desUtil.encrypt("adminewqpeotm"));
        // P.println("hezhouadmin");
        // P.println(desUtil.encrypt("adminwiejdmeo"));
        // P.println("hechiadmin");
        // P.println(desUtil.encrypt("admineueyrtww"));
        // P.println("laibinadmin");
        // P.println(desUtil.encrypt("admindmdbcgew"));
        // P.println("chongzuoadmin");
        // P.println(desUtil.encrypt("admineueyfhwt"));
        // P.println("huizhou");
        // P.println(desUtil.encrypt("adminkeugjdsk"));
        // P.println("foshan");
        // P.println(desUtil.encrypt("adminekgurl"));
        // P.println("zhongshan");
        // P.println(desUtil.encrypt("adminzsiei89"));
        // P.println("zhuhai");
        // P.println(desUtil.encrypt("adminzhe897f"));
        P.println("hangzhou");// 杭州
        P.println(desUtil.encrypt("admindienue"));
        P.println("ningbo");// 宁波
        P.println(desUtil.encrypt("jdyeigh"));
        P.println("wenzhou");// 温州
        P.println(desUtil.encrypt("diurje"));
        P.println("jiaxing");// 嘉兴
        P.println(desUtil.encrypt("dkiebgwe"));
        P.println("huzhou");// 湖州
        P.println(desUtil.encrypt("foejgke"));
        P.println("shaoxing");// 绍兴
        P.println(desUtil.encrypt("dieyngkr"));
        P.println("jinhua");// 金华
        P.println(desUtil.encrypt("jdljenf"));
        P.println("hengzhou");// 蘅州
        P.println(desUtil.encrypt("weefgs"));
        P.println("zhoushan");// 舟山
        P.println(desUtil.encrypt("fgdfg"));
        P.println("taizhou2");// 台州
        P.println(desUtil.encrypt("egdsg"));
        P.println("lishui");// 丽水
        P.println(desUtil.encrypt("seerger"));
        P.println("wuxi");// 无锡
        P.println(desUtil.encrypt("engidg"));
        P.println("xuzhou");// 徐州
        P.println(desUtil.encrypt("digndk"));
        P.println("changzhou");// 常州
        P.println(desUtil.encrypt("sndkrf"));
        P.println("suzhou");// 苏州
        P.println(desUtil.encrypt("fgsdgs"));
        P.println("nantong");// 南通
        P.println(desUtil.encrypt("fbdffg"));
        P.println("lianyungang");// 连云港
        P.println(desUtil.encrypt("gddsf"));
        P.println("huaian");// 淮安
        P.println(desUtil.encrypt("ergcxv"));
        P.println("yancheng");// 盐城
        P.println(desUtil.encrypt("dfcvsd"));
        P.println("yangzhou");// 扬州
        P.println(desUtil.encrypt("rgfgas"));
        P.println("zhenjiang");// 镇江
        P.println(desUtil.encrypt("sgsdf"));
        P.println("taizhou");// 泰州
        P.println(desUtil.encrypt("bsdgw"));
        P.println("suqian");// 宿迁
        P.println(desUtil.encrypt("asdfrh"));
        P.println("kunshan");// 昆山
        P.println(desUtil.encrypt("ndser"));
    }
}