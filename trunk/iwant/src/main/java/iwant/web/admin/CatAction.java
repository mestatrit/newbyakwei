package iwant.web.admin;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.Category;
import iwant.bean.validate.CategoryValidate;
import iwant.svr.CategorySvr;
import iwant.web.BaseAction;
import iwant.web.admin.util.Err;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分类管理
 * 
 * @author akwei
 */
@Component("/mgr/cat")
public class CatAction extends BaseAction {

	@Autowired
	private CategorySvr categorySvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_cat", true);
		req.setAttribute("list", this.categorySvr.getCategoryListForAll());
		return this.getAdminPath("cat/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			req.setAttribute("op_cat", true);
			return this.getAdminPath("cat/create.jsp");
		}
		Category category = new Category();
		category.setName(req.getStringRow("name"));
		List<String> errlist = CategoryValidate.validate(category);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "createerr");
		}
		boolean res = this.categorySvr.createCategory(category);
		if (!res) {
			errlist.add(Err.CATEGORY_DUPLICATE);
			return this.onErrorList(req, errlist, "createerr");
		}
		return this.onSuccess(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		Category category = this.categorySvr.getCategory(req
				.getIntAndSetAttr("catid"));
		if (category == null) {
			return null;
		}
		req.setAttribute("cat", category);
		if (this.isForwardPage(req)) {
			req.setAttribute("op_cat", true);
			return this.getAdminPath("cat/update.jsp");
		}
		category.setName(req.getStringRow("name"));
		List<String> errlist = CategoryValidate.validate(category);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "createerr");
		}
		boolean res = this.categorySvr.updateCategory(category);
		if (!res) {
			errlist.add(Err.CATEGORY_DUPLICATE);
			return this.onErrorList(req, errlist, "updateerr");
		}
		return this.onSuccess(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		this.categorySvr.deleteCategory(req.getInt("catid"));
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toup(HkRequest req, HkResponse resp) throws Exception {
		Category category2 = this.categorySvr.getCategory(req.getInt("toid"));
		if (category2 == null) {
			return null;
		}
		Category category = this.categorySvr.getCategory(req.getInt("catid"));
		int tmp_order = category.getOrder_flag();
		category.setOrder_flag(category2.getOrder_flag());
		category2.setOrder_flag(tmp_order);
		this.categorySvr.updateCategory(category);
		this.categorySvr.updateCategory(category2);
		return null;
	}
}