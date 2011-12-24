<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.login"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter">
	<div class="mod">
		<div class="content">
			<div class="divrow" style="width: 80px;text-align: center;">
				<div>
					<c:if test="${loginUser.userId==userId}">
					<a href="<%=path %>/epp/web/op/user/set_sethead.do?companyId=${companyId}"><img alt="${user.nickName }" title="${user.nickName }" src="${user.head80Pic }"/></a>
					</c:if>
					<c:if test="${loginUser.userId!=userId}">
					<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${userId}"><img alt="${user.nickName }" title="${user.nickName }" src="${user.head80Pic }"/></a>
					</c:if>
				</div>
				<a class="b" href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${userId}">${user.nickName }</a>
			</div>
			<div class="divrow">
				<c:if test="${loginUser.userId==userId}">
					<c:if test="${cmpOrg==null && cmpOrgApply==null}">
						<input type="button" value="<hk:data key="epp.apply.cmporg"/>" onclick="tourl('<%=path %>/epp/web/user_adminorg.do?companyId=${companyId}')"/>
					</c:if>
					<c:if test="${cmpOrg==null && cmpOrgApply!=null}">
						<hk:data key="epp.apply.checking"/>
					</c:if>
				</c:if>
				<c:if test="${cmpOrg!=null}">
					<a target="_blank" href="/edu/${companyId }/${cmpOrg.orgId}">${cmpOrg.name }</a>
				</c:if>
			</div>
		</div>
	</div>
	<c:if test="${fn:length(cmporgstudyaduserlist)>0}">
		<div class="mod">
			<div class="mod_tit"><hk:data key="epp.user.cmporgstudyad" arg0="${user.nickName}"/></div>
			<div class="content">
			<ul class="datalist">
				<c:forEach var="ad" items="${cmporgstudyaduserlist}">
				<li>
					<a class="split-r" href="/edu/${companyId }/${ad.orgId}/zhaosheng/0/${ad.adid}.html">${ad.cmpOrgStudyAd.title }</a>
					<span><fmt:formatDate value="${ad.createTime}" pattern="yyyy-MM-dd"/></span>
					</li>
				</c:forEach>
			</ul>
			</div>
		</div>
	</c:if>
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
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>