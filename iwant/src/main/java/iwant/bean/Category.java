package iwant.bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

/**
 * 项目分类
 * 
 * @author akwei
 */
@Table(name = "category")
public class Category {

	/**
	 * id
	 */
	@Id
	private int catid;

	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 排序号
	 */
	@Column
	private int order_flag;

	public void setOrder_flag(int orderFlag) {
		order_flag = orderFlag;
	}

	public int getOrder_flag() {
		return order_flag;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
