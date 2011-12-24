<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%String path=request.getContextPath(); %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/a.css?v=1" />
		<link rel="icon" href="<%=path %>/favicon.ico" mce_href="<%=path %>/favicon.ico" type="image/x-icon"/>
		<link rel="shortcut icon" href="<%=path %>/favicon.ico" ce_href="<%=path %>/favicon.ico" type="image/x-icon"/>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/pub.js"></script>
		<script type="text/javascript">
		var path="<%=path %>";
		var userLogin=false;
		<c:if test="${userLogin}">
		userLogin=true;
		</c:if>
		</script>
		<c:if test="${js_value!=null}">${js_value}</c:if>
		<title>登录 - 火酷网</title>
	</head>
	<body ${body_attr}>
		<div id="hk"><iframe id="hideframe" name="hideframe" class="hide"></iframe>
			<div id="hkinner">
				<div id="header">
					<div id="top">
						<div id="logo">
							<img src="<%=path %>/webst4/img/huoku.png" style="vertical-align: bottom;"/>
						</div>
						<script type="text/javascript">
							function sub_top_searchfrm(frm){
								if(frm.key.value=='${search_defvalue }'){
									frm.key.value="";
								}
								return true;
							}
						</script>
					</div>
					<div id="bottom">
                        <ul id="mainNav">
                        </ul>
                    </div>
                    <div id="bottomGrad"></div>
				</div>
				<div id="body">
					<br class="linefix" />
					<%
						int count =0;
						int noticeCount=0;
						count=HkWebUtil.getNewMsgCount(request);
						noticeCount=HkWebUtil.getNoReadNoticeCount(request);
						String msg = MessageUtil.getMessage(request);
						Boolean nickname_duplicate=(Boolean)session.getAttribute("nickname_duplicate");
						session.removeAttribute("nickname_duplicate");
						if(nickname_duplicate!=null){
							request.setAttribute("nickname_duplicate",nickname_duplicate);
						}
					%>
					<%if(msg!=null || count>0 || noticeCount>0){ %>
					<div class="alerts_notice">
						<c:if test="${nickname_duplicate}">
						<%if(count>0){ %><a href="<%=path %>/h4/op/msg_last.do"><%=msg %></a><%} %>
						</c:if>
						<c:if test="${nickname_duplicate==null || !nickname_duplicate }">
							<%if(count>0){ %><div>您有<%=count %>封未读私信,<a href="<%=path %>/h4/op/msg_last.do">点击查看</a></div><%} %>
							<%if(noticeCount>0){ %><div>您有<%=noticeCount %>个新通知,<a href="/h4/op/notice.do">点击查看</a></div><%} %>
							<%if(msg!=null){ %><%=msg %><%} %>
						</c:if>
					</div>
					<%} %>
					<div>
<form id="loginfrm" method="post" onsubmit="return subloginfrm(this.id)" action="/login" target="hideframe">
<hk:hide name="ch" value="1"/>
<table cellpadding="0" cellspacing="0">
<tr>
	<td width="150px" align="right">
	</td>
	<td>
	<img src="<%=path %>/webst4/img/baoxiang.png"/><br/>
						更多惊喜，即将发布！
	</td>
</tr>
<tr>
	<td width="150px" align="right">
		<hk:data key="view2.login.input"/>
	</td>
	<td><hk:text name="input" maxlength="50" clazz="text" value="${input}"/></td>
</tr>
<tr>
	<td width="150px" align="right">
		<hk:data key="view2.password"/>
	</td>
	<td><hk:pwd name="password" maxlength="50" clazz="text"/></td>
</tr>
<tr>
	<td width="150px" align="right"></td>
	<td>
		<div id="logininfo" class="infowarn"></div>
		<hk:submit value="view2.login" res="true" clazz="btn"/> 
		<a href="<%=path %>/h4/pwd.do">忘记密码</a>
	</td>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
function subloginfrm(frmid){
	setHtml('logininfo','');
	showGlass(frmid);
	return true;
}
function loginok(error,error_msg,respValue){
	tourl('<%=path%>/h4/login_checkip.do?selpcityid=1&return_url=${return_url}');
}
function loginerror(error,error_msg,respValue){
	setHtml('logininfo',error_msg);
	hideGlass();
}
</script>
				</div>
				<div id="footer">
					<div>
						<a>关于火酷</a> |
						<a> 广告服务</a> |
						<a>招聘信息</a> |
						<a>建议和意见</a> |
						<a>友情链接</a> |
						<a>联系我们 </a> 
					</div>
					<div>
						* Copyright ® 2009 huoku.com All rights reserved 京ICP备09054036号
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function tosetmylocation(){
				setHtml('mylocation','<form id="locationfrm" method="post" onsubmit="return sublocationfrm(this.id)" action="<%=path %>/index_changepcity.do" target="hideframe"><input type="text" name="zoneName"/><input type="submit" value="<hk:data key="view2.submit"/>" class="btn"/> <a href="javascript:hidelocationfrm()"><hk:data key="view2.cancel"/></a></form>');
				getObj('locationfrm').zoneName.focus();
			}
			function hidelocationfrm(){
				setHtml('mylocation','<a id="locaiont_value" title="<hk:data key="view2.clicktosetlocation"/>" href="javascript:tosetmylocation()">${mylocation }</a>');
			}
			function sublocationfrm(frmid){
				return true;
			}
			function onlocationok(error,s,v){
				refreshurl();
			}
			function onlocationerror(error,s,v){
				if(error==<%=Err.ZONE_CITY_NOTFOUND %>){
					tourl("<%=path %>/index_selzone.do?provinceId="+v+"&return_url="+encodeLocalURL());
				}
				else {
					tourl("<%=path %>/index_selzone.do?countryId="+v+"&return_url="+encodeLocalURL());
				}
			}
		</script>
	</body>
</html>