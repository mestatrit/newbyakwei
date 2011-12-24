<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">宝箱管理</div>
			<div class="mod_content">
				<div class="divrow">
					<input type="button" class="btn" value="发布宝箱" onclick="tourl('<%=path %>/epp/web/op/webadmin/box_create.do?companyId=${companyId }&navoid=${navoid }')"/>
				</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="n" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:200px">
									<a href="<%=path %>/epp/web/op/webadmin/box_view.do?companyId=${companyId}&boxId=${n.boxId}&navoid=${navoid }">${n.name }</a>
								</div>
								<div class="f_l" style="width: 200px;padding-left: 20px">
								剩余 ${n.totalCount-n.openCount } / 总共${n.totalCount }
								</div>
								<div class="f_l" style="width:150px;padding-left: 20px;">
									<c:if test="${!n.pinkForCmp}">
										<a href="javascript:setcmppink(${n.boxId})">推荐</a>
									</c:if> 
									<c:if test="${n.pinkForCmp}">
										<a href="javascript:delcmppink(${n.boxId})">取消推荐</a>
									</c:if>
									/
									<a href="<%=path %>/epp/web/op/webadmin/box_uplist.do?companyId=${companyId}&boxId=${n.boxId}&navoid=${navoid }">兑换管理</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何数据</div>
				</c:if>
			</div>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/box.do?companyId=${companyId}&navoid=${navoid }</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function setcmppink(boxId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/box_setcmppink.do?companyId=${companyId}&boxId="+boxId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delcmppink(boxId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/box_delcmppink.do?companyId=${companyId}&boxId="+boxId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>