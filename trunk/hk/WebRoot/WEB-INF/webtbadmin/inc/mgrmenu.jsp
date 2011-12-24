<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<style type="text/css">
ul.mgrlist, ul.mgrlist li{
list-style: none;
}
ul.mgrlist li{
height: 25px;
border-bottom: 1px solid #e5e5e5;
text-indent: 5px;
}
ul.mgrlist li a{
display: block;
text-decoration: none;
}
ul.mgrlist li a:hover,
ul.mgrlist li a.sel{
color: #ffffff;
font-weight: bold;
background-color: #2398C9;
text-decoration: none;
}
</style>
<div class="f_l" style="width: 130px">
<ul class="mgrlist">
	<li><a <c:if test="${item_mod}"> class="sel"</c:if> href="${ctx_path }/tb/admin/item">商品列表</a></li>
	<li><a <c:if test="${cmd_item_mod}"> class="sel"</c:if> href="${ctx_path }/tb/admin/item_cmdlist">首页推荐商品</a></li>
	<li><a <c:if test="${taokeitem_mod}"> class="sel"</c:if> href="${ctx_path }/tb/admin/taokeitem">淘宝客商品查询</a></li>
	<li><a <c:if test="${sysuser_mod}"> class="sel"</c:if> href="${ctx_path }/tb/admin/sysuser">系统用户</a></li>
	<li><a <c:if test="${askcat_mod}"> class="sel"</c:if> href="${ctx_path }/tb/admin/askcat">问答分类</a></li>
</ul>
</div>