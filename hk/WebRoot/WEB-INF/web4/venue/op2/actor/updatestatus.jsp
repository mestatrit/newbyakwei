<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpActor"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">
修改状态</div>
<div class="mod_content">
	<div>
		<form method="post" action="<%=path %>/h4/op/venue/actor_updatestatus.do" target="hideframe">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="actorId" value="${actorId}"/>
			<hk:hide name="ch" value="1"/>
			状态修改为：<br/>
			<hk:select name="actorStatus" checkedvalue="${cmpActor.actorStatus}">
				<hk:option value="<%=CmpActor.ACTORSTATUS_RUN %>" data="epp.cmpactor.actorStatus1" res="true"/>
				<hk:option value="<%=CmpActor.ACTORSTATUS_HOLIDAY %>" data="epp.cmpactor.actorStatus2" res="true"/>
				<hk:option value="<%=CmpActor.ACTORSTATUS_PAUSE %>" data="epp.cmpactor.actorStatus3" res="true"/>
				<hk:option value="<%=CmpActor.ACTORSTATUS_REFUSE %>" data="epp.cmpactor.actorStatus4" res="true"/>
			</hk:select>
			<hk:submit value="提交" clazz="btn split-r"/>
			<a href="${denc_return_url }">返回</a>
		</form>
	</div>
</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>