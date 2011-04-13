package iwant.svr;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.dao.ProjectSearchCdn;
import iwant.util.ErrorCode;

import java.io.File;
import java.util.List;

public interface ProjectSvr {

	/**
	 * 创建project
	 * 
	 * @param project
	 * @param imgFile
	 *            必须是图片文件,文件大小不能超过3M，提前验证，如果超过，则不会对图片进行处理
	 * @return OpStatus 中error_code 返回值为 {@link ErrorCode#err_image},
	 *         {@link ErrorCode#success}
	 */
	OptStatus createProject(Project project, File imgFile);

	/**
	 * 更新project
	 * 
	 * @param project
	 * @param imgFile
	 *            必须是图片文件,文件大小不能超过3M，提前验证，如果超过，则不会对图片进行处理
	 * @return OpStatus 中error_code 返回值为 {@link ErrorCode#err_image},
	 *         {@link ErrorCode#success}
	 */
	OptStatus updateProject(Project project, File imgFile);

	/**
	 * 获得project
	 * 
	 * @param projectid
	 * @return
	 */
	Project getProject(long projectid);

	/**
	 * 删除project,并删除相关信息，由于project相关数据多，先删除project、ppt数据，并把id记入到projectRecycle。
	 * slide异步删除
	 * 
	 * @param projectid
	 * @return
	 */
	boolean deleteProject(long projectid);

	ProjectRecycle getProjectRecycle(long projectid);

	List<Project> getProjectListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size);
}