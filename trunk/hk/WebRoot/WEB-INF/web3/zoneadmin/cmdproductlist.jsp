<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">已推荐产品</c:set>
<c:set var="mgr_content" scope="request">
<c:if test="${fn:length(cmdproductlist)==0}">
<div align="center" class="text_14"><hk:data key="nodatalist"/></div>
</c:if>
<c:if test="${fn:length(cmdproductlist)>0}">
	<table class="infotable infotable2" cellpadding="0" cellspacing="0">
		<tr>
		<th width="300px">名称</th>
		<th width="200px"></th>
		</tr>
		<c:forEach var="c" items="${cmdproductlist}">
		<tr onmouseout="this.className='';" onmouseover="this.className='bg2';">
			<td><a href="<%=path %>/product.do?pid=${c.productId}" target="_blank">${c.cmpProduct.name }</a></td>
			<td><a id="cmd${c.oid }" href="javascript:delcmdproduct(${c.oid })">取消推荐</a></td>
		</tr>
		</c:forEach>
	</table>
	<div>
		<hk:page midcount="10" url="/op/cmd/cmd_cmdproductlist.do"/>
		<div class="clr"></div>
	</div>
<script type="text/javascript">
function delcmdproduct(id){
	if(confirm("确定要取消推荐?")){
		showSubmitDivForObj('cmd'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/op/cmd/cmd_delcmdproduct.do?oid='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		})
	}
}
</script>
</c:if>
</c:set>
<jsp:include page="../inc/zoneadminframe.jsp"></jsp:include>