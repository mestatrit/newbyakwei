<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.op.confirmaddcompany.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="e.op.confirmaddcompany.company_already_exist"/></div>
	<div class="hang">
		<hk:form action="/e/op/op_confirmaddcompany.do">
			<hk:hide name="cid" value="${cid}"/>
			<hk:submit name="ok" value="e.op.confirmaddcompany.ok" res="true"/> 
			<hk:submit name="cancel" value="e.op.confirmaddcompany.cancel" res="true"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>