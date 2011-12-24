<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${cmpBulletin.title}" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		${cmpBulletin.title}<br/>
		${cmpBulletin.content}<br/>
	</div>
	<div class="hang even"><hk:a href="/epp/mgr/bulletin_toupdate.do?companyId=${companyId}&bulletinId=${cmpBulletin.bulletinId }"><hk:data key="view.update"/></hk:a></div>
	<div class="hang even"><hk:a href="/epp/mgr/bulletin_list.do?companyId=${companyId}"><hk:data key="view.mgrsite.bulletin"/></hk:a></div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>