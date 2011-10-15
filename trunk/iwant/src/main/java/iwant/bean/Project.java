package iwant.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import iwant.util.PicUtil;

import java.util.Date;

/**
 * 项目信息
 * 
 * @author akwei
 */
@Table(name = "project")
public class Project {

	/**
	 * 项目id
	 */
	@Id
	private long projectid;

	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 描述
	 */
	@Column
	private String descr;

	/**
	 * 分类id
	 */
	@Column
	private int catid;

	/**
	 * 地址
	 */
	@Column
	private String addr;

	/**
	 * 排序号，从大到小
	 */
	@Column
	private long order_flag;

	/**
	 * 是否激活
	 */
	@Column
	private int active_flag;

	/**
	 * 联系电话
	 */
	@Column
	private String tel;

	/**
	 * 地图坐标
	 */
	@Column
	private double lat;

	/**
	 * 地图坐标
	 */
	@Column
	private double lng;

	@Column
	private Date createtime;

	@Column
	private int fans_num;

	@Column
	private int cityid;

	@Column
	private int did;

	/**
	 * 图片路径
	 */
	@Column
	private String path;

	/**
	 * 容积率
	 */
	@Column
	private String rongjilv;

	/**
	 * 绿化率
	 */
	@Column
	private String lvhualv;

	/**
	 * 物业费
	 */
	@Column
	private String mrate;

	/**
	 * 车位
	 */
	@Column
	private String carspace;

	/**
	 * 建筑年代
	 */
	@Column
	private String buildtime;

	/**
	 * 建筑类别
	 */
	@Column
	private String buildtype;

	/**
	 * 物业类型
	 */
	@Column
	private String mtype;

	/**
	 * 交通
	 */
	@Column
	private String traffic;

	/**
	 * 周边
	 */
	@Column
	private String neardescr;

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getNeardescr() {
		return neardescr;
	}

	public void setNeardescr(String neardescr) {
		this.neardescr = neardescr;
	}

	public String getRongjilv() {
		return rongjilv;
	}

	public void setRongjilv(String rongjilv) {
		this.rongjilv = rongjilv;
	}

	public String getLvhualv() {
		return lvhualv;
	}

	public void setLvhualv(String lvhualv) {
		this.lvhualv = lvhualv;
	}

	public String getMrate() {
		return mrate;
	}

	public void setMrate(String mrate) {
		this.mrate = mrate;
	}

	public String getCarspace() {
		return carspace;
	}

	public void setCarspace(String carspace) {
		this.carspace = carspace;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public String getBuildtype() {
		return buildtype;
	}

	public void setBuildtype(String buildtype) {
		this.buildtype = buildtype;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getCityid() {
		return cityid;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddr() {
		return addr;
	}

	public long getOrder_flag() {
		return order_flag;
	}

	public void setOrder_flag(long orderFlag) {
		order_flag = orderFlag;
	}

	public int getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(int activeFlag) {
		active_flag = activeFlag;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getFans_num() {
		return fans_num;
	}

	public void setFans_num(int fansNum) {
		fans_num = fansNum;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getPic1Url() {
		return PicUtil.getSlidePic1Url(this.path);
	}

	public String getPic2Url() {
		return PicUtil.getSlidePic2Url(this.path);
	}
}
