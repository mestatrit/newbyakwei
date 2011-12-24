<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.svr.ZoneService"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.Province"%>
<%@page import="com.hk.bean.Country"%>
<%@page import="com.hk.bean.City"%>
<%@page import="com.hk.svr.pub.ZoneUtil"%>
<%@page import="com.hk.frame.util.HkUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
List<Country> countrylist=ZoneUtil.getCountryList();
List<Province> provincelist =ZoneUtil.getProvinceList();
List<City> citylist=ZoneUtil.getCityList();
request.setAttribute("provincelist", provincelist);
request.setAttribute("countrylist", countrylist);
request.setAttribute("citylist", citylist);
%>
<c:set var="html_title" scope="request"><hk:data key="view2.selzone"/></c:set>
<c:set var="html_body_content" scope="request">
<style type="text/css">
select{
	padding: 5px;
	font-size: 14px;
}
option{
	padding: 2px;
}
</style>
<div class="hcenter" style="width: 600px; text-align: center;">
<h1><hk:data key="view2.selzone"/></h1>
<br/>
<hk:form action="/index_selcity.do" needreturnurl="true">
	<hk:select oid="id_countryId" checkedvalue="${countryId}" name="countryId" onchange="initprovince(this.value);initcity(0);">
		<hk:option value="0" data="view2.please_select" res="true"/>
		<c:forEach var="c" items="${countrylist}">
			<hk:option value="${c.countryId}" data="${c.country}"/>
		</c:forEach>
	</hk:select>
	<hk:select oid="id_provinceId" name="provinceId" onchange="initcity(this.value)">
		<hk:option value="0" data="view2.please_select" res="true"/>
	</hk:select>
	<hk:select oid="id_pcityId" name="cityId" onchange="${onselcityfunction}">
		<hk:option value="0" data="view2.please_select" res="true"/>
	</hk:select>
	<hk:submit value="view2.submit" res="true" clazz="btn"/>
</hk:form>
</div>
<script type="text/javascript">
var sel_countryId=1;
var sel_provicneId=0;

var province=new Array();
<c:forEach var="p" items="${provincelist}" varStatus="idx">
province[${idx.index }]=new Array(${p.provinceId },${p.countryId},'${p.province }');
</c:forEach>

var city=new Array();
<c:forEach var="c" items="${citylist}" varStatus="idx">
city[${idx.index }]=new Array(${c.cityId },${c.provinceId},${c.countryId},'${c.city }');
</c:forEach>
function initprovince(countryId){
	var oo=getObj('id_provinceId');
	oo.options.length=0;
	oo.options[0]=new Option("请选择",0);
	for(var i=0;i<province.length;i++){
		if(province[i][1]==countryId){
			oo.options[oo.options.length]=new Option(province[i][2],province[i][0]);
		}
	}
}

function initcity(pid){
	var oo=getObj("id_pcityId");
	oo.options.length=0;
	oo.options[0]=new Option("请选择",0);
	for(var i=0;i<city.length;i++){
		if(city[i][1]==pid){
			oo.options[oo.options.length]=new Option(city[i][3],city[i][0]);
		}
	}
	if(oo.options.length==2){
		oo.options[0]=new Option(oo.options[1].text,oo.options[1].value);
		oo.options.length=1;
	}
}

function initselected(sel_cityId){
	for(var i=0;i<city.length;i++){
		if(city[i][0]==sel_cityId){
			sel_provicneId=city[i][1];//找到povince
			sel_countryId=city[i][2];//找到country
			break;
		}
	}

	var oo=getObj("id_countryId");
	for(var i=0;i<oo.options.length;i++){
		if(sel_countryId==parseInt(oo.options[i].value)){
			oo.options[i].selected=true;
			break;
		}
	}
	initprovince(sel_countryId);
	oo=getObj("id_provinceId");
	for(var k=0;k<oo.options.length;k++){
		if(sel_provicneId==parseInt(oo.options[k].value)){
			oo.options[k].selected=true;//设定province selected
			break;
		}
	}
	
	initcity(sel_provicneId);
	oo=getObj("id_pcityId");
	for(var i=0;i<oo.options.length;i++){
		if(sel_cityId==parseInt(oo.options[i].value)){
			oo.options[i].selected=true;//设定city selected
			break;
		}
	}
}
initprovince(${countryId});
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>