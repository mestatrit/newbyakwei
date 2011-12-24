<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%>
<%@page import="com.hk.bean.Company"%>
<%@page import="com.hk.bean.CmpSvrCnf"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">${company.name }</div>
	<div class="mod_content">
		<a target="_blank" href="/venue/${companyId }">${company.name }</a>
		<hk:data key="view.companystatus_${company.companyStatus}"/> 
		<c:if test="${!company.checkSuccess}">
			<a class="split-r" href="javascript:checkok(${companyId })">设为通过</a>
		</c:if>
		<c:if test="${!company.checkFail}">
			<a class="split-r" href="javascript:checkfail(${companyId })">设为不通过</a>
		</c:if>
		<br/>
		<c:if test="${not empty company.headPath}">
			<div class="reply"><img src="${company.head240}"/><br/></div>
		</c:if>
		所有人：
		<c:if test="${user!=null}">${user.nickName }</c:if> 
		<c:if test="${user==null}">无</c:if> 
		<a href="<%=path %>/h4/admin/cmp_chgcmpuser.do?companyId=${companyId}">转移企业</a><br/>
		<form id="funcfrm" onsubmit="return subfuncfrm(this.id)" method="post" action="<%=path %>/h4/admin/cmp_createcmpfunc.do" target="hideframe">
			<hk:hide name="companyId" value="${companyId}"/>
			开启功能：
			<%HkWebUtil.loadCmpFuncList(request); %>
			<c:forEach var="func" items="${cmpfunclist}">
				<span class="split-r">
					<hk:checkbox name="funcoid" oid="func_${func.oid}" value="${func.oid}" checkedvalues="${sel_funcoid}"/>
					<label for="func_${func.oid}">${func.name }</label>
				</span>
			</c:forEach>
			<hk:submit clazz="btn" value="保存功能"/>
		</form><br/>
		<c:if test="${cmpOtherInfo.showHkAd}">
		<span class="split-r">显示火酷广告</span> <a href="javascript:hidehkad(${companyId })">关闭</a>
		</c:if>
		<c:if test="${!cmpOtherInfo.showHkAd}">
		<span class="split-r">隐藏火酷广告</span> <a href="javascript:showhkad(${companyId })">显示</a>
		</c:if>
		<c:if test="${cmpInfo!=null}">
			企业网站类型：<hk:data key="view2.company.cmpflg${company.cmpflg}"/> <a href="<%=path %>/h4/admin/cmp_chgcmpflg.do?companyId=${companyId}">修改</a>
			<br/>
			<c:if test="${company.cmpFlgE_COMMERCE}">
				产品属性：
				<c:if test="${company.openProductattrflg}">
					已开启 
					<a href="javascript:closeproductattrflg()">关闭</a>
				</c:if>
				<c:if test="${!company.openProductattrflg}">
					已关闭 
					<a href="javascript:openproductattrflg()">开启</a>
				</c:if><br/>
			</c:if>
			<c:if test="${company.cmpFlgEnterprise}">
				文件系统：
				<c:if test="${cmpSvrCnf.openFile}">
					已开启
					<a href="javascript:closesvrfileflg()">关闭</a><br/>
				</c:if>
				<c:if test="${!cmpSvrCnf.openFile}">
					已关闭
					<a href="javascript:opensvrfileflg()">开启</a><br/>
				</c:if>
				视频系统：
				<c:if test="${cmpSvrCnf.openVideo}">
					已开启
					<a href="javascript:closesvrvideoflg()">关闭</a><br/>
				</c:if>
				<c:if test="${!cmpSvrCnf.openVideo}">
					已关闭
					<a href="javascript:opensvrvideoflg()">开启</a><br/>
				</c:if>
			</c:if>
		</c:if>
		<a class="more2" href="<%=path %>/h4/admin/cmp.do?status=-100">返回</a>
	</div>
</div>
<script type="text/javascript">
function createfuncok(){
	refreshurl();
}
function subfuncfrm(frmid){
	showGlass(frmid);
	return true;
}
function closeproductattrflg(){
	updateproductattrflg(<%=Company.PRODUCTATTRFLG_N %>);
}
function openproductattrflg(){
	updateproductattrflg(<%=Company.PRODUCTATTRFLG_Y %>);
}
function updateproductattrflg(flg){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_setproductattrflg.do?companyId=${companyId}&productattrflg="+flg,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function closesvrfileflg(){
	updatesvrfileflg(<%=CmpSvrCnf.FLG_N %>);
}
function opensvrfileflg(){
	updatesvrfileflg(<%=CmpSvrCnf.FLG_Y %>);
}
function updatesvrfileflg(flg){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_setsvrfileflg.do?companyId=${companyId}&fileflg="+flg,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function closesvrvideoflg(){
	updatesvrvideoflg(<%=CmpSvrCnf.FLG_N %>);
}
function opensvrvideoflg(){
	updatesvrvideoflg(<%=CmpSvrCnf.FLG_Y %>);
}
function updatesvrvideoflg(flg){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_setsvrvideoflg.do?companyId=${companyId}&videoflg="+flg,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function checkfail(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_checkfail.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function checkok(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_checkok.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function hidehkad(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_hidehkad.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function showhkad(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_showhkad.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>