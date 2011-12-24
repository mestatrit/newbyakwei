<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${cmpBulletin.title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		${cmpBulletin.title} 
	<span class="ruo s"><fmt:formatDate value="${cmpBulletin.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
	</div>
	<div class="hang odd">
	${cmpBulletin.content}
	</div>
	<div class="hang even"><hk:a href="/epp/bulletin_list.do?companyId=${companyId}">返回</hk:a></div>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }">返回首页</a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>