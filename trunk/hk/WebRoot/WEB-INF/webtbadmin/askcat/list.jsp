<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">问答分类
		<c:if test="${parent!=null}">${parent.name}</c:if>
		</div>
		<div class="mod_content">
			<div class="row">
			<c:if test="${parent!=null}">
			<a href="${ctx_path }/tb/admin/askcat">查看所有</a>
			</c:if>
			<a href="javascript:create()">创建分类</a>
			</div>
			<div class="row">
				<form method="get" action="${ctx_path }/tb/admin/askcat">
				<hk:hide name="parent_cid" value="${parent_cid}"/>
				名称：<input type="text" class="text" name="name" value="${name }"/>
				<input type="submit" class="btn" value="搜索"/>
				</form>
			</div>
			<c:if test="${fn:length(list)==0}">
			<div class="row b" align="center">本页没有数据</div>
			</c:if>
			<ul class="rowlist">
				<c:forEach var="cat" items="${list}">
					<li>
						<div class="f_l" style="width: 150px">
						<a href="${ctx_path }/tb/admin/askcat?parent_cid=${cat.cid}">${cat.name }</a>
						</div>
						<div class="f_l" style="width: 100px">
							<a href="${ctx_path }/tb/admin/askcat?parent_cid=${cat.cid}">查看子分类</a>
						</div>
						<div class="f_l" style="width: :150px">
						<a class="split-r" href="javascript:update(${cat.cid })">修改</a>
						<a class="split-r" href="javascript:del(${cat.cid })">删除</a>
						</div>
						<div class="f_l" style="width: :150px">
							<form id="orderfrm${cat.cid }" method="post" onsubmit="return suborderfrm(this.id)" action="${ctx_path }/tb/admin/ask_updateordernum">
							<hk:hide name="cid" value="${cat.cid}"/>
							序号：<input type="text" name="order_num" value="${cat.order_num }" class="text" style="width:80px;"/>
							<a href="javascript:void(0)" onclick="javascript:suborderfrm('orderfrm${cat.cid }')">保存</a>
							</form>
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
function update(cid){
	tourl('${ctx_path}/tb/admin/askcat_update?cid=' + cid + "&return_url=" + encodeLocalURL());
}

function del(cid){
	if(window.confirm('确实要删除？')){
		doAjax('${ctx_path}/tb/admin/askcat_del?cid=' + cid, function(data){
			refreshurl();
		});
	}
}
function create(){
	tourl('${ctx_path }/tb/admin/askcat_create?parent_cid=${parent_cid}' + "&return_url=" + encodeLocalURL());
}

function suborderfrm(frmid){
	updateorder(frmid, getObj(frmid).cid.value, getObj(frmid).order_num.value);
	return false;
}

function updateorder(frmid, cid, order_num){
	addGlass(frmid);
	doAjax('${ctx_path}/tb/admin/askcat_updateordernum?cid=' + cid + "&order_num=" + order_num, function(data){
		refreshurl();
	});
}

</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>