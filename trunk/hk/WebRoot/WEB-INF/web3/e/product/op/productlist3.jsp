<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">加入联盟分类</c:set>
<c:set var="mgr_content" scope="request">
<div>
<table width="500px" class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<hk:form method="get" action="/e/op/product/op_productlistweb3.do">
				<hk:hide name="companyId" value="${companyId}"/>
				名称:<hk:text name="s_name" value="${name}" clazz="text_short_1"/>
				分类:
				<hk:select name="s_sortId" checkedvalue="${s_sortId}">
					<hk:option value="0" data="全部"/>
					<c:forEach var="s" items="${sortlist}">
						<hk:option value="${s.sortId}" data="${s.name}"/>
					</c:forEach>
				</hk:select> 
				状态:
				<hk:select name="s_sellStatus" checkedvalue="${s_sellStatus}">
					<hk:option value="-1" data="全部"/>
					<hk:option value="0" data="cmpproduct.sellstatus0" res="true"/>
					<hk:option value="1" data="cmpproduct.sellstatus1" res="true"/>
				</hk:select> 
				<hk:submit value="查询" clazz="btn"/>
			</hk:form><br/>
			<div class="bdbtm"></div>
		</td>
	</tr>
</table>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr class="tr-title">
		<th width="200px">名称</th>
		<th width="80px">联盟分类</th>
		<th width="150px">选择分类</th>
	</tr>
	<c:forEach var="p" items="${list}">
	<tr class="tr-line" onmouseout="this.className='tr-line';" onmouseover="this.className='tr-line bg2';">
		<td width="200px">${p.name }</td>
		<td width="80px">${p.cmpUnionKind.name }
		</td>
		<td width="150px">
			<a href="javascript:toselcmpunionkind(${p.productId })">选择联盟分类</a>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pagecon">
<hk:page midcount="10" url="/e/op/product/op_productlistweb3.do?companyId=${companyId}&s_name=${enc_s_name }&s_sortId=${s_sortId }"/>
<div class="clr"></div>
</div>
</div>
<script type="text/javascript">
function toselcmpunionkind(id){
	tourl("<%=path%>/e/op/product/op_toselcmpunionkind.do?companyId=${companyId}&productId="+id);
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>