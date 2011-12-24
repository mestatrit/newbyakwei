<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.etbhk.util.TbHkViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%TbHkViewUtil.loadZoneInfo(request);
%>
省<hk:select name="provinceId" checkedvalue="${sel_provinceId}" clazz="split-r" onchange="initcity(this.value)">
<hk:option value="0" data="请选择"/>
<c:forEach var="p" items="${provincelist}">
	<hk:option value="${p.provinceId}" data="${p.province}"/>
</c:forEach>
</hk:select>
市<hk:select oid="id_cityId" name="cityid" checkedvalue="${sel_cityId}">
<hk:option value="0" data="请选择"/>
</hk:select>
<script type="text/javascript">
var city=new Array();
<c:forEach var="c" items="${citylist}" varStatus="idx">
city[${idx.index}]=new Array(${c.cityId },${c.provinceId},'${c.city }');
</c:forEach>
function initcity(pid){
	var oo=getObj("id_cityId");
	oo.options.length=0;
	oo.options[0]=new Option("请选择",0);
	for(var i=0;i<city.length;i++){
		if(city[i][1]==pid){
			oo.options[oo.options.length]=new Option(city[i][2],city[i][0]);
		}
	}
	if(oo.options.length==2){
		oo.options[0]=new Option(oo.options[1].text,oo.options[1].value);
		oo.options.length=1;
	}
}
</script>