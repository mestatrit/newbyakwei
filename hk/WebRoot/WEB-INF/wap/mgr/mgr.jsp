<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.WebUtil"%><%@page import="com.hk.frame.util.DataUtil"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="站点管理 - ${o.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<div class="ha"><hk:a href="/epp/mgr/mgr_toedit.do?companyId=${companyId}"><hk:data key="view.mgrsite.cmp"/></hk:a></div>
		<div class="ha"><hk:a href="/epp/mgr/mgr_toupdatelogo.do?companyId=${companyId}"><hk:data key="view.mgrsite.logo"/></hk:a></div>
		<c:forEach var="cm" items="${cmpmoduleList}">
			<div class="ha"><hk:a href="${cm.tmlModule.admin_funcurl}?companyId=${companyId }">${cm.tmlModule.admin_title }</hk:a><br/></div>
		</c:forEach>
		<div class="ha"><a href="/epp/index.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>