<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="laba_tore_add" value="from=${from}&ouserId=${ouserId }&repage=${repage }&w=${w }&tagId=${tagId }&relabaId=${relabaId }&hfrom=reply&sw=${enc_sw }&infoId=${infoId }"/>
<div class="hang">
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<tr>
	<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${labaVo.laba.user.head32Pic }"/></td></c:if>
	<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${labaVo.laba.user.head48Pic }"/></td></c:if>
	<td>
		<hk:a href="/home.do?userId=${labaVo.laba.userId}">${labaVo.laba.user.nickName}</hk:a> 
		<span class="ruo s"><fmt:formatDate value="${labaVo.laba.createTime}" pattern="yyyy-MM-dd HH:ss"/></span> 
		<c:if test="${loginUser.userId!=labaVo.laba.userId}"><hk:a href="/laba/laba.do?labaId=${labaId}&sip=1&cp=1" clazz="line">复制</hk:a></c:if>
		<c:if test="${labaVo.fav}">
		<hk:a href="/laba/op/op_delfav.do?labaId=${labaVo.laba.labaId }&${laba_tore_add }" clazz="line">取消收藏</hk:a>
		</c:if>
		<c:if test="${!labaVo.fav}">
		<hk:a href="/laba/op/op_fav.do?labaId=${labaVo.laba.labaId }&${laba_tore_add }" clazz="line">收藏</hk:a>
		</c:if>
		<br/>
		<c:set var="innerhtml">
			<c:if test="${pink}"><span class="s">(<hk:data key="view.pink"/>)</span> </c:if> 
			<hk:a clazz="s" href="/help_fromwap.do?labaId=${labaVo.laba.labaId}&${laba_tore_add }">${labaVo.sendFromData}</hk:a>
			<c:if test="${labaVo.laba.replyCount>0}"><span class="s">${labaVo.laba.replyCount}个评论</span></c:if>
			<c:if test="${labaVo.laba.refcount>0}"><span class="s">${labaVo.laba.refcount}个转发</span></c:if>
		</c:set>
		<c:if test="${empty labaVo.longContent}">
			<c:if test="${empty labaVo.mainContent}">${labaVo.content } ${innerhtml }</c:if>
			<c:if test="${not empty labaVo.mainContent}"><div>${labaVo.mainContent }</div><div class="refcon">${labaVo.refContent } ${innerhtml }</div></c:if>
		</c:if> 
		<c:if test="${not empty labaVo.longContent}">
			<c:if test="${empty labaVo.mainLongContent}">${labaVo.longContent } ${innerhtml }</c:if>
			<c:if test="${not empty labaVo.mainLongContent}"><div>${labaVo.mainLongContent }</div><div class="refcon">${labaVo.refLongContent } ${innerhtml }</div></c:if>
		</c:if> 
		
		<c:if test="${fn:length(taglist2)>0}">
			<br/>
			<span class="s">
			<hk:rmstr value="|">
				<c:forEach var="t" items="${taglist2}"><hk:a href="/laba/taglabalist.do?tagId=${t.tagId}" clazz="line">${t.name}</hk:a><c:if test="${labaVo.laba.userId==loginUser.userId || t.userId==loginUser.userId}"> <hk:a href="/laba/op/op_deltag.do?otagId=${t.tagId}&${queryString}">x</hk:a></c:if>|</c:forEach>
			</hk:rmstr>
			</span>
		</c:if>
	<div class="hang">
		<hk:form method="post" action="/laba/op/op_fav.do">
			<hk:hide name="from" value="${from}"/>
			<hk:hide name="w" value="${w}"/>
			<hk:hide name="sw" value="${sw}"/>
			<hk:hide name="tagId" value="${tagId}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="relabaId" value="${relabaId}"/>
			<hk:hide name="ouserId" value="${ouserId}"/>
			<hk:hide name="infoId" value="${infoId}"/>
			<hk:hide name="lastUrl" value="/laba/laba.do?labaId=${labaVo.laba.labaId }"/>
			<hk:hide name="labaId" value="${labaVo.laba.labaId }"/>
			<c:if test="${userLogin && fn:length(taglist2)<3}">
			<hk:text name="name" size="10"/><hk:submit name="addtag" value="添加频道"/>
			</c:if>
		</hk:form>
	</div>
	</td>
	</tr></tbody></table>
	</div>
	<div class="hang">
		<hk:a href="/laba/back.do?${laba_tore_add }">返回</hk:a> 
		<hk:a href="/laba/op/op_tosendsms.do?labaId=${labaId}"><hk:data key="view.sendtomobile"/></hk:a> 
		<c:if test="${bomber!=null}">
		<hk:a href="/laba/op/magic.do?labaId=${labaId }&${laba_tore_add }">魔法</hk:a> 
		</c:if>
		<c:if test="${loginUser.userId==labaVo.laba.userId}"><hk:a href="/laba/op/op_del.do?labaId=${labaId }&${laba_tore_add }">删除</hk:a></c:if>
	</div>
	<!-- 喇叭评论 -->
<c:if test="${fn:length(labacmtvolist)>0}">
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<tr class="even"><td>喇叭评论</td></tr>
	</tbody></table>
	<jsp:include page="../inc/labacmtvo.jsp"></jsp:include>
	<hk:simplepage2 href="/laba/laba.do?labaId=${labaId}"/>
</c:if>
<c:if test="${fn:length(reflabalist)>0}">
	<c:forEach var="reflaba" items="${reflabalist}">
	<div class="hang"><hk:a href="/home.do?userId=${reflaba.refUserId}">${reflaba.refUser.nickName}</hk:a> <fmt:formatDate value="${reflaba.createTime}" pattern="yyyy-MM-dd HH:mm"/> 进行了转发 
	<c:if test="${loginUser.userId==reflaba.refUserId}"><hk:a href="/laba/op/op_delref.do?labaId=${labaId}">删除转发</hk:a></c:if>
	</div>
	</c:forEach>
	<c:if test="${morerefuser}">
	<div class="hang"><hk:a href="/laba/laba_refuser.do?labaId=${labaVo.laba.lavaId}&olabaId=${olabaId }&page=2"><hk:data key="view.more"/></hk:a></div>
	</c:if>
</c:if>