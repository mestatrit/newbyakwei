<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">系统机器人用户
		<c:if test="${parent!=null}">${parent.name}</c:if>
		</div>
		<div class="mod_content">
			<div class="row">
				<form method="get" action="${ctx_path }/tb/admin/sysuser_create">
				昵称：<input type="text" class="text" name="nick" />
				<input type="submit" class="btn" value="创建"/>
				</form>
			</div>
			<c:if test="${fn:length(list)==0}">
			<div class="row b" align="center">本页没有数据</div>
			</c:if>
			<ul class="rowlist">
				<c:forEach var="sysuser" items="${list}">
					<li>
						<div class="f_l" style="width: 150px">
							<a href="/p/${sysuser.userid }">${sysuser.tbUser.nick }</a>
						</div>
						<div class="f_l" style="width: :150px">
							<a class="split-r" href="javascript:del(${sysuser.userid })">从系统用户中删除</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
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
var onsubmit=false;
function subfrm(frmid){
	if(onsubmit){
		return false;
	}
	onsubmit=true;
	showGlass(frmid);
	setHtml('info','');
	return true;
}
function createerr(e,msg,v){
	setHtml('info',msg);
	hideGlass();
	onsubmit=false;
}
function createok(){
	refreshurl();
}
function del(userid){
	if(window.confirm('确实要从系统用户中删除？')){
		doAjax('${ctx_path}/tb/admin/sysuser_del?userid=' + userid, function(data){
			refreshurl();
		});
	}
}

</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>