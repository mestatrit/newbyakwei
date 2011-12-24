<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">文章推荐</div>
		<div class="mod_content">
			<c:if test="${cmpArticleBlock!=null}">
			<div class="divrow">
			已推荐到 <span class="b">${cmpPageBlock.name }</span> <a href="javascript:delarticleblock(${cmpArticleBlock.oid })">取消推荐</a>
			</div>
			</c:if>
			<c:if test="${fn:length(blocklist)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="block" items="${blocklist}">
						<c:if test="${block.cmpPageMod.modArticle && cmpArticleBlock.blockId!=block.blockId}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:300px">
									${block.name }
								</div>
								<div class="f_l" style="width:150px;padding-left: 20px">
									<a href="javascript:selblock(${block.blockId })">推荐到此区块</a> 
								</div>
								<div class="clr"></div>
							</div>
						</c:if>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(blocklist)==0}">
				<div class="nodata">还没有添加任何文章区块</div>
			</c:if>
			<div>
			<a class="more2" href="${denc_return_url }">返回</a>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delarticleblock(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delarticleblock.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function selblock(blockId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_selblock.do?ch=1&companyId=${companyId}&oid=${oid}&blockId="+blockId,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(data=="1"){
				refreshurl();
			}
			else{
				tourl('${denc_return_url }');
			}
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>