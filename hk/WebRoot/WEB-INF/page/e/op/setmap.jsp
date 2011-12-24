<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="地图 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<c:set var="otherBodyParam" scope="request">onload="initialize()" onunload="GUnload()"</c:set>
    <script src="http://ditu.google.com/maps?file=api&v=2&sensor=false&hl=zh-CN&key=<%=HkWebConfig.getGoogleApiKey() %>" type="text/javascript"></script>
	<script type="text/javascript">
		var marker_x=${o.markerX};
		var marker_y=${o.markerY};
		var map = null;
		var marker=null;
		var geocoder = null;
		var flg=0;
		function initialize() {
		  if (GBrowserIsCompatible()) {
		    map = new GMap2(document.getElementById("map_canvas"));
		    var center=null;
		    if(marker_x>0 && marker_y>0){
		    	center= new GLatLng(marker_x, marker_y);
		    }
		    else{
			   center= new GLatLng(39.917, 116.397);
		    }
		    map.setCenter(center, 13);
		    map.addControl(new GSmallMapControl());
		    marker = new GMarker(center, {draggable: true});
	    	GEvent.addListener(marker, "dragstart", function() {
				map.closeInfoWindow();
				});
			GEvent.addListener(marker, "dragend", function() {
				var current =marker.getLatLng();
				initInfo(current);
				});
			map.addOverlay(marker);
		    geocoder = new GClientGeocoder();
		    if(marker_x==0 && marker_y==0){
			    showAddress2(document.getElementById('cname').value);
		    }
		  }
		}
		
		function showAddress2(address) {
			if (geocoder) {
				geocoder.getLatLng(
					address,
					function(point) {
						if (!point) {
							//
						}
						else {
							map.setCenter(point, 13);
							initInfo(point);
							if(marker!=null){
								marker.remove();
							}
							marker = new GMarker(point,{draggable: true});
							map.addOverlay(marker);
							marker.openInfoWindowHtml(address);
							GEvent.addListener(marker, "dragstart", function() {
								map.closeInfoWindow();
								});
							GEvent.addListener(marker, "dragend", function() {
								var current =marker.getLatLng();
								initInfo(current);
								});
						}
					}
				);
			}
		}
		function showAddress(address) {
			if (geocoder) {
				geocoder.getLatLng(
					address,
					function(point) {
						if (!point) {
							alert("不能解析: " + address);
						}
						else {
							map.setCenter(point, 13);
							initInfo(point);
							if(marker!=null){
								marker.remove();
							}
							marker = new GMarker(point,{draggable: true});
							map.addOverlay(marker);
							marker.openInfoWindowHtml(address);
							GEvent.addListener(marker, "dragstart", function() {
								map.closeInfoWindow();
								});
							GEvent.addListener(marker, "dragend", function() {
								var current =marker.getLatLng();
								initInfo(current);
								});
						}
					}
				);
			}
		}
		function getObj(id){
			return document.getElementById(id);
		}
		function initInfo(o){
			marker_x=o.lat();
			marker_y=o.lng();
			getObj("id_marker_x").value=marker_x;
			getObj("id_marker_y").value=marker_y;
		}
		function checkMarkerInfo(){
			if(marker_x==0 && marker_y==0){
				alert("地图还未标注");
				return false;
			}
			return true;
		}
	</script>
	<div class="hang odd">
		<hk:form onsubmit="showAddress(document.getElementById('cname').value);return false;" action="#">
			${o.name }<br/>
			地址:${o.addr }
			<div class="hang odd">
				<hk:hide name="companyId" value="${companyId }"/>
				<c:if test="${o.markerX==0 && o.markerY==0}">
					<input id="cname" type="text" size="60" name="address" value="${o.addr }" maxlength="30" />
				</c:if>
				<c:if test="${o.markerX!=0 || o.markerY!=0}">
					<input id="cname" type="text" size="60" name="address" maxlength="30" />
				</c:if>
				<input type="submit" value="查询" class="btn"/>
			 </div>
    	</hk:form>
		<hk:form onsubmit="return checkMarkerInfo()" action="/e/op/op_setmap.do" needreturnurl="true">
			<div class="hang odd">
				<hk:hide name="companyId" value="${companyId }"/>
				<input type="hidden" name="marker_x" id="id_marker_x" value="${o.markerX}"/>
				<input type="hidden" name="marker_y" id="id_marker_y" value="${o.markerY}"/>
				<input type="submit" value="提交地图标注"/>
			 </div>
	      <div id="map_canvas" style="width: 500px; height: 300px"></div>
    	</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>