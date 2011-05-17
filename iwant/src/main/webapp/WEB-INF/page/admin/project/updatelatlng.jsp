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
	function initialize() {
		geocoder = new google.maps.Geocoder();
		var latlng = new google.maps.LatLng(-34.397, 150.644);
		var myOptions = {
		  zoom: 8,
		  center: latlng,
		  mapTypeId: google.maps.MapTypeId.ROADMAP
		}
		map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	}
	
	function codeAddress() {
		var address = document.getElementById("address").value;
		geocoder.geocode( { 'address': address}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
			  map.setCenter(results[0].geometry.location);
			  var marker = new google.maps.Marker({
			      map: map, 
			      position: results[0].geometry.location
			  });
			} 
			else {
				alert("Geocode was not successful for the following reason: " + status);
			}
		});
	}
	
</script>
</c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">地图设置</div>
	<div class="mod_content">
		<div>
			<form method="post" action="${appctx_path }/mgr/project_updateLatlng.do">
				<input type="hidden" name="ch" value="1"/>
				<input type="hidden" name="projectid" value="${projectid }"/>
				<input id="pro_lat" type="hidden" name="lat" value="${project.lat }"/>
				<input id="pro_lng" type="hidden" name="lat" value="${project.lng }"/>
			</form>
		</div>
		<div id="map_canvas" style="height:90%;top:30px">
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>