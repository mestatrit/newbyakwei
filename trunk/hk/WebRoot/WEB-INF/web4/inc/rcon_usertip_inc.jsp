<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
	<%JspDataUtil.loadUserCmpTipDoneList(request,5);
	JspDataUtil.loadUserCmpTipToDoList(request,5);%>
<c:if test="${fn:length(cmptipdonevolist)>0}">
	<div class="mod">
		<div class="mod_title">
			<hk:data key="view2.people.tips"/>
		</div>
		<div class="mod_content">
			<c:forEach var="vo" items="${cmptipdonevolist}">
				<div class="divrow bdtm">
				<a href="/user/${vo.cmpTip.userId }/"><img src="${vo.cmpTip.user.head32Pic }"/>
				${vo.cmpTip.user.nickName }</a>： <a class="b" href="/venue/${vo.cmpTip.companyId }/">${vo.cmpTip.company.name }</a> 
				${vo.cmpTip.simpleContent } <a href="/item/${vo.cmpTip.tipId }"><span class="ruo"><fmt:formatDate value="${vo.cmpTip.createTime}" pattern="yy-MM-dd"/></span></a>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>
<c:if test="${fn:length(cmptiptodovolist)>0}">
	<div class="mod">
		<div class="mod_title">
			<hk:data key="view2.people.todos"/>
		</div>
		<div class="mod_content">
			<c:forEach var="vo" items="${cmptiptodovolist}">
				<div class="divrow bdtm">
				<a href="/user/${vo.cmpTip.userId }/"><img src="${vo.cmpTip.user.head32Pic }"/>
				${vo.cmpTip.user.nickName }</a>： <a class="b" href="/venue/${vo.cmpTip.companyId }/">${vo.cmpTip.company.name }</a> 
				${vo.cmpTip.simpleContent } <a href="/item/${vo.cmpTip.tipId }"><span class="ruo"><fmt:formatDate value="${vo.cmpTip.createTime}" pattern="yy-MM-dd"/></span></a>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>