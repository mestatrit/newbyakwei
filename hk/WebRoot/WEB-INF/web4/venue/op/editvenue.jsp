<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.CompanyKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
List<CompanyKind> kindlist=CompanyKindUtil.getCompanKindList();
request.setAttribute("kindlist",kindlist);%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<table class="nt reg" cellpadding="0" cellspacing="0">
<tr>
	<td><h1 style="display:inline"><hk:data key="view2.edit_venue_info"/></h1></td>
</tr>
</table>
<hr/>
<br/>
<div>
	<form id="sfrm" method="post" onsubmit="return subvenuefrm(this.id)" action="<%=path %>/h4/op/user/venue_editvenue.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		<table class="nt reg" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90px" align="right"><hk:data key="company.name"/></td>
				<td width="260px;">
					<hk:text name="name" value="${company.name}" maxlength="30" clazz="text"/>
				</td>
				<td>
					<div class="ruo"><hk:data key="company.name.tip"/></div>
					<div id="_name" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.addr"/></td>
				<td>
					<hk:text name="addr" maxlength="300" clazz="text" value="${company.addr}"/>
				</td>
				<td>
					<div id="_addr" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.tel"/></td>
				<td>
					<hk:text name="tel" maxlength="300" clazz="text" value="${company.tel}"/>
				</td>
				<td>
					<div id="_tel" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.zone"/></td>
				<td>
					<div style="margin-bottom: 10px;">
					<c:set scope="request" var="onselcityfunction" value="setzonename(this.value)"></c:set>
					<jsp:include page="../../inc/zonesel.jsp"></jsp:include>
					<script type="text/javascript">
					initselected(${company.pcityId});
					</script>
					</div>
				</td>
				<td>
					<div id="_zoneName" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.intro"/>
				</td>
				<td>
				<div class="sp">
					<hk:textarea name="intro" value="${company.intro}" clazz="text_area2"/>
				</div>
				</td>
				<td>
					<div id="_intro" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.traffic"/>
				</td>
				<td>
				<div class="sp">
					<hk:textarea name="traffic" value="${company.traffic}" clazz="text_area2"/>
				</div>
				</td>
				<td>
					<div id="_traffic" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<hk:submit clazz="btn2 split-r" value="view2.submit" res="true"/>
					<a href="/venue/${companyId }/"><hk:data key="view2.return"/></a>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.COMPANY_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.COMPANY_NAME_DUPLICATE %>={objid:"_name"};
var err_code_<%=Err.COMPANY_ADDR_LEN_TOOLONG %>={objid:"_addr"};
var err_code_<%=Err.COMPANY_TEL_LEN_TOOLONG %>={objid:"_tel"};
var err_code_<%=Err.COMPANY_INTRO_LEN_TOOLONG %>={objid:"_intro"};
var err_code_<%=Err.COMPANY_TRAFFIC_ERROR %>={objid:"_traffic"};
var err_code_<%=Err.ZONE_ERROR %>={objid:"_zoneName"};
function subvenuefrm(frmid){
	showGlass(frmid);
	setHtml('_name','');
	setHtml('_addr','');
	setHtml('_tel','');
	setHtml('_zoneName','');
	return true;
}
function editerror(json,errlist){
	var errorcode_arr=errlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function editok(error,error_msg,v){
	tourl('/venue/${companyId}/');
}
function setzonename(id){
	if(id>0){
		for(var i=0;i<city.length;i++){
			if(city[i][0]==parseInt(id)){
				getObj('ipt_zoneName').value=city[i][3];
			}
		}
	}
}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>