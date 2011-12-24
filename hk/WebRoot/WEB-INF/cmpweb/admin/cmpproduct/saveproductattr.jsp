<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">设置产品属性</div>
		<div class="mod_content">
		<br/>
			<c:if test="${cmpProductSortAttrObject==null}">
				产品所在分类${cmpProductSort.name }没有设置属性，请先设置分类<a class="b" href="<%=path %>/epp/web/op/webadmin/cmpproduct_saveattrmodule.do?companyId=${companyId }&navoid=${navoid }&sortId=${cmpProductSort.sortId }&parentId=${cmpProductSort.parentId}">${cmpProductSort.name }</a>的属性
			</c:if>
			<c:if test="${cmpProductSortAttrObject!=null}">
				<div>
					<c:set var="form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmpproduct_saveproductattr.do</c:set>
					<jsp:include page="productattrform.jsp"></jsp:include>
				</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function saveok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>