<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.logo"/></c:set>
<hk:wap title="${title} -  ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form enctype="multipart/form-data" action="/epp/mgr/mgr_updatelogo.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="view.mgrsite.selectpic"/>:<br/>
			<hk:file name="f"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>