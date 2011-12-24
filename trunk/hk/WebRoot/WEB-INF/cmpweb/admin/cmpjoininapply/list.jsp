<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">加盟申请</div>
		<div class="mod_content">
			<div class="divrow">
				<c:if test="${readed==0}"><c:set var="noreadedflg">nactive</c:set></c:if>
				<c:if test="${readed==1}"><c:set var="readedflg">nactive</c:set></c:if>
				<a class="${noreadedflg }" href="<%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&navoid=${navoid }&readed=<%=CmpJoinInApply.READED_N %>">未处理申请</a> / 
				<a class="${readedflg }" href="<%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&navoid=${navoid }&readed=<%=CmpJoinInApply.READED_Y %>">已处理申请</a>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:500px">
								<strong>${n.name }</strong> <fmt:formatDate value="${n.createTime}" pattern="yy-MM-dd HH:mm"/><br/>
								<c:if test="${not empty n.tel}">
									<strong>电话：</strong>${n.tel }<br/>
								</c:if>
								<c:if test="${not empty n.mobile}">
									<strong>手机：</strong>${n.mobile }<br/>
								</c:if>
								<strong>公司名称：</strong>${n.cmpname }<br/>
								${n.content }
							</div>
							<div class="f_l" style="width:70px;padding-left: 20px;text-align: center;">
								<c:if test="${n.applyReaded}">
									<a href="javascript:setnoread(${n.oid})">设为未处理</a>
								</c:if>
								<c:if test="${!n.applyReaded}">
									<a href="javascript:setread(${n.oid})">设为已处理</a>
								</c:if>
								<br/>
								<a href="javascript:delcmpapply(${n.oid })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&navoid=${navoid }&readed=${readed}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpapply(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpjoininapply_del.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function setread(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpjoininapply_setread.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setnoread(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpjoininapply_setnoread.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>