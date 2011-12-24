<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">首页推荐商品列表
		</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)==0}">
				<div class="row b" align="center">本页没有数据</div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="cmd" items="${list}">
					<li>
						<div class="f_l" style="width: 80px;margin-right: 10px;"><img src="${cmd.tbItem.pic_url }_sum.jpg"/></div>
						<div class="f_l" style="width: 400px;margin-right: 20px;"><a href="${ctx_path }/tb/item?itemid=${cmd.tbItem.itemid}">${cmd.tbItem.title }</a></div>
						<div class="f_l" style="width: 150px">
						<a id="cmdop_${cmd.oid }" href="javascript:delcmd(${cmd.oid })">取消首页推荐</a> / 
						<a id="cmdreop_${cmd.oid }" href="javascript:recmd(${cmd.oid })">重新推荐</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
			</c:if>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/admin/item_cmdlist</c:set>
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
function delcmd(oid){
	if(window.confirm('确实要取消推荐？')){
		addGlass('delop_'+oid,true);
		doAjax('${ctx_path}/tb/admin/item_delcmd?oid='+oid,function(data){
			refreshurl();
		});
	}
}
function recmd(oid){
	addGlass('delreop_'+oid,true);
	doAjax('${ctx_path}/tb/admin/item_recmditem?oid='+oid,function(data){
		refreshurl();
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>