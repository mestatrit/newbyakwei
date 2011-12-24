<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.im.msn.MsnRobot"%><%@page import="com.hk.frame.util.HkUtil"%>
<%MsnRobot msnRobot=(MsnRobot)HkUtil.getBean("msnRobot");
msnRobot.getMessenger().logout();
Thread.sleep(5000);
msnRobot.initMessenger();
%>
<h1>msn robot login ok</h1>