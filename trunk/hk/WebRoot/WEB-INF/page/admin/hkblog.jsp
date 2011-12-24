<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.scorelog_title"/></div>
	<c:if test="${fn:length(adminhkbvolist)>0}">
		<c:forEach var="vo" items="${adminhkbvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${vo.adminHkb.userId}">${vo.adminHkb.user.nickName}</hk:a> 
				${vo.content } 
				${vo.adminHkb.addCount } 
				${vo.adminHkb.content }
				<fmt:formatDate value="${vo.adminHkb.createTime}" pattern="yyyy-MM-dd HH:ss"/>
			</div>
		</c:forEach>
		<hk:simplepage href="/user/log/log_scorelog.do"/>
	</c:if>
	<c:if test="${fn:length(adminhkbvolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>