<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">优惠券图片</div>
			<div class="mod_content">
				<div class="divrow">
					<form method="post" enctype="multipart/form-data" action="<%=path %>/epp/web/op/webadmin/coupon_uploadpic.do" target="hideframe">
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="couponId" value="${couponId}"/>
						<input type="file" size="50" name="f"/>
						<hk:submit value="上传" clazz="btn"/>
					</form>
				</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="n" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:80px">
									<img src="${n.h_0Pic }"/>
								</div>
								<div class="f_l" style="width:100px;">
									<a href="javascript:selpic(${n.photoId })">选择</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何数据</div>
				</c:if>
			</div>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/coupon_selprizepic.do?companyId=${companyId}&couponId=${couponId}&navoid=${navoid }</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function uploaderror(error,msg,v){
	setHtml(getoidparam(error),msg);
}
function uploadok(error,msg,v){
	tourl("<%=path %>/epp/web/op/webadmin/coupon_update.do?companyId=${companyId}&couponId=${couponId}&navoid=${navoid }");
}
function selpic(photoId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/coupon_selpic.do?companyId=${companyId}&couponId=${couponId}&photoId="+photoId,
		cache:false,
    	dataType:"html",
		success:function(data){
			tourl("<%=path%>/epp/web/op/webadmin/coupon_update.do?companyId=${companyId}&couponId=${couponId}&navoid=${navoid }");
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>