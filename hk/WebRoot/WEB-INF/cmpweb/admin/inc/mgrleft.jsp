<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%>
<%@page import="web.pub.util.EppViewUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<%
EppViewUtil.loadCmpNavFunc(request);
%>
<style type="text/css">
.amod{
}
.noamod{
	padding-left: 10px;
}
a.linkrow{
	display: block;
	font-size: 14px;
	line-height: 30px;
	padding-left: 10px;
}
a.linkrow.active,
a.linkrow:hover{
background-color: #2398C9;
color: #ffffff;
font-weight: bold;
text-decoration: none;
}
.mgrnav{
	margin-top:10px;
	border: 1px solid #B4B4B4;
}
.mgrnav .tit{
	font-size: 16px;
	color:#222222;
	background-color:#EEEEEE;
	font-weight: bold;
	padding-left: 10px; 
}
</style>
<div class="mgrleft">
	<div class="divrow">
		<div class="amod">
			<a class="linkrow" href="http://<%=request.getServerName() %>">网站首页</a>
		</div>
		<c:if test="${o.userId==loginUser.userId}">
			<div class="amod">
				<a class="linkrow <c:if test="${active_0==1}">active</c:if>" href="<%=path %>/epp/mgr/web/adminuser.do?companyId=${companyId}">管理员</a>
			</div>
		</c:if>
		<div class="amod"><a class="linkrow <c:if test="${active_1==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">栏目管理</a></div>
		<c:if test="${o.cmpEdu || (o.cmpFlgEnterprise && cmpInfo.tmlflg==1)}">
			<div class="amod"><a class="linkrow <c:if test="${active_29==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId}&pageflg=1">首页区块设置</a></div>
		</c:if>
		<c:if test="${o.cmpEdu}">
		<!-- 教育类型具有的功能 -->
			<div class="amod"><a class="linkrow <c:if test="${active_31==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/org.do?companyId=${companyId}">机构管理</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_32==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/org_applylist.do?companyId=${companyId}">机构申请审核</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_33==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/studykind.do?companyId=${companyId}">专业管理</a></div>
			<div class="amod"><a class="linkrow" href="javascript:refreshhomepage()">刷新首页</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_34==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_updatecmpinfoad.do?companyId=${companyId}">企业广告代码</a></div>
		</c:if>
		<div class="amod"><a class="linkrow <c:if test="${active_20==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_update.do?companyId=${companyId}">企业基本信息</a></div>
		<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
			<div class="amod"><a class="linkrow <c:if test="${active_39==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_updatebgimage.do?companyId=${companyId}">默认背景图</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_40==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_updatecpinfo.do?companyId=${companyId}">版权和许可信息</a></div>
		</c:if>
		
		<c:if test="${o.cmpFlgE_COMMERCE}">
			<div class="amod"><a class="linkrow <c:if test="${active_28==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmphomepicad.do?companyId=${companyId}">首页焦点图</a></div>
		</c:if>
		<c:if test="${o.cmpHairDressing}">
			<div class="amod"><a class="linkrow <c:if test="${active_35==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/actor_rolelist.do?companyId=${companyId}">角色管理</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_36==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/actor.do?companyId=${companyId}">人员管理</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_38==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/svr_kindlist.do?companyId=${companyId}">服务分类管理</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_37==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/svr.do?companyId=${companyId}">服务管理</a></div>
			<div class="amod"><a class="linkrow <c:if test="${active_41==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/reserve.do?companyId=${companyId}">预约管理</a></div>
		</c:if>
		<div class="amod"><a class="linkrow <c:if test="${active_21==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_piclist.do?companyId=${companyId}">企业图片</a></div>
		<div class="amod"><a class="linkrow <c:if test="${active_14==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_uploadlogo.do?companyId=${companyId}">网站logo</a></div>
		<div class="amod"><a class="linkrow <c:if test="${active_7==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/adminbomber.do?companyId=${companyId}">内容管理员</a></div>
		<div class="amod"><a class="linkrow <c:if test="${active_22==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpfrlink.do?companyId=${companyId}">友情链接</a></div>
		<div class="amod"><a class="linkrow <c:if test="${active_23==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">企业广告</a></div> 
		<div class="amod"><a class="linkrow <c:if test="${active_24==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmprefuser.do?companyId=${companyId}">企业会员</a></div> 
		<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==0}">
			<div class="amod"><a class="linkrow <c:if test="${active_25==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_styledata.do?companyId=${companyId}">网站配色</a></div> 
		</c:if>
		<div class="amod"><a class="linkrow <c:if test="${active_26==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmplanguage.do?companyId=${companyId}">多语言版本</a></div> 
		<div class="amod"><a class="linkrow <c:if test="${active_30==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmparticle_taglist.do?companyId=${companyId}">标签管理</a></div> 
		<!--
		 --> 
		<div class="mgrnav">
			<div class="tit">现有栏目</div>
			<c:forEach var="mgrleft_nav" items="${mgrleft_navlist}">
				<div class="amod">
					<c:if test="${mgrleft_nav.noAdminFunc}"><span class="noamod">${mgrleft_nav.name }</span></c:if>
					<c:if test="${!mgrleft_nav.noAdminFunc}">
						<a class="linkrow" href="<%=path %>/epp/web/op/webadmin/admincmpnav_view.do?companyId=${companyId}&oid=${mgrleft_nav.oid}">${mgrleft_nav.name }</a>
					</c:if>
					<c:if test="${mgrleft_nav.homeNav}">
						<div class="amod" style="margin-left: 30px;">
							<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==0}">
								<div class="amod"><a class="linkrow <c:if test="${active_9==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav_showinhome.do?companyId=${companyId}">首页模块显示</a></div>
								<div class="amod"><a class="linkrow <c:if test="${active_15==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmparticle_alllist.do?companyId=${companyId}">首页推荐文章</a></div>
							</c:if>
						</div>
					</c:if>
					<c:if test="${mgrleft_nav.cmpBbsFunc}">
						<a class="linkrow <c:if test="${active_27==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}">论坛内容管理</a>
					</c:if>
					<c:forEach var="child" items="${mgrleft_nav.children}">
						<div class="amod" style="margin-left: 30px;">
							<c:if test="${child.noAdminFunc}"><span class="noamod">${child.name }</span></c:if>
							<c:if test="${!child.noAdminFunc}">
								<a class="linkrow <c:if test="${navoid==child.oid}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav_view.do?companyId=${companyId}&oid=${child.oid}">${child.name }</a>
							</c:if>
							<c:if test="${child.cmpBbsFunc}">
								<a class="linkrow <c:if test="${active_27==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}">论坛内容管理</a>
							</c:if>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<a class="linkrow <c:if test="${active_16==1}">active</c:if>" href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出管理</a>
	</div>
</div>
<script type="text/javascript">
function refreshhomepage(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/info_refreshhomepage.do?companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>