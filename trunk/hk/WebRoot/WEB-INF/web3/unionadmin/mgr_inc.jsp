<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();JspDataUtil.loadCmpUnion(request);%>
<c:set var="body_hk_content" scope="request">
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit">信息管理</div>
		<div class="cont"> 
			<div class="subtit">联盟信息</div>
			<ul class="userset">
				<li><a id="op_0" class="n1" href="<%=path %>/cmpunion/op/union_toedit.do?uid=${uid}">基本信息</a></li>
				<li><a id="op_1" class="n1" href="<%=path %>/cmpunion/op/union_logo.do?uid=${uid}">修改logo</a></li>
				<li><a id="op_10" class="n1" href="<%=path %>/cmpunion/op/union_tosetcmpcreate.do?uid=${uid}">商户创建设置</a></li>
			</ul>
			<div class="subtit">分类</div>
			<ul class="userset">
				<li><a id="op_2" class="n1" href="<%=path %>/cmpunion/op/union_kindlist.do?uid=${uid}">分类管理</a></li>
				<li><a id="op_7" class="n1" href="<%=path %>/cmpunion/op/union_cmdkindlist.do?uid=${uid}">分类推荐</a></li>
			</ul>
			<div class="subtit">首页</div>
			<ul class="userset">
				<li><a id="op_3" class="n1" href="<%=path %>/cmpunion/op/union_sitemod.do?uid=${uid}">首页模块显示</a></li>
			</ul>
			<div class="subtit">消息中心</div>
			<ul class="userset">
				<li><a id="op_4" class="n1" href="<%=path %>/cmpunion/op/message_req.do?uid=${uid}&dealflg=0">未处理的请求</a></li>
				<li><a id="op_8" class="n1" href="<%=path %>/cmpunion/op/message_feed.do?uid=${uid}">商家动态</a></li>
			</ul>
			<div class="subtit">新闻</div>
			<ul class="userset">
				<li><a id="op_5" class="n1" href="<%=path %>/cmpunion/op/board.do?uid=${uid}">新闻管理</a></li>
			</ul>
			<div class="subtit">友情链接</div>
			<ul class="userset">
				<li><a id="op_6" class="n1" href="<%=path %>/cmpunion/op/link.do?uid=${uid}">友情链接管理</a></li>
			</ul>
			<div class="subtit">活动</div>
			<ul class="userset">
				<li><a id="op_9" class="n1" href="<%=path %>/cmpunion/op/cmpact.do?uid=${uid}">最新活动</a></li>
			</ul>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>
<div class="mod_primary">
	<div class="nav-2">
		<div class="subnav">
			<div class="l"></div>
			<div class="mid">
				<ul>
					<li class="path">
						<ul>
							<li>
							<c:if test="${not empty jsp_cmpUnion.domain}">
								<c:set var="domain_url">http://${jsp_cmpUnion.domain}</c:set>
							</c:if>
							<c:if test="${empty jsp_cmpUnion.domain}">
								<c:set var="domain_url">http://mall.huoku.com/u/${jsp_cmpUnion.uid}</c:set>
							</c:if><a class="home" href="#"></a></li>
							<li><a class="nav-a" href="#">${jsp_cmpUnion.name }</a></li>
							<li><a class="nav-a" href="#">${html_title }</a></li>
						</ul>
					</li>
				</ul>
				<div class="clr"></div>
			</div>
			<div class="r"></div>
			<div class="clr"></div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="inner">${mgr_content }</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
<c:if test="${op_func!=null }">
var op_func=${op_func };
</c:if>
<c:if test="${op_func==null }">
var op_func=-1;
</c:if>
var obj=getObj("op_"+op_func);
if(obj!=null){
	obj.className="n1 active";
}
</script>
</c:set>
<jsp:include page="../inc/cmpunionframe.jsp"></jsp:include>