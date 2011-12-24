<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.Information"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="info.list.title" arg0="${user.nickName}"/></c:set>
<c:set var="var_stop" value="<%=Information.USESTATUS_STOP %>"/>
<c:set var="var_run" value="<%=Information.USESTATUS_RUN %>"/>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:set var="userurl">/home.do?userId=${user.userId }</c:set>
	<div class="hang"><hk:data key="info.list.title2" arg0="${user.nickName}" arg1="${userurl}"/></div>
	<c:if test="${fn:length(infovolist)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="vo" items="${infovolist}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }"><td>
			<hk:a href="/info/info_view.do?infoId=${vo.information.infoId}">${vo.information.name }</hk:a> 
			<span class="ruo s">${vo.useStatusData } 
			<c:if test="${loginUser.userId==user.userId}">
				<c:if test="${vo.information.run}"><hk:a href="/info/op/info_chgstatus.do?infoId=${vo.information.infoId}&stat=${var_stop }"><hk:data key="info.list.act_stop"/></hk:a></c:if>
				<c:if test="${!vo.information.run}"><hk:a href="/info/op/info_chgstatus.do?infoId=${vo.information.infoId}&stat=${var_run }"><hk:data key="info.list.act_run"/></hk:a></c:if>
				<hk:a href="/info/op/info_toedit.do?infoId=${vo.information.infoId}"><hk:data key="info.list.update"/></hk:a>
			</c:if>
			</span>
			</td></tr>
		</c:forEach>
		</tbody></table>
		<hk:simplepage href="/info/info.do?userId=${userId}"/>
	</c:if>
	<c:if test="${fn:length(infovolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>