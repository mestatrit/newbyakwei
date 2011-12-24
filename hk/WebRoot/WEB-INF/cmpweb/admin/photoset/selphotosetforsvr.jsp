<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">选择图集</div>
			<div class="mod_content">
				<img src="${companyPhoto.pic60 }"/><br/>
				<c:if test="${fn:length(list)>0}">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:80px">
								<c:if test="${not empty n.picPath}">
								<img src="${n.pic60 }"/><br/>
								</c:if>
								${n.name }
							</div>
							<div class="f_l" style="width:150px;padding-left: 20px;">
								<a href="javascript:selset(${n.setId })">选择</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何图集</div>
				</c:if>
				<div>
					<a class="more2" href="${denc_return_url }">返回</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function selset(setId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/info_selphotosetforsvr.do?companyId=${companyId}&ch=1&svrId=${svrId}&setId="+setId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>