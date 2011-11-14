<%@ page language="java" pageEncoding="UTF-8"
%><%@page import="iwant.web.admin.util.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<form id="frm" method="post" onsubmit="subfrm(this.id)" target="hideframe" action="${form_action }">
<input type="hidden" name="ch" value="1"/>
<input type="hidden" name="catid" value="${cat.catid }"/>
<input type="hidden" name="projectid" value="${project.projectid }"/>
<table class="formt">
	<tr>
		<td width="90" align="right">名称</td>
		<td>
			<input maxlength="20" name="name" value="<hk:value value="${project.name }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="4"/></div>
			<div class="infowarn" id="err_name"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">均价</td>
		<td>
			<input maxlength="20" name="avrprice" value="<hk:value value="${project.avrprice }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="45"/></div>
			<div class="infowarn" id="err_avrprice"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">地区</td>
		<td>
			<jsp:include page="../inc/zone_district_inc.jsp"></jsp:include>
			城市：${syscnf_city.name}
			${syscnf_districtlist}
			<div class="infowarn" id="err_did"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">电话</td>
		<td>
			<input maxlength="30" name="tel" value="<hk:value value="${project.tel }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="6"/></div>
			<div class="infowarn" id="err_tel"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">地址</td>
		<td>
			<input maxlength="100" name="addr" value="<hk:value value="${project.addr }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="5"/></div>
			<div class="infowarn" id="err_addr"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">描述</td>
		<td>
			<textarea name="descr" style="width: 270px;height: 80px;"><hk:value value="${project.descr}" textarea="true"/></textarea>
			<div class="ruo"><hk:data key="7"/></div>
			<div class="infowarn" id="err_descr"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">容积率</td>
		<td>
			<input maxlength="30" name="rongjilv" value="<hk:value value="${project.rongjilv }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="36"/></div>
			<div class="infowarn" id="err_rongjilv"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">绿化率</td>
		<td>
			<input maxlength="30" name="lvhualv" value="<hk:value value="${project.lvhualv }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="37"/></div>
			<div class="infowarn" id="err_lvhualv"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">物业费</td>
		<td>
			<input maxlength="30" name="mrate" value="<hk:value value="${project.mrate }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="38"/></div>
			<div class="infowarn" id="err_mrate"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">车位信息</td>
		<td>
			<input maxlength="30" name="carspace" value="<hk:value value="${project.carspace }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="39"/></div>
			<div class="infowarn" id="err_carspace"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">建筑类型</td>
		<td>
			<input maxlength="30" name="buildtype" value="<hk:value value="${project.buildtype }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="44"/></div>
			<div class="infowarn" id="err_buildtype"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">物业类型</td>
		<td>
			<input maxlength="30" name="mtype" value="<hk:value value="${project.mtype }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="41"/></div>
			<div class="infowarn" id="err_mtype"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">交通信息</td>
		<td>
			<textarea name="traffic" style="width: 270px;height: 80px;"><hk:value value="${project.traffic}" textarea="true"/></textarea>
			<div class="ruo"><hk:data key="42"/></div>
			<div class="infowarn" id="err_traffic"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">周边信息</td>
		<td>
			<textarea name="neardescr" style="width: 270px;height: 80px;"><hk:value value="${project.neardescr}" textarea="true"/></textarea>
			<div class="ruo"><hk:data key="43"/></div>
			<div class="infowarn" id="err_neardescr"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right"></td>
		<td>
			<input type="submit" value="提交" class="btn split-r"/>
			<c:if test="${project!=null }">
				<a href="${appctx_path }/mgr/project_view.do?projectid=${projectid}">返回</a>
			</c:if>
			<c:if test="${project==null }">
				<a href="${appctx_path }/mgr/project.do">返回</a>
			</c:if>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.PROJECT_NAME_ERR %>={objid:"err_name"};
var err_code_<%=Err.PROJECT_ADDR_ERR%>={objid:"err_addr"};
var err_code_<%=Err.PROJECT_TEL_ERR%>={objid:"err_tel"};
var err_code_<%=Err.PROJECT_DESCR_ERR%>={objid:"err_descr"};
var err_code_<%=Err.PROJECT_CATID_ERR%>={objid:"err_catid"};
var err_code_<%=Err.PROJECT_DID_ERR%>={objid:"err_did"};
var err_code_<%=Err.PROJECT_RONGJILV_ERR%>={objid:"err_rongjilv"};
var err_code_<%=Err.PROJECT_LVHUALV_ERR%>={objid:"err_lvhualv"};
var err_code_<%=Err.PROJECT_BUILDTYPE_ERR%>={objid:"err_buildtype"};
var err_code_<%=Err.PROJECT_MRATE_ERR%>={objid:"err_mrate"};
var err_code_<%=Err.PROJECT_TRAFFIC_ERR%>={objid:"err_traffic"};
var err_code_<%=Err.PROJECT_NEARDESCR_ERR%>={objid:"err_neardescr"};
var err_code_<%=Err.PROJECT_CARSPACE_ERR%>={objid:"err_carspace"};
var err_code_<%=Err.PROJECT_MTYPE_ERR%>={objid:"err_mtype"};
var err_code_<%=Err.PROJECT_AVRPRICE_ERR%>={objid:"err_avrprice"};

var glassid=null;
var submited=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('err_name','');
	setHtml('err_addr','');
	setHtml('err_tel','');
	setHtml('err_descr','');
	setHtml('err_catid','');
	setHtml('err_f','');
	setHtml('err_did','');
	setHtml('err_rongjilv','');
	setHtml('err_lvhualv','');
	setHtml('err_buildtype','');
	setHtml('err_mrate','');
	setHtml('err_mtype','');
	setHtml('err_neardescr','');
	setHtml('err_carspace','');
	setHtml('err_traffic','');
	setHtml('err_avrprice','');
	return true;
}

function createerr(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	removeGlass(glassid);
	submited=false;
}

function updateerr(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	removeGlass(glassid);
	submited=false;
}

function createok(err,err_msg,v){
	tourl('${appctx_path}/mgr/project_view.do?projectid='+v);
}

function updateok(err,err_msg,v){
	if(back_url.length==0){
		tourl('${appctx_path}/mgr/project.do');
		return;
	}
	tourl(decodeURIComponent(back_url)); 
}
var back_url="${backUrl.lastEncUrl}";
</script>