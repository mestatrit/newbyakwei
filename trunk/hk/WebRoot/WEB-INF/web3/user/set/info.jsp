<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.svr.pub.ZoneUtil"%>
<%@page import="com.hk.bean.City"%>
<%@page import="com.hk.bean.Province"%>
<%@page import="com.hk.bean.User"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.info"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<hk:form oid="infofrm" onsubmit="return subfrm(this.id)" action="/user/set/set_setinfoweb.do" target="hideframe">
<table class="infotable" cellpadding="0" cellspacing="0">
<tr>
	<td width="90px"><hk:data key="user.nickname"/></td>
	<td>
		<div class="f_l">
			<hk:text name="nickName" value="${user.nickName}" maxlength="20" clazz="text"/><br/>
			<div id="user_nickname_error" class="error"></div>
		</div>
		<div id="user_nickname_flag" class="flag"></div><div class="clr"></div>
	</td>
</tr>
<tr>
	<td width="90px"><hk:data key="userotherinfo.name"/></td>
	<td>
		<div class="f_l">
			<hk:text name="name" value="${info.name}" maxlength="20" clazz="text"/><br/>
			<div id="userotherinfo_name_error" class="error"></div>
		</div>
		<div id="userotherinfo_name_flag" class="flag"></div><div class="clr"></div>
	</td>
</tr>
<tr>
	<td width="90px"><hk:data key="userotherinfo.sex"/></td>
	<td>
		<div class="f_l">
			<hk:radioarea name="sex" checkedvalue="${user.sex}">
			<hk:radio value="<%=User.SEX_MALE %>" data="userotherinfo.sex_1" res="true"/>
			<hk:radio value="<%=User.SEX_FEMALE %>" data="userotherinfo.sex_2" res="true"/>
			</hk:radioarea><br/>
			<div id="userotherinfo_sex_error" class="error"></div>
		</div>
		<div id="userotherinfo_sex_flag" class="flag"></div><div class="clr"></div>
	</td>
</tr>
<tr>
	<td width="90px"><hk:data key="userotherinfo.city"/></td>
	<td>
		<div class="f_l">
			<jsp:include page="../../../web4/inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${user.cityId});
				</script>
			<br/>
			<div id="userotherinfo_city_error" class="error"></div>
		</div>
		<div id="userotherinfo_city_flag" class="flag"></div><div class="clr"></div>
	</td>
</tr>
<tr>
	<td width="90px"><hk:data key="userotherinfo.intro"/></td>
	<td>
		<div class="f_l">
			<hk:textarea name="intro" clazz="text_area" value="${info.intro}"/><br/>
			<div id="userotherinfo_intro_error" class="error"></div>
		</div>
		<div id="userotherinfo_intro_flag" class="flag"></div><div class="clr"></div>
	</td>
</tr>
<tr>
	<td width="90px"></td>
	<td>
	<div>
	<hk:submit value="view.submit" res="true" clazz="btn"/>
	</div>
	</td>
</tr>
</table>
</hk:form>
</div>
<script type="text/javascript">
function subfrm(frmid){
	if(getObj('city').value==0){
		validateErr('userotherinfo_city','请选择所在地');
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		validateErr(obj_id_param,error_msg);
		hideSubmitDiv();
	}
	else{
		refreshurl();
	}
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>