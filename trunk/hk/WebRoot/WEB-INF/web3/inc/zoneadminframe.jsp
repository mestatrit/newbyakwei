<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="body_hk_content" scope="request">
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit"><hk:data key="view.user.mgr.title"/></div>
		<div class="cont"> 
			<div class="subtit">信息管理</div>
			<ul class="userset">
				<li><a id="op_1" class="n1" href="<%=path %>/op/cmd/cmd_cmdcmplist.do">已推荐足迹</a></li>
				<li><a id="op_2" class="n1" href="<%=path %>/op/cmd/cmd_cmdproductlist.do">已推荐产品</a></li>
			</ul>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>
<div class="mod_primary">
	<div class="nav-2">
		<div class="subnav">
			<div class="l">
			</div>
			<div class="mid">
				<ul>
					<li class="path">
						<ul>
							<li>
								<a class="home" href="#"></a>
							</li>
							<li>
								<a class="nav-a" href="#">${html_title }</a>
							</li>
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
<jsp:include page="frame.jsp"></jsp:include>