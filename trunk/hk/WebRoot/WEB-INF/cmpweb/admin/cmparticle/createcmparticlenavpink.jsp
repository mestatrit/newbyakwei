<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpNav"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">推荐到栏目
			<c:if test="${cmpNav.articleList}">
				<a class="more" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</c:if>
		</div>
		<div class="mod_content">
			<form id="frm"  method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/cmparticle_createcmparticlenavpink.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="oid" value="${oid}"/>
				<hk:hide name="ch" value="1"/>
				${cmpArticle.title }<br/>
				显示文章前 <hk:text name="pflg" value="0" clazz="text" style="width:50px;"/> 段
				<hk:submit value="提交" clazz="btn split-r"/>
				<a href="${denc_return_url }">返回</a>
				<p>当数字为0时，显示文章所有内容。当数字大于0时，显示所填数字的文章段数</p>
				<p>对于有头图的文章进行推荐时，默认显示文章的4行内容，忽略此处的数字</p>
			</form>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createok(error,msg,v){
	tourl("${denc_return_url }");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>