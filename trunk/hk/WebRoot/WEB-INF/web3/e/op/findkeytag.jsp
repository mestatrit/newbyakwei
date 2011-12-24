<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">购买关键词</c:set>
<c:set var="mgr_content" scope="request">
<div>
<c:if test="${empty name}">
	<h3>请输入要购买的关键词</h3>
	<div class="bdbtm"></div>
	<form id="order_frm" method="post" action="<%=path %>/e/op/cmporder_findkeytag.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<div style="padding-left: 20px;">
			<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td><hk:text name="name" maxlength="20" clazz="text"/></td>
				</tr>
				<tr>
					<td>
						<div class="f_l">
							<input type="submit" value="查看竞标情况" class="btn2" style="width:120px"/>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</c:if>
	<c:if test="${not empty name}">
		<c:if test="${keyTag==null}">
			<hk:form action="/e/op/cmporder_tobuykeytag.do">
				<hk:hide name="name" value="${name}"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<strong class="text_14">目前还没有人搜索“${name}”</strong><br/>
				<hk:submit name="buy" value="马上购买" clazz="btn"/> 
				<hk:button name="continue_find" value="继续搜索" clazz="btn" onclick="tofindkeytag()"/> 
			</hk:form>
		</c:if>
		<c:if test="${keyTag!=null}">
			<hk:form action="/e/op/cmporder_tobuykeytag.do">
				<hk:hide name="name" value="${name}"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<c:if test="${keyTagSearchInfo!=null }">
					<c:set var="searchCount">${keyTagSearchInfo.searchCount}</c:set>
				</c:if>
				<c:if test="${keyTagSearchInfo==null }">
					<c:set var="searchCount">0</c:set>
				</c:if>
				<strong class="text_14">您的关键词“${name}”本月搜索量为${searchCount }个，目前还没有人购买</strong><br/>
				<hk:submit name="buy" value="马上购买" clazz="btn"/> 
				<hk:button name="continue_find" value="继续搜索" clazz="btn" onclick="tofindkeytag()"/> 
			</hk:form>
		</c:if>
	</c:if>
</div>
<script type="text/javascript">
function tofindkeytag(){
	tourl('/e/op/cmporder_findkeytag.do?companyId=${companyId}');
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>