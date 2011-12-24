<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="会员动态 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">会员动态</div>
	<div>
		<hk:rmBlankLines rm="true">
			<div class="hang">
				<c:if test="${w=='all'}"><hk:a href="/feed/feed.do?w=all&from=${from}" clazz="nn">全部</hk:a></c:if>
				<c:if test="${w!='all'}"><hk:a href="/feed/feed.do?w=all&from=${from}">全部</hk:a></c:if>|
				<c:if test="${w=='city'}"><hk:a href="/feed/feed.do?w=city&from=${from}" clazz="nn">本市</hk:a></c:if>
				<c:if test="${w!='city'}"><hk:a href="/feed/feed.do?w=city&from=${from}">本市</hk:a></c:if>|
				<c:if test="${w=='range'}"><hk:a href="/feed/feed.do?w=range&from=${from}" clazz="nn">附近</hk:a></c:if>
				<c:if test="${w!='range'}"><hk:a href="/feed/feed.do?w=range&from=${from}">附近</hk:a></c:if>|
				<c:if test="${w=='ip'}"><hk:a href="/feed/feed.do?w=ip&from=${from}" clazz="nn">旁边</hk:a></c:if>
				<c:if test="${w!='ip'}"><hk:a href="/feed/feed.do?w=ip&from=${from}">旁边</hk:a></c:if>|
				<c:if test="${w==''}"><hk:a href="/feed/feed.do?from=${from}" clazz="nn">关注</hk:a></c:if>
				<c:if test="${w!=''}"><hk:a href="/feed/feed.do?from=${from}">关注</hk:a></c:if>
			</div>
		</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(volist)==0}">
		没有数据显示
	</c:if>
	<c:if test="${fn:length(volist)>0}">
		<div class="blk"></div>
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="f" items="${volist}" varStatus="idx">
				<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if>
				<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
				<tr class="${clazz_var}"><td>
					${f.content } <span class="s"><hk:time value="${f.first.createTime}"/></span>
				</td></tr>
			</c:forEach>
		</tbody></table>
	</c:if>
	<div class="hang"><hk:a href="/feed/feed_back.do?from=${from}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>