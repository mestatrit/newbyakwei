<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">商品列表
		</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)==0}">
				<div class="row b" align="center">本页没有数据</div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="item" items="${list}">
					<li>
						<div class="f_l" style="width: 80px;margin-right: 10px;"><img src="${item.pic_url }_sum.jpg"/></div>
						<div class="f_l" style="width: 400px;margin-right: 20px;"><a href="${ctx_path }/tb/item?itemid=${item.itemid}">${item.title }</a></div>
						<div class="f_l" style="width: 150px">
						<c:if test="${!item.homeCmd}">
							<a id="cmdop_${item.itemid }" href="javascript:cmditem(${item.itemid })">推荐到首页</a> / 
						</c:if>
						<c:if test="${item.homeCmd}">已推荐 / </c:if>
						<a id="delop_${item.itemid }" href="javascript:delitem(${item.itemid })">删除</a><br/>
							<span id="cmdtip_${item.itemid }"></span>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
			</c:if>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/admin/item?title=${enc_title}</c:set>
			<jsp:include page="../../webtb/inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function delitem(itemid){
	if(window.confirm('确实要删除？')){
		addGlass('delop_'+itemid,true);
		doAjax('${ctx_path}/tb/admin/item_delitem?itemid='+itemid,function(data){
			refreshurl();
		});
	}
}
function cmditem(itemid){
	var glass_id=addGlass('cmdop_'+itemid,true);
	doAjax('${ctx_path}/tb/admin/item_cmditem?itemid='+itemid,function(data){
		setHtml('cmdtip_'+itemid,"已推荐");
		$('#cmdop_'+itemid).css('display','none');
		removeGlass(glass_id);
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>