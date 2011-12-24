<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">销售网络管理</div>
		<div class="mod_content">
			<div class="divrow">
				<input value="添加销售网络" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpsellnet_create.do?companyId=${companyId}&navoid=${navoid }')"/>
				<input value="分类管理" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpsellnet_kindlist.do?companyId=${companyId}&navoid=${navoid }')"/>
			</div>
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/cmpsellnet.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="kindId" value="${kindId}"/>
					<hk:hide name="navoid" value="${navoid}"/>
					名称：<hk:text name="name" value="${name}" clazz="text"/>
					<hk:submit value="查询" clazz="btn split-r"/>
					<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&navoid=${navoid }">所有</a>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:480px">
								${n.name }<br/>
								<c:if test="${n.kindId>0}">
									分类：${n.cmpSellNetKind.name }<br/>
								</c:if>
								<strong>电话：</strong>${n.tel }<br/>
								<strong>地址：</strong>${n.addr }
							</div>
							<div class="f_l" style="width:80px;padding-left: 20px">
								<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet_update.do?oid=${n.oid }&companyId=${companyId}&navoid=${navoid }">修改</a> / 
								<a href="javascript:delcmpsellnet(${n.oid })">删除</a><br/>
								<a href="javascript:showorderflgwin(${n.oid },${n.orderflg })">设置序号</a><br/>
								<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet_setmap.do?oid=${n.oid }&companyId=${companyId}&navoid=${navoid }">设置地图</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何销售网络</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&kindId=${kindId}&name=${enc_name}&navoid=${navoid }</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpsellnet(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpsellnet_del.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function showorderflgwin(oid,orderflg){
	var title="设置序号";
	var html='<form id="orderflgfrm" method="post" onsubmit="return setorderflg(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpsellnet_setorderflg.do" target="hideframe">';
	html+='<div class="b">序号越大，排序越靠前</div>';
	html+='<hk:hide name="companyId" value="${companyId}"/>';
	html+='<input type="hidden" name="oid" value="'+oid+'"/>';
	html+='序号：<input type="text" name="orderflg" value="'+orderflg+'" class="text2"/>';
	html+='<hk:submit value="view2.submit" res="true" clazz="btn"/>';
	html+='</form>';
	createSimpleCenterWindow('orderwin',400, 200, title, html,"hideWindow('orderwin')");
}
function setorderflg(){
	return true;
}
function setorderflgok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>