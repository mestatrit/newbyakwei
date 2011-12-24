<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${cmpProduct.name }
			<a class="more" href="<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</div>
			<div class="mod_content">
				<c:if test="${fn:length(list)<=30}">
				<div class="divrow">
					<input type="button" class="btn" value="上传产品图片" onclick="tourl('#upload')"/>
				</div>
				</c:if>
				<div class="divrow">最多只能上传30张图片</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="n" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:80px">
									<img src="${n.pic60 }"/>
								</div>
								<div class="f_l" style="width:150px;padding-left: 20px;">
									<c:if test="${cmpProduct.headPath != n.path}">
										<a href="javascript:sethead(${n.oid})">设为产品头图</a> / 
									</c:if>
									<a href="javascript:delcmpproductphoto(${n.oid })">删除</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何数据</div>
				</c:if>
				<c:if test="${fn:length(list)<=30}">
				<div style="width: 400px">
					<br/><a name="upload"></a>
					<h2>上传图片</h2>
					<form id="uppicfrm" onsubmit="return subuppicfrm(this.id)" method="post" enctype="multipart/form-data" action="<%=path %>/epp/web/op/webadmin/cmpproduct_uploadpic.do">
						<hk:hide name="productId" value="${productId}"/> 
						<hk:hide name="companyId" value="${companyId}"/> 
						<c:forEach var="b" begin="0" end="4">
							<div class="divrow">
								<input type="file" name="f${b }" size="50"/>
							</div>
						</c:forEach>
						<div align="right">
						<hk:submit value="上传" clazz="btn"/>
						</div>
					</form>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subuppicfrm(frmid){
	showGlass(frmid);
	return true;
}
function delcmpproductphoto(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpproduct_delpic.do?companyId=${companyId}&productId=${productId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function sethead(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpproduct_sethead.do?companyId=${companyId}&productId=${productId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>