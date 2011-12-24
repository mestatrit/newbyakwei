<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">我要提问
</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 600px" onkeyup="keysubfrm(event)">
<c:set var="form_action" scope="request">${ctx_path }/tb/ask_prvask</c:set>
<jsp:include page="askform.jsp"></jsp:include>
</div>
<script type="text/javascript">
function askerr(e, msg, v){
	setHtml(getoidparam(e), msg);
	hideGlass();
}

function askok(e, msg, v){
	tourl('${ctx_path}/tb/ask?aid=' + v);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>