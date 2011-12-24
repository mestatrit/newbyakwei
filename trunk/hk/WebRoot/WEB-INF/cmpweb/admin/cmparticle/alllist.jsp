<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">所有文章
			</div>
			<div class="mod_content">
				<div class="divrow b">
				在此推荐的文章将会在首页中间位置显示
				</div>
				<div class="divrow b">
					<form action="<%=path %>/epp/web/op/webadmin/cmparticle_alllist.do">
						<hk:hide name="companyId" value="${companyId}"/>
						标题：<hk:text name="title" clazz="text" value="${title}"/>
						<hk:submit clazz="btn" value="搜索"/>
					</form>
				</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="at" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:330px">
									<div style="padding:0 20px 0 0">
									<a href="<%=path %>/epp/web/op/webadmin/cmparticle_view.do?oid=${at.oid }&companyId=${companyId}">${at.title }</a>
									</div>
								</div>
								<div class="f_l" style="width:100px">
									<fmt:formatDate value="${at.createTime}" pattern="yy-MM-dd HH:mm"/>
								</div>
								<div class="f_l" style="width:150px;padding-left: 10px">
									<c:if test="${!at.pinkToHome}">
										<a href="javascript:sethomepink(${at.oid})">推荐到首页</a>
									</c:if> 
									<c:if test="${at.pinkToHome}">
										<a href="javascript:delhomepink(${at.oid})">取消推荐到首页</a>
									</c:if> 
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有添加任何文章</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmparticle_alllist.do?companyId=${companyId}&title=${enc_title}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function sethomepink(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_sethomepink.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delhomepink(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delhomepink.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>