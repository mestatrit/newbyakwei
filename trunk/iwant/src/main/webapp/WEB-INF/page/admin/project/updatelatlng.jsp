<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<c:set var="html_head_title" scope="request"><hk:value value="${project.name}"/></c:set>
<c:set var="html_head_value" scope="request">
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
var geocoder;
var map;
var marker=null;
var centerLat=-34.397;
var centerLng=150.644;
var glassid=null;
var submited=false;
function initialize() {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(centerLat, centerLng);
	var myOptions = {
	  zoom: 10,
	  center: latlng,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	google.maps.event.addListener(map, 'dragend', function() {
		centerLat=map.getCenter().lat();
		centerLng=map.getCenter().lng();
	});
}

function showMarker(){
	if(marker!=null){
		marker.setMap(null);
		marker=null;
	}
	marker= new google.maps.Marker({
			    map: map,
			    draggable:true,
			    title: document.getElementById("search_addr").value,
			    position: map.getCenter()
			});
}

function searchlatlng() {
	var address = document.getElementById("search_addr").value;
	geocoder.geocode( { 'address': address}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			map.setCenter(results[0].geometry.location);
			if(marker!=null){
				marker
				marker.setMap(null);
				marker=null;
			}
			marker= new google.maps.Marker({
			    map: map,
			    draggable:true,
			    title: address,
			    position: results[0].geometry.location
			});
			google.maps.event.addListener(marker,'dragend',function(event){
				centerLat=event.latLng.lat();
				centerLng=event.latLng.lng();
			});
		} 
		else {
			alert("Geocode 查询失败: " + status);
		}
	});
}

function subLatlng(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('err_latlng','');
	return true;
}

function onupdateok(err,err_msg,v){
	tourl('${appctx_path}/mgr');
}

$(document).ready(function(){
	initialize();
});
</script>
</c:set>
<c:set scope="request" var="mgr_body_content">
<style type="text/css">
.marker{
	position: absolute;
	background-color: #000000;
	width: 200px;
	height: 30px;
	line-height: 30px;
	z-index: 999;
	top:20px;
	left:200px;
	text-align: center;
}
.marker a{
	display: block;
	color:#ffffff;
	font-weight: bold;
	font-size: 20px;
}
</style>
<div class="mod">
	<div class="mod_title">地图设置
	<a class="more" href="${appctx_path }/mgr/project_back.do">返回</a>
	</div>
	<div class="mod_content">
		<div>
			<div class="f_l">
				<form method="post" onsubmit="searchlatlng();return false;" action="#">
				<c:set var="addr"><hk:value value="${project.addr}" onerow="true"/></c:set>
					地址：<input id="search_addr" name="addr" class="text" value="${addr}"/>
					<input type="submit" value="查询" class="btn"/>
				</form>
			</div>
			<div class="f_l">
				<form id="latlngfrm" method="post" onsubmit="return subLatlng(this.id)" action="${appctx_path }/mgr/project_updateLatlng.do" target="hideframe">
					<input type="hidden" name="ch" value="1"/>
					<input type="hidden" name="projectid" value="${projectid }"/>
					<input id="pro_lat" type="hidden" name="lat" value="${project.lat }"/>
					<input id="pro_lng" type="hidden" name="lat" value="${project.lng }"/>
					<input type="submit" value="保存当前位置"/>
				</form>
			</div>
			<div class="clr"></div>
		</div>
		<div style="position: relative;">
			<div class="marker">
				<a class="ma" href="javascript:showMarker()">显示标记</a>
			</div>
			<div id="map_canvas" style="height:500px;top:10px;">
			</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>