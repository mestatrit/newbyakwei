<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<c:set var="html_head_title" scope="request">项目管理</c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">项目管理</div>
	<div class="mod_content">
		<div>
		<input type="button" class="btn" value="创建项目" onclick="tocreate()"/>
		</div>
		<div style="padding-left: 50">
		<form method="get" action="${appctx_path}/mgr/project.do">
			<input type="hidden" name="catid" value="${projectSearchCdn.catid}"/>
			<input type="text" class="text" name="name" value="<hk:value value="${projectSearchCdn.name}" onerow="true"/>"/>
			<input type="submit" value="模糊搜索" class="btn"/>
		</form>
		</div>
		<ul class="rowlist">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">名称</div>
				<div class="clr"></div>
			</li>
			<c:forEach var="project" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">
					<a href="javascript:view(${project.projectid})"><hk:value value="${project.name }" onerow="true"/></a>
				</div>
				<div class="f_l">
					<a href="javascript:viewppt(${project.projectid})" class="split-r">查看PPT</a>
					<a href="javascript:toupdate(${project.projectid })" class="split-r" id="op_update_${project.projectid }">修改</a>
					<a href="javascript:opdel(${project.projectid })" class="split-r" id="op_delete_${project.projectid }">删除</a>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0 && page==1) || (fn:length(list)==0 && page==null) }">
				<li><div class="nodata">本页没有数据</div></li>
			</c:if>
		</ul>
		<div>
			<c:set var="page_url" scope="request">${appctx_path }/mgr/project.do?catid=${catid }&amp;name=${projectSearchCdn.encName }</c:set>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function tocreate(){
	tourl('${appctx_path}/mgr/project_create.do');
}
function toupdate(projectid){
	tourl('${appctx_path}/mgr/project_update.do?projectid='+projectid+"&back_url="+encodeLocalURL());
}
function view(projectid){
	tourl('${appctx_path}/mgr/project_view.do?projectid='+projectid+"&back_url="+encodeLocalURL());
}
function viewppt(projectid){
	tourl('${appctx_path}/mgr/ppt.do?projectid='+projectid);
}
function opdel(projectid){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete_'+projectid,false);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/project_delete.do?projectid="+projectid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			},
			error:function(data){
				removeGlass(glassid_op);
				alert('服务器出错，请刷新页面稍后继续操作');
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>