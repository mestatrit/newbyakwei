<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpInfo"%>
<%@page import="com.hk.svr.CmpInfoService"%>
<%@page import="com.hk.frame.util.DesUtil"%>
<%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.Company"%><%@page import="com.hk.svr.CompanyService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="com.hk.frame.util.ServletUtil"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%String path=request.getContextPath();
long oid=ServletUtil.getLong(request,"oid");
long companyId=ServletUtil.getLong(request,"companyId");
String addr=ServletUtil.getString(request,"addr");
double marker_x=ServletUtil.getDouble(request,"marker_x");
double marker_y=ServletUtil.getDouble(request,"marker_y");
request.setAttribute("marker_x",marker_x);
request.setAttribute("marker_y",marker_y);
String referer=request.getHeader("referer");
if(referer==null || referer.indexOf("/epp/web/op/webadmin/cmpsellnet")==-1){
	return;
}
String v=referer.substring(6);
int idx=referer.indexOf("/");
String domain=referer.substring(0,idx);
CmpInfoService cmpInfoService=(CmpInfoService)HkUtil.getBean("cmpInfoService");
CmpInfo cmpInfo=cmpInfoService.getCmpInfo(companyId);
if(cmpInfo==null|| cmpInfo.getCompanyId()!=companyId){
	return;
}
String sckey=ServletUtil.getString(request,"sckey");
if(sckey==null || sckey.length()==0){
	return;
}
String key=DesUtil.decode("websitegooglemaphkkey",sckey);
request.setAttribute("key",key);
String op=request.getParameter("op");
%>
<fmt:formatDate var="currentdate" pattern="yyyy-MM-dd HH" value="<%=new Date() %>"/>
<c:if test="${key==currentdate}">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<title>Google 地图 JavaScript API 示例: 简单地址解析</title>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/pub.js"></script>
<script src="http://maps.google.com/maps?file=api&v=2&sensor=false&hl=zh-CN&key=<%=HkWebConfig.getGoogleApiKey() %>" type="text/javascript"></script>
<script src="http://www.google.com/uds/api?file=uds.js&v=1.0" type="text/javascript"></script>    
<script src="http://www.google.com/uds/solutions/localsearch/gmlocalsearch.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/gmlocalsearch.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/gsearch.css" />
<style type="text/css">
body { font-family: Arial, Helvetica, sans-serif;font-size: 14px;color: #5A5858;line-height: 25px;width: auto;margin:0;padding:3px;}

div.mapstatus{
	border: 1px solid #CCCCCC;
	color: #ffffff;
	font-size: 14px;
	padding: 3px 6px;
	position: absolute;
	right: 5px;
	bottom: 0px;
	text-shadow: 1px 1px #000000;
	background-color:#757575;
	z-index: 1000;
	-moz-border-radius:5px 5px 5px 5px;
}
div.mapstatus a,
div.mapstatus2 a{
	color: #ffffff;
}
div.statusinfo{
	border: 1px solid #CCCCCC;
	color: #ffffff;
	font-size: 12px;
	padding: 3px 6px;
	position: absolute;
	right: 5px;
	top: 25px;
	text-shadow: 1px 1px #000000;
	background-color:rgba(0, 0, 0, 0.5);
	z-index: 1000;
	-moz-border-radius:5px 5px 5px 5px;
}
</style>
<script type="text/javascript">
var companyId=<%=companyId%>;
var addr="<%=addr%>";
var marker_x=<%=marker_x%>;
var marker_y=<%=marker_y%>;
var map = null;
var marker=null;
var geocoder = null;
var last_point=null;
function initialize() {
// 创建“微型”标记图标
var blueIcon = new GIcon(G_DEFAULT_ICON);
blueIcon.image = "<%=path %>/webst4/img/google_blank.png";
// 设置 GMarkerOptions 对象
markerOptions = { icon:blueIcon,draggable: true };
<c:if test="${marker_x!=0}">
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map_canvas"));
		var center=new GLatLng(marker_x,marker_y);
		last_point=center;
		map.setCenter(center, 14);
		map.addControl(new GSmallMapControl());
		marker = new GMarker(center, markerOptions);
		map.addOverlay(marker);
		map.addControl(new google.maps.LocalSearch(), new GControlPosition(G_ANCHOR_BOTTOM_RIGHT, new GSize(10,20)));
		GSearch.setOnLoadCallback(initialize);
		GEvent.addListener(marker, "dragstart", function() {
			map.closeInfoWindow();
		});
		GEvent.addListener(marker, "dragend", function() {
			var current =marker.getLatLng();
			last_point=current;
			updateCompanyLocation(current);
		});
	}
</c:if>
<c:if test="${marker_x==0}">
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map_canvas"));
		map.addControl(new google.maps.LocalSearch(), new GControlPosition(G_ANCHOR_BOTTOM_RIGHT, new GSize(10,20)));
		GSearch.setOnLoadCallback(initialize);
		geocoder = new GClientGeocoder();
		findByAddr('<%=addr%>',false);
	}
</c:if>
}

function hidemapstatus_info(){
	setHtml('mapstatus_info','');
}

function findByAddr(address,editmap) {
	if (geocoder) {
		geocoder.getLatLng(address,function(point){
			if (!point) {
				//alert('no');
				setmapdata(new GLatLng(39.917, 116.397));
			}
			else {
				//alert('yes,'+address);
				setmapdata(point,editmap);
			}
		});
	}
}

function setmapdata(point,editmap){
	last_point=point;
	map.setCenter(point, 13);
	map.addControl(new GSmallMapControl());
	if(marker!=null){
		marker.remove();
	}
	marker = new GMarker(point,markerOptions);
	map.addOverlay(marker);
	GEvent.addListener(marker, "dragstart", function() {
		map.closeInfoWindow();
	});
	GEvent.addListener(marker, "dragend", function() {
		var current =marker.getLatLng();
		last_point=current;
		updateCompanyLocation(current);
	});
}
function findMaker(){
	if(marker!=null){
		marker.remove();
	}
	marker = new GMarker(map.getCenter(),markerOptions);
	map.addOverlay(marker);
	GEvent.addListener(marker, "dragstart", function() {
		map.closeInfoWindow();
	});
	GEvent.addListener(marker, "dragend", function() {
		var current =marker.getLatLng();
		last_point=current;
		updateCompanyLocation(current);
	});
}
function setmapstatus_info(msg){
	setHtml('mapstatus_info',msg);
}
function updateCompanyLocation(o){
	setHtml('mapstatus_info','保存位置 ... ...');
	marker_x=o.lat();
	marker_y=o.lng();
	$.ajax({
		type:"POST",
		url:"<%=path%>/pub/cmpfunc_savecmpsellnetpos.do?oid=<%=oid%>&marker_x="+marker_x+"&marker_y="+marker_y+"&op=<%=op%>",
		cache:false,
    	dataType:"html",
		success:function(data){
			var html='';
			setHtml('mapstatus_info','保存成功');
			delay("hidemapstatus_info()",2000);
		}
	});
}
</script>
	</head>
	<body onload="initialize()" onunload="GUnload()">
		<div style="position: relative; width: 600px; height: 500px">
			<div class="mapstatus" style="top:5px;bottom:auto;">
				拖动蓝色图钉来修改位置。找不到图钉？<a href="javascript:findMaker()" style="color:#2398C9;">让它过来！</a>
				<span id="mapstatus_info"></span>
			</div>
			<div id="map_canvas" style="width: 600px; height: 500px">
		</div>
		</div>
	</body>
</html>
</c:if>