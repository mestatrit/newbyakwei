<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path=request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/a.css" />
		<link rel="shortcut icon" href="<%=path %>/favicon.ico?v=2" />
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
		<title>${html_title }</title>
	</head>
	<body ${body_attr}>
		<div id="hk"><iframe id="hideframe" name="hideframe" class="hide"></iframe>
			<div id="hkinner">
				<div id="header">
					<div id="top">
						<ul id="topNav">
							<c:if test="${loginUser!=null}">
								<li class="first withImage">
									<a href="/user/${loginUser.userId }/"><img alt="${loginUser.nickName }" title="${loginUser.nickName }" src="${loginUser.head32Pic }"/></a>
									<a href="/user/${loginUser.userId }/"><hk:data key="view2.myhome"/></a>
								</li>
								<li><a href="/venue/search"><hk:data key="view2.add_things"/></a></li>
								<li><a href="<%=path %>/h4/op/msg.do">私信</a></li>
								<li><a href="/invite">邀请</a></li>
								<li><a href="<%=path %>/h4/op/notice.do"><hk:data key="view2.notice"/></a></li>
								<li><a href="<%=path %>/h4/op/user/set.do"><hk:data key="view2.user.setting"/></a></li>
								<li><a href="/overview"><hk:data key="view2.help"/></a></li>
								<li class="last"><a href="/logout"><hk:data key="view2.logout"/></a></li>
							</c:if>
							<c:if test="${loginUser==null}">
								<li class="first"><a href="/index"><hk:data key="view2.home"/></a></li>
								<li><a href="/signup"><hk:data key="view2.signup"/></a></li>
								<li><a href="/login"><hk:data key="view2.login"/></a></li>
								<li class="last"><a href="/overview"><hk:data key="view2.help"/></a></li>
							</c:if>
						</ul>
						<div id="logo">
							<a href="/index"><img src="<%=path %>/webst4/img/huoku.png" style="vertical-align: bottom;"/></a>
							内测
							<a href="/m/">手机版</a>
						</div>
	                    <div class="searchbox">
							<form method="get" action="/search" onsubmit="return sub_top_searchfrm(this)">
								<hk:hide name="ch" value="1"/><c:set var="search_defvalue"><hk:data key="view2.search.defvalue"/></c:set>
								<input  onfocus="this.value=''" onblur="if(this.value==''){this.value='${search_defvalue }'}" type="text" maxlength="20" name="key" class="text s_txt"  value="${search_defvalue }"/>
								<input type="submit" value="搜索" class="searchbtn" />
							</form>
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
                        	<c:if test="${loginUser!=null}">
                            <li><a href="/user/${loginUser.userId }/"><hk:data key="view2.myhome"/><c:if test="${to_myhome && loginUser.userId==userId}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                        	</c:if>
                            <li><a href="/index"><hk:data key="view2.home"/><c:if test="${to_index}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                            <li><a href="/venues"><hk:data key="view2.zuji"/><c:if test="${to_venue}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                            <c:if test="${loginUser==null}">
	                            <li><a href="/laba/all"><hk:data key="view2.laba"/><c:if test="${to_laba}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                            </c:if>
                            <c:if test="${loginUser!=null}">
	                            <li><a href="/laba/follow"><hk:data key="view2.laba"/><c:if test="${to_laba}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                            </c:if>
                            <li><a href="/feed"><hk:data key="view2.allfeed"/><c:if test="${to_feed}"><img src="<%=path %>/webst4/img/headerCarrot.png" id="carrot" /></c:if></a></li>
                        </ul>
                        <div id="location">
                            <span class="split-r f_l"><hk:data key="view2.currentlocation"/></span>
                            <c:set var="mylocation"><c:if test="${sys_zone_pcity!=null}">${sys_zone_pcity.name}</c:if><c:if test="${sys_zone_pcity==null}">？</c:if></c:set>
							<span class="f_l" id="mylocation"><a id="locaiont_value" title="<hk:data key="view2.clicktosetlocation"/>" href="javascript:tosetmylocation()">${mylocation }</a></span>
						</div>
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
					${html_body_content }
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