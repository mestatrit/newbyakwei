<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="title"><hk:data key="view.prouser.title2" arg0="${proUser.creater.nickName}" arg1="${proUser.nickName}"/></c:set>
<hk:wap title="${title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:set var="url">/home.do?userId=${proUser.createrId}</c:set>
		<hk:data key="view.prouser.title" arg0="${url}" arg1="${proUser.creater.nickName}" arg2="${proUser.nickName}"/><br/>
		<c:if test="${not empty proUser.intro}">@${proUser.nickName}的介绍:<br/>${proUser.intro }<br/></c:if>
		<c:if test="${loginUser.userId!=proUser.userId}"><hk:a href="/op/prouser_add.do?prouserId=${prouserId}">加入邀请${proUser.nickName }的行列</hk:a></c:if>
	</div>
	<c:if test="${fn:length(impressionvolist)>0}">
		<div class="hang">关于${proUser.nickName }</div>
		<c:set var="impression_addstr" value="prouserId=${prouserId}" scope="request"/>
		<jsp:include page="../inc/impressionvolist.jsp"></jsp:include>
	</c:if>
	<c:if test="${!reviewed || reviewed==null}">
		<div class="hang" onkeydown="submitLaba(event)">
			<hk:form name="labafrm" onsubmit="return confirmCreate();" action="/impression/op/op_add.do">
				<hk:hide name="prouserId" value="${prouserId}"/>
				我认识${proUser.nickName}，提交我对他的印象:<br/>
				<hk:textarea oid="status" name="content" clazz="ipt2"/><br/>
				<hk:submit value="提交"/> <span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
			</hk:form>
			<jsp:include page="../inc/labainputjs.jsp"></jsp:include>
		</div>
	</c:if>
	<c:if test="${fn:length(wellist)>0}">
		<div class="hang">欢迎${proUser.nickName }的人们</div>
		<div class="hang">
			<c:forEach var="wu" items="${wellist}">
				<hk:a href="/home.do?userId=${wu.userId}">${wu.user.nickName}</hk:a> 
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${proUser.userId>0}">
		<div class="hang"><hk:a href="/home.do?userId=${proUser.userId}"><hk:data key="view.return"/></hk:a></div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>