<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/info/admin/admin.do">
			<div class="hang even"><hk:data key="info.admin.list.timelater"/></div>
			<div class="hang odd">
				<hk:select name="t" checkedvalue="${t}">
					<hk:option value="0" data="info.admin.list.week_later" res="true"/>
					<hk:option value="1" data="info.admin.list.month_later" res="true"/>
				</hk:select>
			</div>
			<hk:submit value="info.admin.list.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="i" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }"><td>
			<hk:a target="_blank" href="/info/info_view.do?infoId=${i.infoId}">${i.name }</hk:a> 
			<hk:a target="_blank" href="/home.do?userId=${i.userId}">${i.user.nickName}</hk:a> 
			<span class="s"><hk:a href="/info/admin/admin_smsnotice.do?infoId=${i.infoId}"><hk:data key="info.admin.list.smsnotice"/></hk:a></span>
			</td></tr>
		</c:forEach>
		</tbody></table>
		<hk:simplepage href="/info/info.do?userId=${userId}"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>