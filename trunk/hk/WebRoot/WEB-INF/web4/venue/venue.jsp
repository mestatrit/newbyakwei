<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<meta name="keywords" content="${company.name }|<hk:data key="view2.website.title"/>"/>
<meta name="DEscription" content="${company.introRow }"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/gmlocalsearch.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/gsearch.css" />
<script type="text/javascript">
var view2_clrinvenue_tip='<hk:data key="view2.clrinvenue.tip"/>';
var view2_add_as_todo="<hk:data key="view2.add_as_todo"/>";
var view2_i_done_this2="<hk:data key="view2.i_done_this2"/>";
var view2_i_done_this="<hk:data key="view2.i_done_this"/>";
var CompanyUserStatus_USERSTATUS_WANT=<%=CompanyUserStatus.USERSTATUS_WANT %>;
var CompanyUserStatus_USERSTATUS_DONE=<%=CompanyUserStatus.USERSTATUS_DONE %>;
var view2_pleast_input_otherbuilding_for_search="<hk:data key="view2.pleast_input_otherbuilding_for_search"/>";
var companyId=${companyId};
var companyname="${company.name}";
var marker_x=${company.markerX};
var marker_y=${company.markerY};
var map = null;
var marker=null;
var geocoder = null;
var findforcity=false;//根据城市查询地图
var tmp_mapid='';
var last_point=null;
var canedit=false;
var isShowBig=false;
<c:if test="${canedit}">
canedit=true;
</c:if>
function initialize() {
// 创建“微型”标记图标
var blueIcon = new GIcon(G_DEFAULT_ICON);
blueIcon.image = "<%=path %>/webst4/img/google_blank.png";
// 设置 GMarkerOptions 对象
markerOptions = { icon:blueIcon,draggable: canedit };
markerOptions2 = { icon:blueIcon,draggable: false };
<c:if test="${company.markerX!=0}">
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map_canvas"))
		var center=new GLatLng(marker_x,marker_y);
		last_point=center;
		map.setCenter(center, 14);
		map.addControl(new GSmallMapControl());
		marker = new GMarker(center, markerOptions2);
		map.addOverlay(marker);
	}
</c:if>
<c:if test="${company.markerX==0}">
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map_canvas"));
		geocoder = new GClientGeocoder();
		findByAddr('${company.pcity.name} ${company.name}','${company.pcity.name} ${company.addr}',false);
	}
</c:if>
}
</script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/js4/venue.js"></script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/js4/tip.js"></script>
</c:set><c:set var="body_attr" scope="request">onunload="GUnload()"</c:set><c:set var="html_title" scope="request">${company.name}</c:set><c:set var="html_body_content" scope="request">
<div class="userbody">
<div id="simpletraffic" class="hide">${company.simpleTraffic}
<a class="s" href="javascript:showmoretraffic()"><hk:data key="view2.venue.traffic.showall"/></a>
</div>
<div id="moretraffic" class="hide">${company.traffic }
<a class="s" href="javascript:hidemoretraffic()"><hk:data key="view2.venue.traffic.helong"/></a>
</div>
	<div class="f_l" style="width: 300px">
		<h1>${company.name }</h1>
	<c:if test="${cmpInfo!=null}">
		<a target="_blank" href="http://www.${cmpInfo.domain }">www.${cmpInfo.domain }</a>
	</c:if>
		<div>
			${country.country} ${province.province} ${company.pcity.name} - ${company.addr }<br />
			${company.tel }<br/>
			<c:if test="${not empty company.traffic}">
			<div id="venue_traffic">
				${company.simpleTraffic }
				<c:if test="${company.moreTraffic}">
					<a class="s" href="javascript:showmoretraffic()"><hk:data key="view2.venue.traffic.showall"/></a>
				</c:if>
			</div>
			</c:if>
			<div id="venue_intro">
				<c:if test="${not empty company.intro}">
					${company.simpleIntro} 
					<c:if test="${company.moreIntro}">
						<a class="s" href="/venue/${companyId }/intro"><hk:data key="view2.moreinfo"/></a>
					</c:if>
					<br/>
				</c:if>
			</div>
			<c:if test="${canedit}">
			<a class="split-r" href="<%=path %>/h4/op/user/venue_editvenue.do?companyId=${companyId}"><hk:data key="view2.edit_venue_info"/></a>
			<a href="<%=path %>/h4/op/venue/cmp.do?companyId=${companyId}">管理</a>
			</c:if>
			<c:if test="${company.userId==0}">
			<div><a href="javascript:toauth()">如果这是您的商户，马上认领!</a></div>
			<script type="text/javascript">
			function toauth(){
				tourl('<%=path%>/h4/op/authcmp.do?companyId=${companyId}&return_url='+encodeLocalURL());
			}
			</script>
			</c:if>
			<c:if test="${cmpRefUser!=null && cmpInfo!=null}">
			<a href="javascript:clrinvenue(${companyId })"><hk:data key="view2.clrinvenue"/></a>
			</c:if>
		</div>
	</div>
	<div class="r" style="width: 560px">
		<div class="f_r wdbreak" style="width: 414px;">
			<c:if test="${mayor!=null}">
			<div class="statbox">
				<div class="title"><hk:data key="view2.majorship"/></div>
				<div class="content">
					<div style="width: 75px;">
						<a href="/user/${mayor.userId }/"><img width="55" height="55" src="${mayor.head80Pic }" /></a>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${loginUser!=null}">
				<div class="statbox">
					<div class="title"><hk:data key="view2.myvisitcount"/></div>
					<div class="content">${usercheckincount }</div>
				</div>
			</c:if>
			<div class="statbox">
				<div class="title"><hk:data key="view2.unique.usercount"/></div>
				<div class="content">${usercount }</div>
			</div>
			<div class="statbox">
				<div class="title"><hk:data key="view2.checkin.total"/></div>
				<div class="content">${checkincount }</div>
			</div>
			<div class="clr"></div>
		</div>
		<div class="f_r" style="position: relative;width: 126px;">
			<div style="margin-bottom: 10px;">
				<input type="button" class="btn4" value="<hk:data key="view2.checkin"/>" onclick="tourl('<%=path %>/h4/op/user/venue_checkequ.do?companyId=${companyId }')"/>
				<div id="checkin_tip" class="floattip" style="display: none;width: 270px"></div>
			</div>
			<c:if test="${userCmpPoint!=null}">
				<div style="margin-bottom: 10px;text-align: center;">
					<a class="b" href="/overview#points">
					<hk:data key="view2.userpointsincmp" arg0="${userCmpPoint.points }"/></a>
				</div>
			</c:if>
			<div style="font-size: 14px">
				<c:if test="${companyUserStatus==null}">
					<div id="div_cmpuserstatus_done" class="tip_todo_unchecked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
					<div id="div_cmpuserstatus_want" class="tip_todo_unchecked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
				</c:if>
				<c:if test="${companyUserStatus!=null}">
					<c:if test="${companyUserStatus.done}">
						<div id="div_cmpuserstatus_done" class="tip_checked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)" checked="checked"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
					</c:if>
					<c:if test="${!companyUserStatus.done}">
						<div id="div_cmpuserstatus_done" class="tip_todo_unchecked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
					</c:if>
					<c:if test="${companyUserStatus.want}">
						<div id="div_cmpuserstatus_want" class="tip_checked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)" checked="checked"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
					</c:if>
					<c:if test="${!companyUserStatus.want}">
						<div id="div_cmpuserstatus_want" class="tip_todo_unchecked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
<div class="b_l">
	<div class="mod">
		<div id="jscon">
		</div>
		<div style="height: 200px;border:1px solid #888888;position: relative;">
			<div id="map_canvas" style="height: 200px;"></div>
			<div class="mapstatus">
			<a href="javascript:showBigMap()">大图</a>
			<c:if test="${canedit}">
			 | <a href="javascript:editMap()"><hk:data key="view2.edit"/></a>
			</c:if>
			</div>
		</div>
	</div>
	<div class="mod">
		<div class="mod_title"><hk:data key="view2.people.tips"/></div>
		<div class="mod_content">
			<c:if test="${fn:length(cmptipvolist)==0}">
				<div><hk:data key="view2.no_tips_in_this_place"/></div>
			</c:if>
			<%request.setAttribute("force_me",true); %>
			<jsp:include page="../inc/cmptipvolist_inc.jsp"></jsp:include>
			<c:if test="${more_tip}">
				<a class="more2" href="/venue/${companyId }/tips/2"><hk:data key="view2.more"/></a>
			</c:if>
			<br />
			<a class="b" href="/createtip?doneflg=<%=CmpTip.DONEFLG_DONE %>&companyId=${companyId }"><hk:data key="view2.add_tip"/></a> /
			<a class="b" href="/createtip?doneflg=<%=CmpTip.DONEFLG_TODO %>&companyId=${companyId }"><hk:data key="view2.add_todo"/></a>
		</div>
	</div>
</div>
<div class="b_r">
		<div class="mod">
			<div class="mod_title">
				<c:if test="${userLogin}">
				<a class="fn" href="<%=path %>/h4/op/user/venue_uploadpic.do?companyId=${companyId}"><img src="<%=path %>/webst4/img/uploadpic.png"/><hk:data key="view2.company.uploadpic"/></a>
				</c:if>
				<c:if test="${photoCount>0}">
				<div class="f_r"><a class="fn" href="<%=path %>/h4/venue_first.do?companyId=${companyId}"><hk:data key="view2.venue.photocount" arg0="${photoCount}"/></a></div>
				<div class="clr"></div>
				</c:if>
			</div>
			<div class="mod_content" align="center">
				<c:if test="${photoCount>0}">
					<div id="headdiv">
					<a href="<%=path %>/h4/venue_first.do?companyId=${companyId}"><img id="cmphead" alt="${company.name }" title="${company.name }" src="${company.head320 }" onerror="load240();"></a>
					<script type="text/javascript">
					function load240(){
						var html='<a href="<%=path %>/h4/venue_first.do?companyId=${companyId}"><img id="cmphead" alt="${company.name }" title="${company.name }" src="${company.head240 }"></a>';
						setHtml('headdiv',html);
					}
					</script>
					</div>
				</c:if>
			</div>
		</div>
	<div class="mod">
		<div class="mod_title">
			<hk:data key="view2.venue.whointhsplace"/>
		</div>
		<div class="mod_content">
			<c:if test="${fn:length(cmpcheckinuserlist)==0}">
			<hk:data key="view2.venue.nouser_ever_in_this_place"/>
			</c:if>
			<c:if test="${fn:length(cmpcheckinuserlist)>0}">
				<ul class="smallhead">
					<c:forEach var="in" items="${cmpcheckinuserlist}">
						<li><a href="/user/${in.userId }/"><img alt="${in.user.nickName }" title="${in.user.nickName }" src="${in.user.head32Pic }"></a></li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</c:if>
		</div>
	</div>
	<div class="mod">
		<div class="mod_title">
			<hk:data key="view2.venue.tag"/>
		</div>
		<div class="mod_content">
			<c:forEach var="t" items="${cmptagreflist}">
			<div class="tagbox">
				<a href="/venue/tag/${t.tagId }" class="b">${t.name }</a>
				<c:if test="${t.userId==loginUser.userId}">
					[<a href="javascript:deltag(${t.tagId })">X</a>]
				</c:if>
			</div>
			</c:forEach>
			<div id="tagline" class="clr"></div>
			<c:if test="${userLogin}">
				<hk:data key="view2.venue.tag.tip"/><br />
				<form id="tagfrm" method="post" onsubmit="return subtagfrm(this.id)" action="/h4/op/user/venue_createtag.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/>
					<input id="tagipt" type="text" name="name" maxlength="20" class="text3" />
					<hk:submit clazz="btn3" value="view2.submit" res="true"/>
				</form>
			</c:if>
		</div>
	</div>
	<c:if test="${fn:length(todocmptipvolist)>0}">
		<div class="mod">
			<div class="mod_title">
				<a href="/venue/${companyId }/todo"><hk:data key="view2.venue_user_todolist"/></a>
				<c:if test="${more_todo}">
				<a class="more" href="/venue/${companyId }/todo"><hk:data key="view2.more"/></a>
				</c:if>
			</div>
			<div class="mod_content">
				<c:forEach var="todovo" items="${todocmptipvolist}">
					<div class="divrow bdtm">
						<a href="/user/${todovo.cmpTip.userId }"><img title="${todovo.cmpTip.user.nickName }" alt="${todovo.cmpTip.user.nickName }" src="${todovo.cmpTip.user.head32Pic }"/></a>
						${todovo.cmpTip.simpleContent }
						<c:if test="${todovo.cmpTip.moreContent}">
						<a href="/item/${todovo.cmpTip.tipId }"><hk:data key="view2.cmptip.all"/></a>
						</c:if>
						<span class="ruo"><fmt:formatDate value="${todovo.cmpTip.createTime}" pattern="yy-MM-dd"/></span>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
</div>
<div class="clr"></div>
<script src="http://maps.google.com/maps?file=api&v=2&sensor=false&hl=zh-CN&key=<%=HkWebConfig.getGoogleApiKey() %>" type="text/javascript"></script>
<script src="http://www.google.com/uds/api?file=uds.js&v=1.0" type="text/javascript"></script>    
<script src="http://www.google.com/uds/solutions/localsearch/gmlocalsearch.js" type="text/javascript"></script>
<script type="text/javascript">
delay("initialize()", 2000);
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>