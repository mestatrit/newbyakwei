package svr;

import iwant.bean.Category;
import iwant.svr.CategorySvr;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategorySvrTest extends BaseSvrTest {

	@Resource
	private CategorySvr categorySvr;

	private Category category1;

	private Category category2;

	@Before
	public void init() {
		this.categorySvr.clearAllData();
		// data 1
		category1 = new Category();
		category1.setName("akwei");
		this.categorySvr.createCategory(category1);
		// data 2
		category2 = new Category();
		category2.setName("akweiwei");
		this.categorySvr.createCategory(category2);
	}

	private void assertData(Category expected, Category actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getCatid(), actual.getCatid());
		Assert.assertEquals(expected.getOrder_flag(), actual.getOrder_flag());
	}

	@Test
	public void createCategory() {
		Category category = new Category();
		category.setName("akwei");
		boolean result = this.categorySvr.createCategory(category);
		Assert.assertEquals(result, false);
		category.setName("akwei00");
		result = this.categorySvr.createCategory(category);
		Assert.assertEquals(result, true);
	}

	@Test
	public void updateCategory() {
		category1.setName("akweiwei");
		category1.setOrder_flag(99);
		boolean result = this.categorySvr.updateCategory(category1);
		Assert.assertEquals(false, result);
		category1.setName("jyy");
		result = this.categorySvr.updateCategory(category1);
		Assert.assertEquals(true, result);
		Category db_cateCategory1 = this.categorySvr.getCategory(category1
				.getCatid());
		this.assertData(category1, db_cateCategory1);
	}

	@Test
	public void deleteCategory() {
		this.categorySvr.deleteCategory(this.category1.getCatid());
		Category dbCategory1 = this.categorySvr.getCategory(this.category1
				.getCatid());
		Assert.assertNull(dbCategory1);
	}

	@Test
	public void getCategoryListForAll() {
		List<Category> list = this.categorySvr.getCategoryListForAll();
		Assert.assertEquals(2, list.size());
		this.assertData(this.category2, list.get(0));
		this.assertData(this.category1, list.get(1));
	}
}