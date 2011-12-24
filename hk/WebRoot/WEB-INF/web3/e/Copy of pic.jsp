<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/photo.css" /></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body{background:#F0F0F0 ;}
</style>
	<div class="mod">
		<div class="phototop">
			<div class="f_l">
				<h1 id="photoname" class="title"></h1>
			</div>
			<div class="f_r" style="padding-right: 20px">
				<h2><a href="<%=path %>/cmp.do?companyId=${companyId }">${company.name}</a></h2>
			</div>
			<div class="clr"></div>
		</div>
		<div class="mod-8 mod-9">
			<div class="bg">
				<div class="rd">
					<div class="l"></div>
					<div class="mid"></div>
					<div class="r"></div>
					<div class="clr"></div>
				</div>
			</div>
			<div class="cont">
				<div id="photocon" class="photocon">
				</div>
			</div>
			<div class="mod_btm">
				<div class="mod_btm-bottom-l"></div>
				<div id="smallphotocon" class="mod_btm-bottom-mid">
				</div>
				<div class="mod_btm-bottom-r"></div>
				<div class="clr"></div>
			</div>
		</div>
	</div>
<script type="text/javascript">
var img=new Array();
<c:forEach var="p" items="${list}" varStatus="idx">
img[${idx.index }]=new Array(${p.photoId },'${p.pic60 }','${p.pic240 }','${p.pic640 }','${p.name}');
</c:forEach>
function initimg(){
	var s="";
	for(var i=0;i<img.length;i++){
		s+='<a class="smallphoto" href="javascript:chgimg('+i+')"><img src="'+img[i][1]+'" /></a>';
	}
	setHtml('smallphotocon',s);
}
function chgimg(idx){
	setHtml('photocon','<table><tr><td><img id="picshow" src="'+img[idx][3]+'" onerror="whenbigerror(this,'+idx+')"/></td></tr></table>');
	//getObj('photocon').style.cssText="background: url("+img[idx][2]+") center no-repeat;";
	setHtml('photoname',img[idx][4]);
}
function whenbigerror(obj,idx){
	obj.src=img[idx][2];
	obj.onerror="";
}
initimg();
chgimg(0);
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>