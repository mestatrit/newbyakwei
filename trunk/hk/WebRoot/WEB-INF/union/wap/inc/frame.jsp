<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>${cmpUnion.name}</title>
<style type="text/css">
html, body, p,form, img ,div,span,table,tr,td,th,a{margin: 0;padding: 0;border: 0;}
body{font-size:medium;}
table{border-collapse:collapse;margin: 0px;}
table.list{width: 100%}
td{vertical-align:top;padding:0.3em;padding-right: 0px}
*{line-height:1.4em;}
p{line-height:1.4em;}
img{vertical-align:middle;}
a {color:#039;text-decoration:none;}
a:hover, a:active, a:focus {text-decoration:none;}
.row{padding: 0.3em;}
.nav2{padding: 0.3em;background-color: #E5E5E5;}
.split-r{margin-right: 10px;}
.top{padding: 0.3em;background-color: #666666;border-bottom: 1px solid #000000;color: #ffffff;}
.top a{color: #ffffff;text-decoration: underline;}
.top a.noline{text-decoration:none}
.foot{padding: 0.3em;border-top:2px double #C5C5C5;background-color: #E5E5E5}
.foot2{padding: 3px 0 4px 4px;border-top: 1px solid #000000;background-color: #666666;color: #ffffff}
.foot2 a{color:#ffffff;text-decoration: underline;}
.img-icon{padding: 0 3px 4px 0;}
.warn{padding:4px 3px;padding-left:4px;background-color:#FFFFAD;}
.warn a{text-decoration:none;color:#357BDD;}
.s{font-size: small;}
.odd,.odd td{background:#fff}
.even,.even td{background:#eee}
.reply,.reply td{background:#ffa}
.reply,.reply.even td{background:#dd9}
.ruo{color:#555;}
td.h0{width:32px;}
td.h0 img{width:32px;height:32px}
td.userimg{width: 50px;height: 50px;}
.info{color: #CC0000;}
.rowipt{width: 99%}
.alert{color:red}
</style>
</head>
<body>
<jsp:include page="../inc/top.jsp"></jsp:include>
<div>${html_main_content }</div>
<jsp:include page="../inc/foot.jsp"></jsp:include>
</body>
</html>