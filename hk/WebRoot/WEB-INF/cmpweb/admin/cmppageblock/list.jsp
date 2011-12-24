<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">首页区块设置</div>
		<div class="mod_content">
			<div>
				<input value="添加页面区块" type="button" class="btn" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmppageblock_create.do?companyId=${companyId}&pageflg=${pageflg }')"/>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:260px">
								${n.name }
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_setorderflg.do?companyId=${companyId}&pageflg=${pageflg}&pageModId=${n.pageModId}">设置同类模块顺序</a> / 
								<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_update.do?blockId=${n.blockId }&companyId=${companyId}&pageflg=${pageflg}">修改</a> /
								<a href="javascript:delblock(${n.blockId })">删除</a>
								<c:if test="${n.cmpPageMod.modAd || n.cmpPageMod.modArticle}">
								 / <a href="<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${n.blockId}">维护</a>
								</c:if>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何区块</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId}&pageflg=${pageflg }</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
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
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>