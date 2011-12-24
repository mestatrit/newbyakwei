<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${b.name }</c:set>
<c:set var="html_body_content" scope="request">
<div style="margin: auto; width: 800px;">
	<div class="f_l" style="width:300px;">
	<img src="${b.pic300 }"/>
	</div>
	<div class="f_r" style="width: 480px">
		<div style="font-size: 36px;margin: 20px 0;color: #0066CC;font-weight: bold;height:44px;">${b.name }</div>
		<h2 style="font-size: 28px;margin-bottom: 20px;color: #0066CC;height: 68px;">${b.intro }</h2>
		<div class="clr"></div>
		<div class="f_l" style="width:60px;">
			<a href="/user/${user.userId }/"><img width="60" height="60" src="${user.head80Pic }"/></a>
		</div>
		<div class="f_r" style="width:410px;font-size: 24px;line-height: 24pt;">
			<c:if test="${b.invite}">
				<a href="/user/${user.userId }/" class="b">${user.nickName }</a>通过邀请用户注册获得
			</c:if>
			<c:if test="${!b.invite}">
				<a href="/user/${user.userId }/" class="b">${user.nickName }</a>于 <fmt:formatDate value="${b.createTime}" pattern="yyyy-MM-dd HH:mm"/>在<a class="b" href="/venue/${b.companyId }/">${company.name }</a>获得
			</c:if>
		</div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
	<div style="padding-top: 20px;">
		<table class="badge_grid">
			<tr>
				<td>
					<c:forEach var="b" items="${userbadgelist}">
					<a href="/userbadge/${b.oid }"><img title="${b.name }:${b.intro}" src="${b.pic57 }" alt="${b.name }" /></a>
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
	<a class="more2" href="javascript:void(0)" onclick="goback()"><hk:data key="view2.return"/></a>
</div>
<script type="text/javascript">
function goback(){
	if(document.referrer){
		history.go(-1);
	}
	else{
		tourl("/user/${user.userId}/");
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>