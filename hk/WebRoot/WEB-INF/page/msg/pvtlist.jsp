<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="私信对话录 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:a href="/msg/pvtlist.do">所有对话录</hk:a>|<hk:a href="/msg/send_tosend.do">发送私信</hk:a></div>
		<c:if test="${fn:length(list)>0}">
			<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${p.newMsg}"><c:set var="clazz_var" value="reply" /></c:if>
				<tr class="${clazz_var }"><td>
					与<hk:a clazz="n" href="/home.do?userId=${p.user2.userId}">${p.user2.nickName}</hk:a>的对话录 <span><fmt:formatDate value="${p.createTime}" pattern="yy-MM-dd HH:mm"/></span><br/> 
					 ${p.msg } 
					 <c:if test="${p.noReadCount>0}"> <span class="big">${p.noReadCount}</span>个未读</c:if> 
					 <hk:a clazz="s" href="/msg/pvt.do?mainId=${p.mainId}">展开</hk:a> 
				</td></tr>
			</c:forEach>
			</tbody></table>
			<hk:simplepage clazz="page" href="/msg/pvtlist.do"/>
		</c:if>
		<c:if test="${fn:length(list)==0}">
			没有数据显示
		</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>