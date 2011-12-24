<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">修改评论
</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 600px;">
	修改评论：<span class="ruo">最多500字</span> <br />
	<form id="cmtfrm" onsubmit="return subcmtfrm(this.id)" method="post" action="${ctx_path }/tb/itemcmt_prvupdate" target="hideframe">
		<hk:hide name="ch" value="1"/>
		<hk:hide name="cmtid" value="${cmtid}"/>
		<div class="row">
			<hk:textarea oid="_content" value="${tbItemCmt.content}" name="content" style="width: 560px; height: 120px;"/>
			<div class="infowarn" id="info"></div>
		</div>
		<div class="row" align="right" style="width:560px;">
			<input type="submit" class="btn split-r" value="保存点评" />
			<a href="${denc_return_url }">返回</a>
		</div>
	</form>
</div>
<script type="text/javascript">
var submited=false;
$(document).ready(function(){
	$('#_content').bind('keyup',function(event){
		if((event.ctrlKey)&&(event.keyCode==13)){
			if(subcmtfrm('cmtfrm')){
				getObj('cmtfrm').submit();
			}
		}
	});
});
function subcmtfrm(frmid){
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
	tourl('${denc_return_url}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>