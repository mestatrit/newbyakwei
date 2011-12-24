<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">修改建议
</c:set><c:set var="html_head_value">
</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 600px;">
<div class="mod">
<div class="mod_title">修改建议</div>
<div class="mod_content">
我的建议：<span class="ruo">最多3000字</span><br />
<form id="answerfrm" onsubmit="return subanswerfrm(this.id)" method="post" action="${ctx_path }/tb/ask_prvupdateanswer" target="hideframe">
	<hk:hide name="ansid" value="${ansid}"/>
	<hk:hide name="ch" value="1"/>
	<div class="row">
		<hk:textarea oid="_content" name="content" value="${tbAnswer.content}" style="width: 560px; height: 200px;"/>
		<div class="infowarn" id="info"></div>
	</div>
	<c:if test="${not empty tbAnswer.resolve_content}">
	<p class="b">建议方案：</p>
	<c:forEach var="item" items="${tbAnswer.tbAnswerItemList}">
		<div class="picbox">
		<a target="_blank" href="${ctx_path }/tb/ask_item?ansid=${tbAnswer.ansid}&num_iid=${item.num_iid}"><img src="${item.pic_url }_60x60.jpg"/></a>
		<p>￥${item.price }</p>
		</div>
	</c:forEach>
	<div class="clr"></div>
	</c:if>
	<a id="addurlinput_a" class="split-r" href="javascript:addurlinput()">添加链接</a>（支持淘宝商品链接）<br/>
	<div class="row" align="right" style="width:560px;">
		<input type="submit" class="btn split-r" value="保存" />
		<a href="${denc_return_url }">返回</a>
	</div>
</form>
</div>
</div>
</div>
<script type="text/javascript">
var submited=false;
var len=${fn:length(tbAnswer.tbAnswerItemList) };
$(document).ready(function(){
	$('#_content').bind('keyup',function(event){
		keysubanswerfrm(event);
	});
});
function keysubanswerfrm(event){
	if ((event.ctrlKey) && (event.keyCode == 13)) {
		if (subanswerfrm('answerfrm')) {
			getObj('answerfrm').submit();
		}
	}
}

function subanswerfrm(frmid){
	if(submited){
		return false;
	}
	submited=true;
	showGlass(frmid);
	setHtml('info', '');
	return true;
}

function answererr(e, msg, v){
	submited=false;
	setHtml('info', msg);
	hideGlass();
}

function answerok(e, msg, v){
	tourl('${denc_return_url }');
}

function addurlinput(){
	if(len>=10){
		$('#addurlinput_a').css('display','none');
		return;
	}
	var id = createObjId('row_url');
	var html = '<div id="' + id + '" class="row">商品链接：<hk:text name="item_url" clazz="text" style="width:400px"/> <a href="javascript:removeurlinput(\'' + id + '\')">删除</a></div>';
	insertObjBefore(html, "addurlinput_a");
	len++;
}

function removeurlinput(id){
	delObj(id);
	$('#addurlinput_a').css('display','block');
	len--;
}

function delansweritem(num_iid){
	if (window.confirm('确实要删除此商品？')) {
		doAjax('${ctx_path}/tb/ask_prvdelansweritem?ansid=${ansid}&num_iid=' + num_iid, function(data){
			delObj('li_'+num_iid);
			$('#addurlinput_a').css('display','block');
			len--;
		});
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>