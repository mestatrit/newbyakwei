<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">修改信息</c:set>
<c:set var="mgr_content" scope="request">
<div>
<hk:form oid="editfrm" onsubmit="return subeditfrm(this.id)" action="/cmpunion/op/union_edit.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="90px">名称</td>
			<td>
				<div class="f_l">
					<hk:text name="name" clazz="text" value="${o.name}"/>
					<div id="name_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>网站域名</td>
			<td>
				<div class="f_l">
					http://<hk:text name="domain" clazz="text_short_3" value="${o.domain}"/>
					<div id="domain_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>个性化域名</td>
			<td>
				<div class="f_l">
					http://mall.huoku.com/<hk:text name="webName" clazz="text_short_3" value="${o.webName}"/><span class="ruo">4-20个数字与字母的组合</span>
					<div id="webName_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
		<td width="90px">选择地区</td>
		<td>
			<div class="f_l" style="width: 200px;">
				<jsp:include page="../../web4/inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${o.pcityId});
				</script>
				<br/>
				<div id="zone_error" class="error"></div>
			</div>
			<div class="clr"></div>
		</td>
	</tr>
		<tr>
			<td>地址</td>
			<td>
				<div class="f_l">
					<hk:textarea name="addr" clazz="text_area" value="${o.addr}"/>
					<div id="addr_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>交通</td>
			<td>
				<div class="f_l">
					<hk:textarea name="traffic" clazz="text_area" value="${o.traffic}"/>
					<div id="traffic_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>介绍</td>
			<td>
				<div class="f_l">
					<hk:textarea name="intro" clazz="text_area" value="${o.intro}"/>
					<div id="intro_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
				<hk:submit value="提交" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPUNION_ADDR_ERROR %>={objid:"addr"};
var err_code_<%=Err.CMPUNION_DOMAIN_DUPLICATE %>={objid:"domain"};
var err_code_<%=Err.CMPUNION_WEBNAME_DUPLICATE %>={objid:"webName"};
var err_code_<%=Err.CMPUNION_WEBNAME_ERROR %>={objid:"webName"};
var err_code_<%=Err.CMPUNION_NAME_ERROR %>={objid:"name"};
var err_code_<%=Err.CMPUNION_INTRO_ERROR %>={objid:"intro"};
var err_code_<%=Err.CMPUNION_TRAFFIC_ERROR %>={objid:"traffic"};
var err_code_<%=Err.CMPUNION_ZONE_ERROR %>={objid:"zone"};
function subeditfrm(frmid){
	showSubmitDiv(frmid);
	validateClear("addr");
	validateClear("domain");
	validateClear("webName");
	validateClear("name");
	validateClear("intro");
	validateClear("traffic");
	validateClear("zone");
	return true;
}
function oneditsuccess(error,error_msg,respValue){
	refreshurl();
}
function onediterror(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	hideSubmitDiv();
}

</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>