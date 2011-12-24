<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">请求</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
	<ul class="orderlist">
		<c:if test="${fn:length(volist)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(volist)>0}">
			<c:forEach var="vo" items="${volist}">
				<li class="row" onmouseover="this.className='row bg1';" onmouseout="this.className='row';">
					<span class="split-r">${vo.content }</span>
					<a id="agree${vo.cmpUnionReq.reqid }" href="javascript:agree(${vo.cmpUnionReq.reqid })">通过</a>
					/
					<a id="refuse${vo.cmpUnionReq.reqid }" href="javascript:refuse(${vo.cmpUnionReq.reqid })">拒绝</a>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/message_req.do?uid=${uid}&dealflg=${dealflg }"/>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
function agree(id){
	showSubmitDivForObj("agree"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/message_deal.do?uid=${uid}&reqid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function refuse(id){
	showSubmitDivForObj("refuse"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/message_refuse.do?uid=${uid}&reqid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>