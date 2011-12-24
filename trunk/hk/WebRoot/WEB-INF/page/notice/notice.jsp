<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="消息通知 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:forEach var="sortVo" items="${list}">
		<div class="hang">${sortVo.noticeTypeIntro}<c:if test="${sortVo.showSwitch}">|<hk:a href="/user/set/set_psn.do?noticeType=${sortVo.noticeType}"><c:if test="${sortVo.closeSysNotice}">关闭提醒</c:if><c:if test="${!sortVo.closeSysNotice}">打开提醒</c:if></hk:a></c:if></div>
		<c:if test="${fn:length(sortVo.list)>0}">
			<table class="list" cellpadding="0" cellspacing="0"><tbody>
				<c:forEach var="n" items="${sortVo.list}" varStatus="idx">
					<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
					<tr class="${clazz_var }"><td>
						${n.content} 
						<c:if test="${sortVo.labaReplyNoticeType}">
						<hk:a clazz="s" href="/laba/laba.do?labaId=${n.notice.labaId}&sip=1">评论</hk:a>
						</c:if>
					</td></tr>
				</c:forEach>
			</tbody></table>
			<div class="hang"><hk:a href="/notice/notice_more.do?noticeType=${sortVo.noticeType}">更多</hk:a></div>
			<div class="blk"></div>
		</c:if>
	</c:forEach>
	<c:if test="${fn:length(feedvolist)>0}">
		<div class="hang">关注者动态</div>
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="f" items="${feedvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
				<tr class="${clazz_var }"><td>${f.content }</td></tr>
			</c:forEach>
		</tbody></table>
		<div class="hang"><hk:a href="/feed/feed.do?from=notice">更多</hk:a></div>
		<div class="blk"></div>
	</c:if>
	<div class="hang"><hk:a href="/user/set/set_toSetNoticeInfo.do">设置消息提醒</hk:a>|<hk:a href="/home.do?userId=${loginUser.userId}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>