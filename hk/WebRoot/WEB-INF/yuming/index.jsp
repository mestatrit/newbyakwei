<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/yuming/a.css" />
		<title>${o.name }</title>
	</head>
	<body>
	<div id="hk">
		<div style="font-size: 20px">${o.name } 域名正在等待您来购买 <br/> <br/></div>
		<div style="font-size: 20px">
		如果您想拥有本域名,请直接联系wangdongfeng#gmail.com或18830396#qq.com(请把#换成@)<br/>
		 关于我，王东烽，请访问我的微博： <br/>
		 <a href="http://www.weibo.com/bosee" target="_blank">www.weibo.com/bosee</a>
		</div><br/><br/>
		<div style="font-size: 16px">
		${o.descr }
		</div> <br/><br/>
	</div>
	</body>
</html>