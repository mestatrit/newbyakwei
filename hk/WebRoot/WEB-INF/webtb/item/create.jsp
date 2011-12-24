<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">添加商品
</c:set><c:set var="html_body_content" scope="request">
<div class="row" id="iptdiv" style="height: 100%">
	请从浏览器中复制您看到的淘宝商品链接到下面的输入框中：
	<br/>
	<form method="post" onsubmit="loadtaobaoitem();return false;" action="#">
	<input id="_url" type="text" class="text" nam="taobao_item_url" maxlength="300" style="width:550px"/><input type="button" class="btn" value="提交商品链接" onclick="loadtaobaoitem()"/>
	</form>
</div>
<div class="row" id="item_data">

</div>
<link rel="stylesheet" type="text/css" href="${ctx_path }/webtb/css/starobj.css" />
<script type="text/javascript" src="${ctx_path }/webtb/js/starobj.js"></script>
<script type="text/javascript">
var submited=false;
var star=null;
function loadtaobaoitem(){
	showGlass('iptdiv');
	setHtml('item_data','');
	star=null;
	$('#cmt_area').css('display','none');
	doAjax("${ctx_path}/tb/item_loadtaobaoitem?taobao_item_url="+encodeURL(getObj('_url').value),function(data){
		setHtml('item_data',data);
		$('#cmt_area').css('display','block');
		if(getObj('starscore')!=null){
			star=new StarObj('starscore',-1,function(star_idx){
				getObj('_score').value=star_idx+1;
			});
			star.show();
		}
		hideGlass('iptdiv');
	});
}
function keysubcmt(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subcmt('frm')){
			getObj('frm').submit();
		}
	}
}
function subcmt(frmid){
	if(submited){
		return false;
	}
	submited=true;
	showGlass(frmid);
	setHtml('info','');
	return true;
}
function cmterr(e,msg,v){
	hideGlass();
	setHtml('info',msg);
	submited=false;
}
function cmtok(e,msg,v){
	tourl('${ctx_path}/tb/item?itemid='+v);
}
function showscore(){
	$('#score_area').css('display','block');
}
function hidescore(){
	$('#score_area').css('display','none');
	getObj('_score').value=0;
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>