<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">销售网络分类</div>
		<div class="mod_content">
			<div class="divrow">
				<input type="button"  class="btn" value="销售网络管理" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&navoid=${navoid }')"/>
			</div>
			<div class="divrow">
				<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpsellnet_createkind.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/>
					名称：<hk:text name="name" clazz="text"/>
					<hk:submit value="创建分类" clazz="btn"/>
					<div class="infowarn" id="errmsg"></div>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width:150px">
							${n.name }
						</div>
						<div class="f_l" style="width:80px;padding-left: 20px">
							<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet_updatekind.do?kindId=${n.kindId }&companyId=${companyId}&navoid=${navoid }">修改</a> / 
							<a href="javascript:delcmpsellnet(${n.kindId })">删除</a><br/>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何分类</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delkind(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpsellnet_delkind.do?companyId=${companyId}&kindId="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function subfrm(frmid){
	showGlass(frmid);
	setHtml('errmsg','');
	return true;
}
function createerror(error,msg,v){
	hideGlass();
	setHtml('errmsg',msg);
}
function createok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>