<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">
		二级页面广告
		</div>
		<div class="mod_content">
			<div class="divrow">
			二级页面最多显示5个最新推荐的广告
			</div>
			<div>
				<c:if test="${!o.cmpFlgEnterprise}">
					<input value="添加广告" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId}&groupId=${groupId }')"/>
					<input value="添加广告代码" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId}&groupId=${groupId }&htmlflg=1')"/>
				</c:if>
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">所有广告</a>
			</div>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="ref" items="${list}">
					<ul class="datalist">
						<li>
							<span class="f_l" style="width:300px">
								<c:if test="${not empty ref.cmpAd.name}">
									${ref.cmpAd.name }<br/>
								</c:if>
								${ref.cmpAd.url }
							</span>
							<span class="f_l" style="width:260px;padding-left: 20px">
								<a href="javascript:readref(${ref.oid })">重新推荐</a> / 
								<a href="<%=path %>/epp/web/op/webadmin/cmpad_update.do?adid=${ref.cmpAd.adid }&companyId=${companyId}">修改</a> / 
								<a href="javascript:delcmpad(${ref.cmpAd.adid })">删除</a> / 
								<a href="javascript:deladref(${ref.oid })">取消推荐</a>
							</span>
							<div class="clr"></div>
						</li>
					</ul>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何广告</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpad_reflist.do?companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#ffffcc');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
function delcmpad(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpad_del.do?companyId=${companyId}&adid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function deladref(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpad_delcmpadref.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function readref(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpad_recmpadref.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>