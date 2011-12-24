<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:rmBlankLines rm="true">
			<hk:a href="/user/log/log_scorelog.do" clazz="nn"><hk:data key="view.scorelog_title"/></hk:a>|
			<hk:a href="/user/log/log_hkblog.do"><hk:data key="view.hkblog_title"/></hk:a>
		</hk:rmBlankLines><br/>
	</div>
	<div class="hang even"><hk:data key="view.remainscore"/>:${info.score }</div>
	<c:if test="${fn:length(scorelogvolist)>0}">
		<c:forEach var="vo" items="${scorelogvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${vo.content } 
				${vo.scoreLog.addcount }<hk:data key="view.scorelog"/> 
				<fmt:formatDate value="${vo.scoreLog.createTime}" pattern="yyyy-MM-dd HH:mm"/>
			</div>
		</c:forEach>
		<hk:simplepage href="/user/log/log_scorelog.do"/>
	</c:if>
	<c:if test="${fn:length(scorelogvolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>