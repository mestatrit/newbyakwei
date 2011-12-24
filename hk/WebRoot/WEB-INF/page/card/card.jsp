<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="card.card.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		${o.simpleName }
		<c:if test="${(loginUser.userId==userId) || (loginUserId!=o.userId && !o.protectedChange)}">
		<c:if test="${not empty info.mobile}"><br/><br/>
			<hk:data key="userotherinfo.mobile"/>:<br/>
			${info.mobile}
		</c:if>
		<c:if test="${not empty o.anotherMobile}"><br/><br/>
			<hk:data key="usercard.anothermobile"/>:<br/>
			${o.anotherMobile}
		</c:if>
		</c:if>
		<c:if test="${not empty info.prvWeb}"><br/><br/>
			<hk:data key="userotherinfo.prvweb"/>:<br/>
			<a href="http://${info.prvWeb}" target="_blank">${info.prvWeb}</a>
		</c:if>
		<c:if test="${not empty o.workAddr}"><br/><br/>
			<hk:data key="usercard.workaddr"/>:<br/>
			${o.workAddr}
		</c:if>
		<c:if test="${not empty o.workPostcode}"><br/><br/>
			<hk:data key="usercard.workpostcode"/>:<br/>
			${o.workPostcode}
		</c:if>
		<c:if test="${not empty o.workplace}"><br/><br/>
			<hk:data key="usercard.workplace"/>:<br/>
			${o.workplace}
		</c:if>
		<c:if test="${not empty o.workPlaceWeb}"><br/><br/>
			<hk:data key="usercard.workplaceweb"/>:<br/>
			${o.workPlaceWeb}
		</c:if>
		<c:if test="${not empty o.jobRank}"><br/><br/>
			<hk:data key="usercard.jobrank"/>:<br/>
			${o.jobRank}
		</c:if>
		<c:if test="${not empty o.email}"><br/><br/>
			<hk:data key="usercard.email"/>:<br/>
			${o.email}
		</c:if>
		<c:if test="${not empty o.qq}"><br/><br/>
			<hk:data key="usercard.qq"/>:<br/>
			${o.qq}
		</c:if>
		<c:if test="${not empty o.msn}"><br/><br/>
			<hk:data key="usercard.msn"/>:<br/>
			${o.msn}
		</c:if>
		<c:if test="${not empty o.gtalk}"><br/><br/>
			<hk:data key="usercard.gtalk"/>:<br/>
			${o.gtalk}
		</c:if>
		<c:if test="${not empty o.skype}"><br/><br/>
			<hk:data key="usercard.skype"/>:<br/>
			${o.skype}
		</c:if>
		<c:if test="${info.birthdayDate!=0 && info.birthdayMonth!=0}"><br/><br/>
			<hk:data key="userotherinfo.birthday"/>:<br/>
			${info.birthdayMonth }月${info.birthdayDate }日
		</c:if>
		<c:if test="${not empty o.homeAddr}"><br/><br/>
			<hk:data key="usercard.homeaddr"/>:<br/>
			${o.homeAddr}
		</c:if>
		<c:if test="${not empty o.homePostcode}"><br/><br/>
			<hk:data key="usercard.homepostcode"/>:<br/>
			${o.homePostcode}
		</c:if>
		<c:if test="${(loginUserId==userId) || (loginUserId!=o.userId && !o.protectedChange)}">
		<c:if test="${not empty o.homeTelphone}"><br/><br/>
			<hk:data key="usercard.hometelphone"/>:<br/>
			${o.homeTelphone}
		</c:if>
		</c:if>
		<c:if test="${not empty o.intro}"><br/><br/>
			<hk:data key="usercard.intro"/>:<br/>
			${o.intro}
		</c:if>
		<c:if test="${loginUser.userId==o.userId}">
			<div class="hang">
				<hk:form method="get" action="/card/card_toedit.do">
					<hk:submit value="view.edit" res="true"/>
				</hk:form>
			</div>
		</c:if>
		<c:if test="${loginUser.userId!=o.userId}">
			<div class="hang">
				<hk:form method="get" action="/card/card_deluser.do">
					<hk:hide name="userId" value="${o.userId}"/>
					<hk:submit value="view.deletethiscard" res="true"/>
				</hk:form>
			</div>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/card/card_back.do?${queryString}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>