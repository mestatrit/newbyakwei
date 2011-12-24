<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.product.mgr.everydaymgr"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<table width="500px" class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<hk:form method="get" action="/e/op/product/op_productlistweb2.do">
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
		<th width="100px">编号</th>
		<th width="200px">名称</th>
		<th width="80px">缩写</th>
		<th width="80px">价格</th>
		<th width="80px">分类</th>
		<th width="80px">状态</th>
		<th width="100px"></th>
	</tr>
	<c:forEach var="p" items="${list}">
	<tr class="tr-line" onmouseout="this.className='tr-line';" onmouseover="this.className='tr-line bg2';">
		<td>${p.pnum }</td>
		<td>${p.name }</td>
		<td>${p.shortName }</td>
		<td>￥${p.money }</td>
		<td><a href="<%=path %>/e/op/product/op_productlistweb2.do?companyId=${companyId }&s_sortId=${p.sortId }&s_name=${enc_s_name }">${p.cmpProductSort.name }</a></td>
		<td class="<c:if test="${!p.sell}">yzm</c:if>">
			<hk:data key="cmpproduct.sellstatus${p.sellStatus}"/>
		</td>
		<td>
		<c:if test="${p.sell}">
			<a href="javascript:stopsell(${p.productId })">停售</a>
		</c:if>
		<c:if test="${!p.sell}">
			<a href="javascript:runsell(${p.productId })">继续销售</a>
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pagecon">
<hk:page midcount="10" url="/e/op/product/op_productlistweb2.do?companyId=${companyId}&s_name=${enc_s_name }&s_sortId=${s_sortId }"/>
<div class="clr"></div>
</div>
</div>
<script type="text/javascript">
function stopsell(pid){
	tourl("<%=path%>/e/op/product/op_stopsell.do?companyId=${companyId}&page=${page}&productId="+pid);
}
function runsell(pid){
	tourl("<%=path%>/e/op/product/op_runsell.do?companyId=${companyId}&page=${page}&productId="+pid);
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>