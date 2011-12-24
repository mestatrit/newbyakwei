<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${cmpNav.name }
				<c:if test="${cmpNav.articleList}">
					<a class="more" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?navoid=${cmpNav.oid }&companyId=${companyId}">返回</a>
				</c:if>
			</div>
			<div class="mod_content">
				<div class="b">${cmpArticle.title }</div>
				<c:if test="${cmpProduct!=null}">
					已关联产品：
					<div class="divrow">
						<c:if test="${not empty cmpProduct.headPath}">
							<img src="${cmpProduct.head60}"/><br/>
						</c:if>
						${cmpProduct.name } <a href="javascript:delproductref()">取消关联</a>
					</div>
				</c:if>
				<form method="get" action="<%=path %>/epp/web/op/webadmin/cmparticle_setproductref.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="navoid" value="${navoid}"/>
					<hk:hide name="oid" value="${oid}"/>
					<hk:hide name="searchflg" value="1"/>
					产品名称：
					<hk:text name="name" clazz="text" value="${name}"/>
					<hk:submit clazz="btn" value="产品查询"/>
				</form>
				<div class="divrow">
				<c:forEach var="product" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width: 300px">
							<c:if test="${not empty product.headPath}">
							<img src="${product.head60 }"/><br/>
							</c:if>
							${product.name }
						</div>
						<div class="f_l" style="width: 200px">
							<a href="javascript:setproductref(${product.productId })">选择</a>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function setproductref(productId){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_setproductref.do?companyId=${companyId}&navoid=${navoid}&oid=${oid}&ch=1&productId="+productId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delproductref(){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_delproductref.do?companyId=${companyId}&navoid=${navoid}&oid=${oid}&ch=1",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>