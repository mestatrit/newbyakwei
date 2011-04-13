<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">分类管理</div>
	<div class="mod_content">
		<div>
		<input type="button" class="btn" value="创建分类" onclick="tocreate()"/>
		</div>
		<ul class="rowlist">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">名称</div>
				<div class="f_l" style="width: 80px;margin-right: 20px">顺序</div>
				<div class="clr"></div>
			</li>
			<c:forEach var="cat" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">
					<hk:value value="${cat.name }" onerow="true"/>
				</div>
				<div class="f_l" style="width: 80px;margin-right: 20px">
					<c:if test="${idx.index>0}">
						<a href="toup()">上移</a>
					</c:if>&nbsp;
				</div>
				<div class="f_l">
					<a href="javascript:toupdate(${cat.catid })" class="split-r" id="op_update_${cat.catid }">修改</a>
					<a href="javascript:opdel(${cat.catid })" class="split-r" id="op_delete_${cat.catid }">删除</a>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0)}">
				<li><div class="nodata">本页没有数据</div></li>
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function tocreate(){
	tourl('${appctx_path}/mgr/cat_create.do');
}
function toupdate(catid){
	tourl('${appctx_path}/mgr/cat_update.do?catid='+catid);
}
function opdel(cid){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete_'+cid,false);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/cat_delete?catid="+cid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			},
			error:function(data){
				removeGlass(glassid_op);
				alert('服务器出错，请刷新页面稍后继续操作');
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>