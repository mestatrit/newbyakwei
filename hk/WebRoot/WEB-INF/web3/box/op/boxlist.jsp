<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.box" /></c:set>
<c:set var="mgr_content" scope="request">
	<c:set var="page_url" scope="request"><%=path%>/box/op/op_my.do?v=1</c:set>
	<jsp:include page="boxlist_inc.jsp"></jsp:include>
<script type="text/javascript">
function toedit(id){
	if(window.confirm("修改信息需要停止宝箱，确定要停止？")){
		tourl("<%=path %>/box/op/op_toeditboxweb.do?boxId=${b.boxId }");
	}
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>