<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpJoinInApply"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<%
EppViewUtil.loadCmpNavFunc(request);
%>
<style type="text/css">
a.linkrow{
	display: block;
	font-size: 16px;
	line-height: 30px;
}
a.linkrow.active,
a.linkrow:hover{
background-color: #2398C9;
color: #ffffff;
font-weight: bold;
text-decoration: none;
}
</style>
<div class="mgrleft">
	<div class="divrow">
		<c:if test="${o.userId==loginUser.userId}">
			<a class="linkrow <c:if test="${active_0==1}">active</c:if>" href="<%=path %>/epp/mgr/web/adminuser.do?companyId=${companyId}">管理员</a>
		</c:if>
		<a class="linkrow <c:if test="${active_14==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_uploadlogo.do?companyId=${companyId}">网站logo</a>
		<a class="linkrow <c:if test="${active_1==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">导航管理</a>
		<div style="margin-left: 20px;">
		<c:forEach var="mgrleft_nav" items="${mgrleft_navlist}">
			<c:if test="${!mgrleft_nav.homeNav}">
				<a href="<%=path %>/epp/web/op/webadmin/admincmpnav_view.do?oid=${mgrleft_nav.oid }&companyId=${companyId}">${n.name }</a>
			</c:if>
		</c:forEach>
		</div>
		<c:if test="${nav_reffunc_11}">
			<a class="linkrow <c:if test="${active_9==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav_showinhome.do?companyId=${companyId}">首页模块显示</a>
			<a class="linkrow <c:if test="${active_15==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmparticle_alllist.do?companyId=${companyId}">首页推荐文章</a>
		</c:if>
		<c:if test="${nav_reffunc_10}">
			<a class="linkrow <c:if test="${active_2==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}">销售网络</a>
		</c:if>
		<c:if test="${nav_reffunc_8}">
			<a class="linkrow <c:if test="${active_3==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpcontact.do?companyId=${companyId}">在线联系</a>
		</c:if>
		<c:if test="${nav_reffunc_7}">
			<a class="linkrow <c:if test="${active_4==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpmsg.do?companyId=${companyId}">留言</a>
		</c:if>
		<c:if test="${nav_reffunc_13}">
			<a class="linkrow <c:if test="${active_5==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&readed=<%=CmpJoinInApply.READED_N %>">加盟申请</a>
		</c:if>
		<c:if test="${nav_reffunc_6}">
			<a class="linkrow <c:if test="${active_6==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpbbskind.do?companyId=${companyId}">论坛分类</a>
			<a class="linkrow <c:if test="${active_7==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/adminbomber.do?companyId=${companyId}">内容管理员</a>
		</c:if>
		<c:if test="${nav_reffunc_9}">
			<a class="linkrow <c:if test="${active_8==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav_setmap.do?companyId=${companyId}">电子地图</a>
		</c:if>
		<c:if test="${nav_reffunc_12}">
			<a class="linkrow <c:if test="${active_10==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId}">产品分类</a>
			<a class="linkrow <c:if test="${active_11==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId}">产品</a>
		</c:if>
		
		<c:if test="${nav_reffunc_4}">
			<a class="linkrow <c:if test="${active_12==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/box.do?companyId=${companyId}">宝箱</a>
		</c:if>
		<c:if test="${nav_reffunc_5}">
			<a class="linkrow <c:if test="${active_13==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/coupon.do?companyId=${companyId}">优惠券</a>
		</c:if>
		<a class="linkrow <c:if test="${active_16==1}">active</c:if>" href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出管理</a>
	</div>
</div>
