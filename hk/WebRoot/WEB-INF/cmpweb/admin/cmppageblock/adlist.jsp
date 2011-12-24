<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${cmpPageBlock.name }
			<a class="more" href="<%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId}&pageflg=${pageflg}">返回</a>
			</div>
			<div class="divrow">
				<input type="button" class="btn" value="创建广告" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId }&blockId=${blockId }')"/>
			</div>
			<div class="mod_content">
				<c:if test="${fn:length(cmpadblocklist)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="at" items="${cmpadblocklist}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:330px">
									<div style="padding:0 20px 0 0">
									<a href="<%=path %>/epp/web/op/webadmin/cmpad_update.do?adid=${at.cmpAd.adid }&companyId=${companyId}&blockId=${blockId}">${at.cmpAd.name }</a>
									</div>
								</div>
								<div class="f_l" style="width:150px;padding-left: 10px">
									<a href="javascript:deladblock(${at.oid })">取消推荐</a> / 
									<a href="javascript:reselblock(${at.oid })">重新推荐</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(cmpadblocklist)==0}">
					<div class="nodata">还没有推荐任何广告</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmppageblock_content.do?blockId=${blockId }&companyId=${companyId}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function deladblock(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmppageblock_delcmpadblock.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}

function reselblock(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmppageblock_reseladblock.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>