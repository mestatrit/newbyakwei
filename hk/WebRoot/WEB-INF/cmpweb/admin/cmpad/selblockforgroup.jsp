<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">推荐${cmpAdGroup.name }中的广告到区块</div>
		<div class="mod_content">
			<p class="b">
			如果组中的广告数量超出模块规定数量，超出部分将不会被推荐到模块<br/>
			如果组中的广告已经选择了其他模块，这些广告将不会被推荐到当前模块
			</p>
			<c:if test="${fn:length(blocklist)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="block" items="${blocklist}">
						<c:if test="${block.cmpPageMod.modAd }">
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
				<div class="nodata">还没有添加任何广告区块</div>
			</c:if>
			<div>
			<a class="more2" href="${denc_return_url }">返回</a>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function selblock(blockId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpad_selblockforgroup.do?ch=1&companyId=${companyId}&groupId=${groupId}&blockId="+blockId,
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