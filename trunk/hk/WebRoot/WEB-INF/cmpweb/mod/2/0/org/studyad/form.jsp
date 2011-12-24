<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" onsubmit="return substudyfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="orgnavId" value="${orgnavId}"/>
	<hk:hide name="orgId" value="${orgId}"/>
	<hk:hide name="adid" value="${adid}"/>
	<hk:hide name="kindId" value="${kindId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="70">专业：</td>
			<td>
				${cmpStudyKind.name } <a href="javascript:toeditkind()">修改专业</a>
			</td>
		</tr>
		<tr>
			<td width="70">课程名称：</td>
			<td>
				<hk:text name="title" maxlength="50" clazz="text" value="${cmpOrgStudyAd.title}"/> 
				<div class="infowarn" id="_title"></div>
			</td>
		</tr>
		<tr>
			<td>授课学校：</td>
			<td>
				<hk:text name="schoolName" maxlength="50" clazz="text" value="${cmpOrgStudyAd.schoolName}"/> 
				<div class="infowarn" id="_schoolname"></div>
			</td>
		</tr>
		<tr>
			<td>授课方式：</td>
			<td>
				<hk:text name="teachType" maxlength="50" clazz="text" value="${cmpOrgStudyAd.teachType}"/> 
				<div class="infowarn" id="_teachtype"></div>
			</td>
		</tr>
		<tr>
			<td>培训费用：</td>
			<td>
				<hk:text name="price" maxlength="20" clazz="text" value="${cmpOrgStudyAd.price}"/> 
				<div class="infowarn" id="_price"></div>
			</td>
		</tr>
		<tr>
			<td>开课时间：</td>
			<td><fmt:formatDate var="beginTime" value="${cmpOrgStudyAd.beginTime}" pattern="yyyy-MM-dd"/>
				<hk:text name="beginTime" maxlength="50" clazz="text beginTime" value="${beginTime}"/> 
				<div class="infowarn" id="_begintime"></div>
			</td>
		</tr>
		<tr>
			<td>有效日期：</td>
			<td><fmt:formatDate var="availableTime" value="${cmpOrgStudyAd.availableTime}" pattern="yyyy-MM-dd"/>
				<hk:text name="availableTime" maxlength="50" clazz="text availableTime" value="${availableTime}"/> 
				<div class="infowarn" id="_availabletime"></div>
			</td>
		</tr>
		<tr>
			<td>上课地点：</td>
			<td>
				<hk:text name="studyAddr" maxlength="50" clazz="text" value="${cmpOrgStudyAd.studyAddr}"/> 
				<div class="infowarn" id="_studyaddr"></div>
			</td>
		</tr>
		<tr>
			<td>招生对象：</td>
			<td>
				<hk:text name="studyUser" maxlength="50" clazz="text" value="${cmpOrgStudyAd.studyUser}"/> 
				<div class="infowarn" id="_studyuser"></div>
			</td>
		</tr>
		<tr>
			<td colspan="2">内容简介：<br/>
				<hk:textarea name="content" style="width: 600px;height: 300px" value="${cmpOrgStudyAdContent.content}"/> 
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn split-r" value="提交"/>
				<c:if test="${cmpOrgStudyAd!=null}">
				<a href="/edu/${companyId }/${orgId}/zhaosheng/${orgnavId}/${adid }.html">返回</a> 
				</c:if>
				<c:if test="${cmpOrgStudyAd==null}">
				<a href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
				</c:if>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPORGSTUDYAD_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPORGSTUDYAD_AVAILABLETIME_ERROR %>={objid:"_availabletime"};
var err_code_<%=Err.CMPORGSTUDYAD_BEGINTIME_ERROR %>={objid:"_begintime"};
var err_code_<%=Err.CMPORGSTUDYAD_STUDYADDR_ERROR %>={objid:"_studyaddr"};
var err_code_<%=Err.CMPORGSTUDYAD_SCHOOLNAME_ERROR %>={objid:"_schoolname"};
var err_code_<%=Err.CMPORGSTUDYAD_STUDYUSER_ERROR %>={objid:"_studyuser"};
var err_code_<%=Err.CMPORGSTUDYAD_TEACHTYPE_ERROR %>={objid:"_teachtype"};
var err_code_<%=Err.CMPORGSTUDYAD_PRICE_ERROR %>={objid:"_price"};
var err_code_<%=Err.CMPORGSTUDYADCONTENT_CONTENT_ERROR %>={objid:"_content"};
function substudyfrm(frmid){
	setHtml('_title','');
	setHtml('_availabletime','');
	setHtml('_begintime','');
	setHtml('_studyaddr','');
	setHtml('_schoolname','');
	setHtml('_studyuser','');
	setHtml('_teachtype','');
	setHtml('_price','');
	setHtml('_content','');
	showGlass(frmid);
	return true;
}
function delstudyad(){
	if(window.confirm('确实要删除文章？')){
		tourl("<%=path%>/epp/web/org/studyad_del.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&adid=${adid}");
	}
}
function toeditkind(){
	tourl("<%=path%>/epp/web/org/studyad_selkind.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&adid=${adid}&return_url="+encodeLocalURL());
}
</script>