<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.ChgCardAct"%>
<%@page import="com.hk.bean.UserCard"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:form action="/chgcard/act_edit.do">
		<hk:hide name="actId" value="${actId}"/>
		<hk:data key="chgcardact.name"/>:<br/>
		<hk:text name="name" value="${o.name}" maxlength="30"/><br/>
		<span class="ruo s"><hk:data key="chgcardact.name.tip"/></span><br/><br/>
		<hk:data key="chgcardact.persisthour"/>:<br/>
		<hk:text name="persistHour" value="${o.persistHour}" maxlength="2"/><br/>
		<span class="ruo s"><hk:data key="chgcardact.persisthour.tip"/></span><br/><br/>
		<hk:data key="chgcardact.actstatus"/>:<br/>
		<hk:radioarea name="actStatus" checkedvalue="${o.actStatus}">
			<hk:radio value="<%=ChgCardAct.ACTSTATUS_INUSE+"" %>" data="chgcardact.actstatus_inuse" res="true"/>
			<hk:radio value="<%=ChgCardAct.ACTSTATUS_PAUSE+"" %>" data="chgcardact.actstatus_pause" res="true"/>
		</hk:radioarea><br/><br/>
		<hk:data key="usercard.chgflg"/>:<br/>
		<hk:radioarea name="chgflg" checkedvalue="${o.chgflg}">
			<hk:radio value="<%=UserCard.CHGFLG_PUBLIC+"" %>" data="usercard.chgflg_public" res="true"/><br/>
			<hk:radio value="<%=UserCard.CHGFLG_PROTECT+"" %>" data="usercard.chgflg_protect" res="true"/>
		</hk:radioarea><br/>
		<hk:submit value="view.submit" res="true"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/chgcard/act_view.do?actId=${actId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>