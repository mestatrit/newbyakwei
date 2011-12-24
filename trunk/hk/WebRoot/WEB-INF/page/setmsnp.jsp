<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.im.msn.MsnRobot"%><%@page import="com.hk.frame.util.HkUtil"%>
<%MsnRobot msnRobot=(MsnRobot)HkUtil.getBean("msnRobot");
msnRobot.getMessenger().getOwner().setDisplayName("火酷机器人www.huoku.com");
msnRobot.getMessenger().getOwner().setPersonalMessage("火酷，让你知道哪里最火什么最酷");
%>
<h1>msn set ok</h1>