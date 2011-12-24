<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.frame.util.ServletUtil"%>
<%@page import="com.hk.svr.CompanyService"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.bean.Company"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
String path=request.getContextPath();
long companyId=ServletUtil.getLong(request,"companyId");
request.setAttribute("companyId",companyId);
CompanyService companyService=(CompanyService)HkUtil.getBean("companyService");
Company company=companyService.getCompany(companyId);
if(company==null){
	return;
}
request.setAttribute("company",company);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Google 地图 JavaScript API 示例: 简单地址解析</title>
    <script src="http://maps.google.com/maps?file=api&v=2&sensor=false&hl=zh-CN&key=<%=HkWebConfig.getGoogleApiKey() %>" type="text/javascript"></script>
    <script type="text/javascript">
    var marker_x=${company.markerX};
    var marker_y=${company.markerY};
    var map = null;
    var geocoder = null;
    function initialize() {
    	// 创建“微型”标记图标
    	var blueIcon = new GIcon(G_DEFAULT_ICON);
    	blueIcon.image = "<%=path %>/webst4/img/google_blank.png";
    	// 设置 GMarkerOptions 对象
    	markerOptions = { icon:blueIcon,draggable: false };
    	<c:if test="${company.markerX!=0}">
    		if (GBrowserIsCompatible()) {
    			map = new GMap2(document.getElementById("map_canvas"));
    			var center=new GLatLng(marker_x,marker_y);
    			last_point=center;
    			map.setCenter(center, 14);
    			map.addControl(new GSmallMapControl());
    			marker = new GMarker(center, markerOptions);
    			map.addOverlay(marker);
    		}
    	</c:if>
    	<c:if test="${company.markerX==0}">
    		if (GBrowserIsCompatible()) {
    			map = new GMap2(document.getElementById("map_canvas"));
    			geocoder = new GClientGeocoder();
    			findByAddr('${company.pcity.name} ${company.name}','${company.pcity.name} ${company.addr}',false);
    		}
    	</c:if>
    	}

    function findByAddr(address,address2,editmap) {
    	if (geocoder) {
    		geocoder.getLatLng(address,function(point){
    			if (!point) {
    				geocoder.getLatLng(address2,function(point){
    					if(!point){
    						setmapdata(new GLatLng(39.917, 116.397));
    					}
    					else{
    						setmapdata(point);
    					}
    					
    				});
    			}
    			else {
    				setmapdata(point);
    			}
    		});
    	}
    }

    function setmapdata(point){
    	map.setCenter(point, 13);
    	map.addControl(new GSmallMapControl());
    	marker = new GMarker(point,markerOptions);
    	map.addOverlay(marker);
    }
    </script>
    <style type="text/css">
body { font-family: Arial, Helvetica, sans-serif;font-size: 14px;color: #5A5858;line-height: 25px;width: auto;margin:0;padding:3px;}
    </style>
  </head>
  <body onload="initialize()" onunload="GUnload()">
      <div id="map_canvas" style="width: 590px; height: 590px"></div>
  </body>
</html>