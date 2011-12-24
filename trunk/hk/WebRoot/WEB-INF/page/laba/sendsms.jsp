<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:if test="${empty vo.longContent}">${vo.content }</c:if>
		<c:if test="${not empty vo.longContent}">${vo.longContent }</c:if>
		<br/><br/>
		<hk:form action="/laba/op/op_sendsms.do">
			<hk:hide name="userId" value="${loginUser.userId}"/>
			<hk:hide name="labaId" value="${labaId}"/>
		1、<hk:data key="view.sendtomymobile"/><hk:submit value="view.send" res="true"/>
		</hk:form><br/>
		2、<hk:data key="view.sendtousercard"/><br/>
		<hk:form method="get" action="/laba/op/op_tosendsms.do">
			<hk:hide name="labaId" value="${labaId}"/>
			<hk:text name="key" value="${key}"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="au" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:form action="/laba/op/op_sendsms.do">
					<hk:hide name="userId" value="${au.userCard.userId}"/>
					<hk:hide name="labaId" value="${labaId}"/>
					${au.userCard.simpleName} 
					<hk:submit value="view.send" res="true"/>
				</hk:form>
			</div>
		</c:forEach>
		<hk:simplepage href="/laba/op/op_tosendsms.do?labaId=${labaId }&key=${enc_key }"/>
	</c:if>
	<div class="hang"><hk:data key="view.sendsms_jsp" arg0="http://shop58916393.taobao.com/" arg1="/help_hkbhelp.do?hfrom=sendsms&labaId=${labaId}"/></div>
	<div class="hang">
		<hk:a href="/laba/laba.do?labaId=${labaId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>