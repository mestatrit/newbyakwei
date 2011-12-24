<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Act"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.createact"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/act/op/act_add.do">
			<hk:data key="act.name"/><span class="ruo s">(<hk:data key="act.name.tip"/>)</span>:<br/>
			<hk:text name="name" value="${o.name}" maxlength="15"/><br/><br/>
			<hk:data key="act.beginTime"/>:<br/>
			<c:set var="beginTime"><fmt:formatDate value="${o.beginTime}" pattern="yyyyMMddHHmm"/></c:set>
			<hk:text name="beginTime" value="${beginTime}" maxlength="12"/><br/>
			<span class="ruo s"><hk:data key="act.time.tip"/></span><br/><br/>
			<hk:data key="act.endTime"/>:<br/>
			<c:set var="endTime"><fmt:formatDate value="${o.endTime}" pattern="yyyyMMddHHmm"/></c:set>
			<hk:text name="endTime" value="${endTime}" maxlength="12"/><br/>
			<span class="ruo s"><hk:data key="act.time.tip"/></span><br/><br/>
			<hk:data key="act.intro"/><span class="ruo s">(<hk:data key="act.intro.tip"/>)</span>:<br/>
			<hk:textarea name="intro" value="${o.intro}" /><br/><br/>
			<hk:data key="act.addr"/><span class="ruo s">(<hk:data key="act.addr.tip"/>)</span>:<br/>
			<hk:textarea name="addr" value="${o.addr}"/><br/><br/>
			<hk:radioarea name="needCheck" checkedvalue="${o.needCheck}" forcecheckedvalue="0">
				<hk:radio value="<%=Act.NEEDCHECK_N %>" data="act.needcheck_0" res="true"/> 
				<hk:radio value="<%=Act.NEEDCHECK_Y %>" data="act.needcheck_1" res="true"/>
			</hk:radioarea><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>