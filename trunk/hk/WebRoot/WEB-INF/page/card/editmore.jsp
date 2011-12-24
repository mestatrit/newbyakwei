<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.UserCard"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="card.edit.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:rmBlankLines rm="true">
		<hk:a href="/card/card_list.do"><hk:data key="chgcard.list.mycard"/></hk:a>|
		<hk:a href="/chgcard/act.do"><hk:data key="chgcard.list.act"/></hk:a>|
		<hk:a href="/card/card_toedit.do" clazz="nn">编辑我的名片</hk:a>
	</hk:rmBlankLines>
	</div>
	<div class="hang even">
		<hk:form action="/card/card_edit.do">
			<hk:hide name="editmore" value="1"/>
			<hk:data key="usercard.name"/>:<br/>
			<hk:text name="name" value="${info.name}" maxlength="20"/><br/><br/>
			<hk:data key="userotherinfo.prvweb"/>:<br/>
			<hk:text name="prvWeb" value="${info.prvWeb}" maxlength="100"/><br/><br/>
			<hk:data key="usercard.homeaddr"/>:<br/>
			<hk:text name="homeAddr" value="${o.homeAddr}" maxlength="100"/><br/><br/>
			<hk:data key="usercard.homepostcode"/>:<br/>
			<hk:text name="homePostcode" value="${o.homePostcode}" maxlength="10"/><br/><br/>
			<hk:data key="usercard.hometelphone"/>:<br/>
			<hk:text name="homeTelphone" value="${o.homeTelphone}" maxlength="20"/><br/><br/>
			<hk:data key="usercard.workaddr"/>:<br/>
			<hk:text name="workAddr" value="${o.workAddr}" maxlength="100"/><br/><br/>
			<hk:data key="usercard.workpostcode"/>:<br/>
			<hk:text name="workPostcode" value="${o.workPostcode}" maxlength="10"/><br/><br/>
			<hk:data key="usercard.workplace"/>:<br/>
			<hk:text name="workplace" value="${o.workplace}" maxlength="100"/><br/><br/>
			<hk:data key="usercard.workplaceweb"/>:<br/>
			<hk:text name="workPlaceWeb" value="${o.workPlaceWeb}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.jobrank"/>:<br/>
			<hk:text name="jobRank" value="${o.jobRank}" maxlength="20"/><br/><br/>
			<hk:data key="usercard.anothermobile"/>:<br/>
			<hk:text name="anotherMobile" value="${o.anotherMobile}" maxlength="11"/><br/><br/>
			<hk:data key="usercard.email"/>:<br/>
			<hk:text name="email" value="${o.email}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.qq"/>:<br/>
			<hk:text name="qq" value="${o.qq}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.msn"/>:<br/>
			<hk:text name="msn" value="${o.msn}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.gtalk"/>:<br/>
			<hk:text name="gtalk" value="${o.gtalk}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.skype"/>:<br/>
			<hk:text name="skype" value="${o.skype}" maxlength="50"/><br/><br/>
			<hk:data key="usercard.chgflg"/>:<br/>
			<hk:radioarea name="chgflg" checkedvalue="${o.chgflg}">
				<hk:radio value="<%=UserCard.CHGFLG_PUBLIC+"" %>" data="usercard.chgflg_public" res="true"/><br/>
				<hk:radio value="<%=UserCard.CHGFLG_PROTECT+"" %>" data="usercard.chgflg_protect" res="true"/><span class="ruo s"> 各种电话不能被查看</span>
			</hk:radioarea><br/><br/>
			<hk:data key="usercard.intro"/>:<br/>
			<hk:textarea name="intro" value="${o.intro}"/>
			<div class="hang"><hk:submit value="view.submit" res="true"/></div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/card/card.do?userId=${loginUser.userId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>