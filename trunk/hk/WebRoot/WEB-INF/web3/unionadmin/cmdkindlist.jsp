<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">分类管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px">名称</td>
				<td></td>
			</tr>
			</table>
		</li>
		<c:if test="${fn:length(cmdkindlist)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(cmdkindlist)>0}">
			<c:forEach var="k" items="${cmdkindlist}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px">${k.name }</td>
						<td>
							<a id="del${k.kindId }" href="javascript:del(${k.kindId })">从推荐中删除</a>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
</div>
<script type="text/javascript">
function del(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj("del"+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/cmpunion/op/union_delcmdkind.do?uid=${uid}&kindId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>