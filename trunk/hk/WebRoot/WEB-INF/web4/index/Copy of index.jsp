<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.frame.util.DataUtil"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<style type="text/css">
.index_user{
	float: left;
	width: 60px;
	overflow: hidden;
	text-align: center;
	margin-right: 20px;
	line-height: 20px;
}
.index_user img{
width: 60px;
height: 60px;
}
.index_user .index_user_con{
text-align: left;
font-size: 12px;
}


.showbg{// fadeout 效果背景设定 *可设也可以不设
background-color: black;
}

.link_nor{// 数字超联结的设定
background-color: #333333;
font: normal 76% "Arial", "Lucida Grande",Verdana, Sans-Serif;
border: 2px solid #808000;
padding:0px 4px 0px 4px;
text-align:center;
color:white
}


.link_act
{//数字超联结目前正执行的连结设定
background-color:#808000;
font: normal 76% "Arial", "Lucida Grande",Verdana, Sans-Serif;
border: 2px solid #808000;
padding:0px 4px 0px 4px;
text-align:center;
color:white
}

span{// 水印
font: normal 76% "Arial", "Lucida Grande",Verdana, Sans-Serif;
color:black
}

.gray
{// 封面
background-color: gray;
position: absolute;
border: 0px solid #9F6D11;
z-index: 100;
}

img{// 图片边框
border: 0px solid #663300;
background-color: #663300
}

.opa{// 水印的透明度
color:white;
padding:10px 20px 0px 20px;
position: absolute;
z-index: 101;
background-color: #FFA928;
opacity:0.5;
filter: alpha(opacity=50);
filter:progid:DXImageTransform.Microsoft.Alpha
(Opacity=100,FinishOpacity=50, Style=3, StartX=0, FinishX=100, StartY=0,FinishY=16);
-moz-opacity: 0.5;
}
</style>
</c:set>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title" /></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<div>
		<div class="mod">
			<div class="f_l bd" style="width: 500px">
				<c:if test="${fn:length(boxlist)>0}">
					<div id=imgstore  style="display:none">
						<img src=img/img/00031.jpg title="Howe Bay">
						<img src=00032.jpg title="Orange Morning" >
						<img src=00033.jpg title="Red Leaves">
						<img src=00034.jpg title="waterscape">
						<img src=00035.jpg title="Clashing Fronts">
						<img src=00036.jpg title="Autumn Mist">
						<img src=00037.jpg title="Desert dry 1">
						<img src=00038.jpg title="Ferns 2">
					</div>
					<div id=showhere></div>




				</c:if>
				<c:if test="${fn:length(boxlist)==0}">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
						<td valign="middle" align="center">
						<img src="<%=path %>/webst4/img/baoxiang.png"/><br/>
						更多惊喜，即将发布！
						</td>
						</tr>
					</table>
				</c:if>
			</div>
			<div class="f_r bd" style="">
				<div style="margin-bottom: 15px">火酷可以告诉你大家喜欢去哪里，哪些地方最火，人气最旺。</div>
				<div style="margin-bottom: 15px">当你去一个地方的时候，通过手机报到一下，你将能结识经常去这里的朋友。</div>
				<div style="margin-bottom: 20px">通过报到，你可以得到徽章，还可以成为这个地方的地主，享受特别奖励。</div>
				<c:if test="${userLogin}">
					<div>1、<a href="/invite">邀请</a>一个朋友来火酷，获得点数。</div>
					<div>2、对你去过的地方进行报到，获得点数。</div>
					<div>3、消耗点数可以免费开宝箱，获得各种奖励！</div>
				</c:if>
				<c:if test="${!userLogin}">
					<div>
						<form id="loginfrm" method="post" onsubmit="return subloginfrm(this.id)" action="/login" target="hideframe">
							<hk:hide name="ch" value="1"/>
							<table cellpadding="0" cellspacing="0">
							<tr>
								<td width="50px" align="right">
									<hk:data key="view2.login.input"/>
								</td>
								<td><hk:text name="input" maxlength="50" clazz="text" value="${input}"/></td>
							</tr>
							<tr>
								<td align="right">
									<hk:data key="view2.password"/>
								</td>
								<td><hk:pwd name="password" maxlength="50" clazz="text"/></td>
							</tr>
							<tr>
								<td align="right"></td>
								<td>
									<div id="logininfo" class="infowarn"></div>
									<hk:submit value="view2.login" res="true" clazz="btn"/> 
									<a class="split-r" href="/signup"><hk:data key="view2.nothaveid_pleasesignup"/></a> 
									<a href="<%=path %>/h4/pwd.do">忘记密码</a>
								</td>
							</tr>
							</table>
						</form>
					</div>
				</c:if>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="index2">
	<c:if test="${fn:length(mayorlist)>0}">
		<div class="mod">
			<div class="mod_title"><hk:data key="view2.newmayors"/>，${sys_zone_pcity.name }</div>
			<div class="mod_content">
				<c:forEach var="m" items="${mayorlist}">
					<div class="index_user">
						<a href="/user/${m.userId }"><img src="${m.user.head80Pic }" alt="${m.user.nickName }" title="${m.user.nickName }"/></a>
						<div class="index_user_con">
							<a href="/user/${m.userId }">${m.user.nickName }</a> 在 
							<a href="/venue/${m.lastCmpCheckInUserLog.companyId }">${m.lastCmpCheckInUserLog.company.name }</a>
						</div>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(cmplist)>0}">
		<div class="mod">
			<div class="mod_title"><hk:data key="view2.cmp_of_userlike"/></div>
			<div class="mod_content">
				<c:forEach var="c" items="${cmplist}">
					<div class="index_user">
						<a href="/venue/${c.companyId }"><img src="${c.head60 }" alt="${c.name }" title="${c.name }"/></a>
						<div class="index_user_con">
							<a href="/venue/${c.companyId }">${c.name }</a>
						</div>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$.init_slide('imgstore','showhere',1,1,1000,1,5000,1,'_self');
	});

$.init_slide(firstname,secondname,watermark,iscover,speed,autoplay,playtime,clickable,mytarget);

* firstname 放置欲展示图片的对象ID
* secondname 图片展示位置的对象ID
* watermark : 是否显示水印(文字标题)(1 是使用)
* iscover : 图片转换是否使用封面(1是使用)
* speed : 图片封面的速度 (1000=1sec)
* autoplay : 是否自动展示 (1 是自动)
* playtime: 自动展示的间隔时间 (1000=1sec 内定是 6sec)
* clickable 是否显示可按的数字连结
* mytarget:连结开启的窗口(内定是 _blank)



function subloginfrm(frmid){
	setHtml('logininfo','');
	showGlass(frmid);
	return true;
}
function loginok(error,error_msg,respValue){
	//tourl('<%=path%>/h4/user_me.do');
	tourl('<%=path%>/h4/login_checkip.do?return_url=${return_url}');
}
function loginerror(error,error_msg,respValue){
	setHtml('logininfo',error_msg);
	hideGlass();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>