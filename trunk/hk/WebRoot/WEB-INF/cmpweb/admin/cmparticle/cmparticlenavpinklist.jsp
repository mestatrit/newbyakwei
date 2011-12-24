<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${cmpNav.name } 文章推荐</div>
			<div class="mod_content">
				<div>
					<a class="split-r" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?navoid=${navoid }&companyId=${companyId}">文章管理</a>
				</div>
				<c:if test="${fn:length(list)>0}">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:330px">
								<div style="padding:0 20px 0 0">
									${n.cmpArticle.title }
								</div>
							</div>
							<div class="f_l" style="width:150px;padding-left: 10px">
								<a href="javascript:delcmparticlenavpink(${n.oid })">取消推荐</a> / 
								<a href="javascript:showorderflgwin(${n.oid },${n.orderflg })">设置序号</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有推荐任何文章</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmparticlenavpink(oid){
	if(window.confirm('确实要取消推荐？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmparticle_delcmparticlenavpink.do?companyId=${companyId}&ajax=1&oid="+oid,
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
	var html='<form id="orderflgfrm" method="post" onsubmit="return setorderflg(this.id)" action="<%=path %>/epp/web/op/webadmin/cmparticle_updatecmparticlenavpinkorderflg.do" target="hideframe">';
	html+='<div class="b">序号越大，排序越靠前</div>';
	html+='<hk:hide name="companyId" value="${companyId}"/>';
	html+='<input type="hidden" name="oid" value="'+oid+'"/>';
	html+='序号：<input type="text" name="orderflg" value="'+orderflg+'" class="text2"/>';
	html+='<hk:submit value="提交" clazz="btn"/>';
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