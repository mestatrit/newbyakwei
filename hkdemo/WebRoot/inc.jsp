<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<a href="<%=request.getContextPath() %>/index.jsp">首页</a>
<br/>
<a href="<%=request.getContextPath()%>/hello" target="_blank">页面输出</a>
<br />
<a href="<%=request.getContextPath()%>/hello_method1">向jsp页面输出用户传入的数据</a>
<br />
<a href="<%=request.getContextPath()%>/hello_method2">重定向功能</a>
<br />
<a href="<%=request.getContextPath()%>/hello_method3">资源文件输出</a>
<br />
<a href="<%=request.getContextPath()%>/hello_method4">页面调用action</a>
<br />
<a href="<%=request.getContextPath()%>/hello_method4">连接到外部网站163.com</a>
<br />
