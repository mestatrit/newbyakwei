<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">产品管理</div>
			<div class="mod_content">
				<div class="divrow">
					<input type="button" class="btn split-r" value="添加产品" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpproduct_create.do?companyId=${companyId }&navoid=${navoid }')"/>
					<input type="button" class="btn" value="管理产品分类" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId }&navoid=${navoid }')"/>
				</div>
				<c:if test="${fn:length(list)>0}">
					<div class="divrow b">
					在此推荐的产品将会在首页产品模块中显示
					</div>
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="n" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:200px">
									<c:if test="${not empty n.headPath}"><img src="${n.head60 }"/><br/></c:if>
									${n.name }
								</div>
								<div class="f_l" style="width:100px">
									${n.cmpProductSort.name }
								</div>
								<div class="f_l" style="width:250px;padding-left: 20px;text-align: center;">
									<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_piclist.do?companyId=${companyId}&productId=${n.productId}&navoid=${navoid }">图片管理</a> / 
									<c:if test="${!n.pinkForCmp}">
										<a href="javascript:setcmppink(${n.productId})">推荐</a> /
									</c:if> 
									<c:if test="${n.pinkForCmp}">
										<a href="javascript:delcmppink(${n.productId})">取消推荐</a> /
									</c:if> 
									<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_update.do?companyId=${companyId }&productId=${n.productId }&navoid=${navoid }">修改</a> / 
									<a href="javascript:delcmpproduct(${n.productId })">删除</a><br/>
									<a href="javascript:showorderflgwin(${n.productId },${n.orderflg })">设置序号</a>
									<c:if test="${o.openProductattrflg && n.sortId>0}">
									/ <a href="<%=path %>/epp/web/op/webadmin/cmpproduct_saveproductattr.do?companyId=${companyId }&productId=${n.productId }&navoid=${navoid }">设置属性</a>
									</c:if>
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
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId}&navoid=${navoid }</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpproduct(productId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpproduct_del.do?companyId=${companyId}&productId="+productId,
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
	var html='<form id="orderflgfrm" method="post" onsubmit="return setorderflg(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpproduct_setorderflg.do" target="hideframe">';
	html+='<div class="b">序号越大，排序越靠前</div>';
	html+='<hk:hide name="companyId" value="${companyId}"/>';
	html+='<input type="hidden" name="productId" value="'+oid+'"/>';
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
function setcmppink(productId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpproduct_setcmppink.do?companyId=${companyId}&productId="+productId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delcmppink(productId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpproduct_delcmppink.do?companyId=${companyId}&productId="+productId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>