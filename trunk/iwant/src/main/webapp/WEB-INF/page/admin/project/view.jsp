<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request"><hk:value value="${project.name}"/></c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">
		查看项目 - <hk:value value="${project.name}"/>
	</div>
	<div class="mod_content">
		<div>
			<table class="formt" cellpadding="0" cellspacing="0">
				<tr>
					<td width="90" align="right">名称</td>
					<td>
						<hk:value value="${project.name }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">电话</td>
					<td>
						<hk:value value="${project.tel }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">地址</td>
					<td>
						<hk:value value="${project.addr }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">描述</td>
					<td>
						<hk:value value="${project.descr}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right"></td>
					<td>
						<a href="javascript:viewppt()" class="split-r">查看客户简报</a>
						<a href="javascript:toupdate()" class="split-r">修改</a>
						<a href="${appctx_path }/mgr/project_back.do">返回</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
function toupdate(projectid){
	tourl('${appctx_path}/mgr/project_update.do?projectid=${project.projectid}&back_url='+encodeLocalURL());
}
function viewppt(projectid){
	tourl('${appctx_path}/mgr/ppt.do?projectid=${project.projectid}');
}
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>