<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">企业会员</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)>0}">
				<div class="divrow b" style="height: 100%;">
					<div class="f_l split-r" style="width:70px;">
						地区
					</div>
					<div class="f_l split-r" style="width:80px;">
						昵称
					</div>
					<div class="f_l split-r" style="width:80px;">
						姓名
					</div>
					<div class="f_l split-r" style="width:200px;">
						E-mail
					</div>
					<div class="f_l" style="width:100px;">
						手机
					</div>
					<div class="clr"></div>
				</div>
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" style="height: 100%;" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l split-r" style="width:70px;overflow: hidden;word-break:break-all;">
							${n.user.pcity.name }
						</div>
						<div class="f_l split-r" style="width:80px;overflow: hidden;">
							<a target="_blank" href="<%=path %>/epp/web/user.do?companyId=${companyId }&userId=${n.userId}">${n.user.nickName }</a>
						</div>
						<div class="f_l split-r" style="width:80px;height: 100%;overflow: hidden;">
							${n.userOtherInfo.name }&nbsp;
						</div>
						<div class="f_l split-r" style="width:200px;overflow: hidden;word-break:break-all;">
							${n.userOtherInfo.email }
						</div>
						<div class="f_l" style="width:100px;overflow: hidden;">
							${n.userOtherInfo.mobile }
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何企业会员</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmprefuser.do?navoid=${navoid }&companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpfrlink(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpfrlink_del.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>