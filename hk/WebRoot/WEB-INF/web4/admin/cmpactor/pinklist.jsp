<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%>
<%@page import="com.hk.bean.Company"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">美发师推荐管理</div>
	<div class="mod_content">
		<ul class="rowlist">
			<c:forEach var="pink" items="${list}">
				<li>
					<div class="f_l" style="width: 150px;">
						<a href="/cmp/${pink.cmpActor.companyId }/actor/${pink.actorId}">${pink.cmpActor.name }</a>
					</div>
					<div class="f_l" style="width: 80px;">
						<a href="javascript:delcmpactorpink(${pink.oid })">取消推荐</a>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
		<div>
			<c:set var="page_url" scope="request"><%=path%>/h4/admin/actor_pinklist.do</c:set>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function delcmpactorpink(oid){
	if(window.confirm("确实要取消推荐？")){
		var url="<%=path%>/h4/admin/actor_delcmpactorpink.do?oid="+oid;
		doAjax(url,function(data){
			refreshurl();
		});
	}
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>