<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<div>
			<form method="post" action="<%=path %>/epp/web/cmpmsg_msgcreate.do" target="hideframe">
				<hk:hide name="ch" value="1"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<div class="divrow">
					<span class="split-r"><hk:data key="epp.cmpmsg.name"/>：<input type="text" class="text2" name="name"/></span>
					<hk:data key="epp.cmpmsg.tel"/>：<input type="text" class="text2" name="tel" maxlength="20"/>
				</div>
				<div class="infowarn" id="errormsg"></div>
				<div class="divrow">
					<hk:data key="epp.cmpmsg.content"/>：(<hk:data key="epp.cmpmsg.content.tip"/>)<br/>
					<textarea name="content" style="width:580px;height: 100px;"></textarea><br/>
					<div class="divrow" style="text-align: right;"><hk:submit clazz="btn" value="epp.submit" res="true"/></div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
function createok(error,msg,v){
	refreshurl();
}
function createerror(error,msg,v){
	setHtml('errormsg',msg);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>