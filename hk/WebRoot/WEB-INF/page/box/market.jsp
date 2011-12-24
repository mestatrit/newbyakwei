<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="宝箱市场 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${cityId>0}"><c:set var="city_css">nn</c:set></c:if>
		<c:if test="${cityId==0}"><c:set var="all_css">nn</c:set></c:if>
		<hk:a clazz="${city_css}" href="/box/market.do?cityId=${sys_zone_pcityId}">${sys_zone_pcity.name}</hk:a> |
		<hk:a clazz="${all_css}" href="/box/market.do">全球</hk:a> | 
		<hk:a href="/index_changecity2.do" needreturnurl="true">切换城市</hk:a>
	</div>
	<div class="hang even">宝箱市场</div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="box" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
				<tr class="${clazz_var}">
					<td><hk:a href="/box/box.do?boxId=${box.boxId}" page="true">${box.name} ${box.totalCount-box.openCount}/${box.totalCount}</hk:a></td>
				</tr>
			</c:forEach>
		</tbody></table>
		<hk:simplepage2 clazz="page" href="/box/market.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}">
	<div class="hang odd">暂时没有宝箱可以开</div></c:if>
	<div class="hang"><hk:a href="/index_h.do">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>