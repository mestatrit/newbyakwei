<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">页面区块顺序设置
		<a class="more" href="<%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId }&pageflg=${pageflg }&pageModId=${pageModId}">返回</a>
		</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:260px">
								${n.name }
							</div>
							<div class="f_l" style="width:160px;padding-left: 20px">
								<a href="javascript:showorderflgwin(${n.blockId },${n.orderflg })">设置序号</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何区块</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delblock(blockId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmppageblock_del.do?companyId=${companyId}&blockId="+blockId,
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
	var html='<form id="orderflgfrm" method="post" onsubmit="return setorderflg(this.id)" action="<%=path %>/epp/web/op/webadmin/cmppageblock_setorderflg.do" target="hideframe">';
	html+='<div class="b">序号越大，排序越靠前</div>';
	html+='<hk:hide name="companyId" value="${companyId}"/>';
	html+='<hk:hide name="ch" value="1"/>';
	html+='<input type="hidden" name="blockId" value="'+oid+'"/>';
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