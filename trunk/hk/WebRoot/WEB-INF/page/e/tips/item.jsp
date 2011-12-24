<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${company.name }" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">${company.name }</div>
	<div class="hang odd">
	<table class="list" cellpadding="0" cellspacing="0">
	<tr class="${clazz_var }">
		<c:if test="${!noneedhead}">
		<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.cmpTip.user.head32Pic }"/></td></c:if>
		<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.cmpTip.user.head48Pic }"/></td></c:if>
		</c:if>
		<td>
			<hk:a href="/home.do?userId=${vo.cmpTip.userId}">${vo.cmpTip.user.nickName}</hk:a>
			<c:if test="${vo.cmpTip.toDo}"><hk:data key="view2.wantto"/></c:if>
			<c:if test="${vo.cmpTip.done}"><hk:data key="view2.didthis"/></c:if>
			<span class="s ruo"><fmt:formatDate value="${vo.cmpTip.createTime}" pattern="yy-MM-dd HH:mm"/></span>
			<br/>
			<hk:a href="/e/cmp.do?companyId=${company.companyId}">${company.name }</hk:a>：${vo.cmpTip.content } 
		</td>
	</tr>
	</table>
	</div>
	<div class="hang even">
	<hk:form action="/op/cmp_createusercmptip.do">
		<hk:hide name="tipId" value="${tipId}"/>
		<c:if test="${userLogin}">
			<c:if test="${vo.addDone}">
				√<hk:data key="view2.i_done_this"/><hk:a href="/op/cmp_deleteusercmptip.do?tipId=${tipId}">x</hk:a>
				<hk:submit name="todo" value="view2.add_as_todo" res="true"/>
			</c:if>
			<c:if test="${vo.addToDo}">
				<hk:submit name="done" value="view2.i_done_this" res="true"/>
				√<hk:data key="view2.add_as_todo"/><hk:a href="/op/cmp_deleteusercmptip.do?tipId=${tipId}">x</hk:a>
			</c:if>
			<c:if test="${!vo.addDone && !vo.addToDo}">
				<hk:submit name="done" value="view2.i_done_this" res="true"/>
				<hk:submit name="todo" value="view2.add_as_todo" res="true"/>
			</c:if>
		</c:if>
	</hk:form>
	</div>
	<div class="hang">
	<c:if test="${vo.cmpTip.userId==loginUser.userId}">
	<hk:a href="/op/cmp_deletecmptip.do?tipId=${tipId}"><hk:data key="view2.delete"/></hk:a>
	</c:if>
	<hk:a href="/e/cmp.do?companyId=${company.companyId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>