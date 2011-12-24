<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">报名管理</div>
	<div class="content">
		<c:if test="${!cmpOrg.openUserInfo}">
			<div class="divrow">
			<input type="button" value="请付费后, 查看更详细的报名数据" class="btn" onclick="tocharge()"/>
			</div>
		</c:if>
		<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmpdata.nodatalist"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<ul class="datalist">
				<c:forEach var="user" items="${list}">
					<li>
						<div>
							<span class="split-r">${user.name }</span>
							<span class="split-r"><hk:data key="epp.cmporgstudyaduser.sex${user.sex}"/></span>
							<span class="split-r">${user.city }</span>
							<c:if test="${cmpOrg.openUserInfo}">
							${user.cmpOrgStudyAd.title }
							</c:if>
						</div>
						<c:if test="${cmpOrg.openUserInfo}">
							<div>
								<c:if test="${not empty user.tel}"><span class="split-r">联系电话：${user.tel }</span></c:if>
								<c:if test="${not empty user.mobile}"><span class="split-r">手机：${user.mobile }</span></c:if>
								<c:if test="${not empty user.email}"><span class="split-r">E-mail：${user.email }</span></c:if>
								<c:if test="${not empty user.im}"><span class="split-r">QQ/MSN：${user.im }</span></c:if>
							</div>
							<c:if test="${not empty user.msg}"><div>${user.msg }</div></c:if>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="fr">
			<c:set var="page_url" scope="request"><%=path %>/epp/web/org/studyad_userlist.do?companyId=${companyId }&orgId=${orgId}</c:set>
			<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#e5e6e8');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
function tocharge(){
	tourl('<%=path %>/epp/web/org/pay.do?companyId=${companyId }&orgId=${orgId}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>