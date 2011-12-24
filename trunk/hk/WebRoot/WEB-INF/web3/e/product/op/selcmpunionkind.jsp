<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">分类管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<div class="text_14 heavy">
		<c:if test="${fn:length(list2)>0}">
			<a href="<%=path %>/e/op/product/op_toselcmpunionkind.do?companyId=${companyId }&productId=${productId}">分类 </a>&gt;
			<c:forEach var="kk" items="${list2}">
				<a href="<%=path %>/e/op/product/op_toselcmpunionkind.do?companyId=${companyId }&productId=${productId}&parentId=${kk.kindId}">${kk.name }</a> &gt;
			</c:forEach>
		</c:if>
	</div>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px">名称</td>
				<td></td>
			</tr>
			</table>
		</li>
		<c:if test="${fn:length(list)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="k" items="${list}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="200px">${k.name }</td>
						<td>
							<a href="<%=path %>/e/op/product/op_toselcmpunionkind.do?companyId=${companyId }&parentId=${k.kindId}&productId=${productId}">查看子分类</a>
							<c:if test="${!k.hasChild}">
								/ <a id="kind${k.kindId }" href="javascript:selcmpunionkind(${k.kindId })">选中此分类</a>
							</c:if>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/e/op/product/op_toselcmpunionkind.do?companyId=${companyId }&productId=${productId}&parentId=${parentId }"/>
		<div class="clr"></div>
	</div>
	<div align="center">
		<hk:button clazz="btn" value="回到产品" onclick="returntoproduct();"/>
	</div>
</div>
<script type="text/javascript">
function returntoproduct(){
	tourl("<%=path %>/e/op/product/op_productlistweb.do?companyId=${companyId}");
}
function selcmpunionkind(id){
	showSubmitDivForObj("kind"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/product/op_selcmpunionkind.do?companyId=${companyId }&productId=${productId}&kindId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>