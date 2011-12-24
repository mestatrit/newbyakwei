<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpReserve"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">预约管理</div>
		<div class="mod_content">
			<div class="divrow">
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right" width="90px" class="b">服务人员</td>
						<td>
						${cmpActor.name }
						</td>
					</tr>
					<tr>
						<td align="right" width="90px" class="b">预约时间</td>
						<td>
						<fmt:formatDate value="${cmpReserve.reserveTime}" pattern="yyyy-MM-dd HH:mm"/>
						</td>
					</tr>
					<c:if test="${fn:length(svrlist)>0}">
						<tr>
							<td align="right" width="90px" class="b">预约的服务</td>
							<td>
								<c:forEach var="svr" items="${svrlist}">
									<span class="split-r">${svr.name }</span>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<tr>
						<td align="right" width="90px" class="b"></td>
						<td>
						<form id="frm" onsubmit="return subfrm(this.id)" method="post" action="<%=path %>/epp/web/op/webadmin/reserve_update.do" target="hideframe">
							<hk:hide name="companyId" value="${companyId}"/>
							<hk:hide name="reserveId" value="${reserveId}"/>
							更改状态：
							<hk:select name="reserveStatus" checkedvalue="${cmpReserve.reserveStatus}">
								<hk:option value="<%=CmpReserve.RESERVESTATUS_DEF %>" data="epp.cmpreserve.reservestatus1" res="true"/>
								<hk:option value="<%=CmpReserve.RESERVESTATUS_DOING %>" data="epp.cmpreserve.reservestatus2" res="true"/>
								<hk:option value="<%=CmpReserve.RESERVESTATUS_SUCCESS %>" data="epp.cmpreserve.reservestatus3" res="true"/>
								<hk:option value="<%=CmpReserve.RESERVESTATUS_CANCEL %>" data="epp.cmpreserve.reservestatus4" res="true"/>
							</hk:select>
							<hk:submit clazz="btn split-r" value="提交"/>
							<a href="${denc_return_url }">返回</a>
						</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function updateok(err,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>