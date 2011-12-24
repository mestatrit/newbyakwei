<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.ServletUtil"%>
<%@page import="com.hk.svr.Tb_UserService"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.bean.taobao.Tb_User"%>
<%@page import="com.etbhk.util.TbHkWebUtil"%>
<%
long userid=ServletUtil.getLong(request,"userid");
Tb_UserService tbUserService=(Tb_UserService)HkUtil.getBean("tb_UserService");
Tb_User tbUser=tbUserService.getTb_User(userid);
if(tbUser==null){
	return;
}
TbHkWebUtil.setLoginUser(request, response, tbUser, "");
%>
<h1>user login ok</h1>