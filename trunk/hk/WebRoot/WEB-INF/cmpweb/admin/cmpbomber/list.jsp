<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">内容管理员</div>
		<div class="mod_content">
			<div class="divrow">
			内容管理员可以对论坛不良内容进行删除
			</div>
			<div class="divrow">
				<form action="<%=path %>/epp/web/op/webadmin/adminbomber_find.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="companyId" value="${companyId}"/>
					昵称： <hk:text name="nickName" clazz="text"/>
					<hk:submit clazz="btn" value="添加内容管理员"/>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:200px">
								<img src="${n.user.head48Pic }"/> ${n.user.nickName }
							</div>
							<div class="f_l" style="width:100px">
								${n.bombcount }个炸弹
							</div>
							<div class="f_l" style="width:80px;padding-left: 20px">
								<a href="<%=path %>/epp/web/op/webadmin/adminbomber_update.do?companyId=${companyId}&oid=${n.oid}">修改</a> / 
								<a href="javascript:delbomber(${n.oid })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何内容管理员</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/adminbomber.do?companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delbomber(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/adminbomber_del.do?companyId=${companyId}&oid="+oid,
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