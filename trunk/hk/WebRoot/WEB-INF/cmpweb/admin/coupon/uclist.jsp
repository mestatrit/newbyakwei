<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${coupon.name }
			<a class="more" href="<%=path %>/epp/web/op/webadmin/coupon.do?companyId=${companyId}&navoid=${navoid}">返回</a>
			</div>
			<div class="mod_content">
				<div class="divrow">
					<form method="get" action="<%=path %>/epp/web/op/webadmin/coupon_uclist.do">
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="couponId" value="${couponId}"/>
						<hk:hide name="navoid" value="${navoid}"/>
						暗号：<hk:text name="mcode" clazz="text" value="${mcode}"/>
						<hk:submit clazz="btn" value="查询"/>
					</form>
				</div>
				<div class="divrow">
					<div class="f_l b" style="width: 150px">
						用户
					</div>
					<div class="f_l b" style="width: 100px">
						暗号
					</div>
					<div class="clr"></div>
				</div>
				<c:forEach var="uc" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width: 150px">
							<img src="${uc.user.head48Pic}" /> ${uc.user.nickName }
						</div>
						<div class="f_l" style="width: 100px">
							${uc.mcode }
							<c:if test="${uc.couponUsed}">
								<div class="b" style="color: red;">已兑换</div>
							</c:if>
						</div>
						<div class="f_l" style="width: 100px">
							<a href="javascript:setused(${uc.oid })">设为已兑换</a>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何数据</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path%>/epp/web/op/webadmin/coupon_uclist.do?companyId=${companyId}&couponId=${couponId}&navoid=${navoid}&mcode=${mcode}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function setused(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/coupon_setused.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>