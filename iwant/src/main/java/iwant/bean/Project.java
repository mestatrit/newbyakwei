package iwant.bean;

import java.util.Date;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

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
}
