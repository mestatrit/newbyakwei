<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.invite"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 750px">
<div>
	<div>
		<c:if test="${userInviteConfig.inviteNum>0}">
			您目前还有<span class="infowarn b">${userInviteConfig.inviteNum }</span>个邀请码，
			<a href="<%=path %>/h4/op/invite_createinvitecode.do">生成一个邀请链接</a>
		</c:if>
		<c:if test="${userInviteConfig.inviteNum==0 && fn:length(invitecodelist)==0}">
			您已经没有邀请码了，赶快去 <a class="b" href="/venue/search"><hk:data key="view2.addvenueandtip"/></a>，来获得更多的邀请码
		</c:if>
		<c:if test="${fn:length(invitecodelist)>0}">
			<div>我的邀请链接:
			<c:forEach var="i" items="${invitecodelist}">
				<div style="margin-bottom: 10px;"><input id="inviteCode_${i.oid }" type="text" onmouseover="this.focus();" onfocus="this.select();" class="text" style="margin-bottom: 0;" value="http://<%=HkWebConfig.getWebDomain() %>/r/invite/${i.data}"/> <a href="javascript:cplink(getObj('inviteCode_${i.oid }').value)">复制</a></div>
			</c:forEach>
			复制链接并发给我qq、msn、gtalk、skype好友<br/>
			或者只告诉朋友邀请码即：
			<hk:rmstr value="、">
				<c:forEach var="i" items="${invitecodelist}">
					${i.data}、
				</c:forEach>
			</hk:rmstr>
			，注册的时候填入即可。
			</div>
		</c:if>
	</div>
	<div style="padding-top: 20px;">
		<h1 class="bdtm"><hk:data key="view2.invitelogforsuccess"/></h1>
	</div>
	<div>
		<c:if test="${fn:length(list)==0}"><div class="b"><hk:data key="view2.noinvitedata"/></div></c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="i" items="${list}" varStatus="idx">
				<div class="divrow" style="height: 100%;" onmouseover="this.className='divrow bg2';" onmouseout="this.className='divrow bg0';">
					<a href="/user/${i.friendId }/" class="b"><img src="${i.friend.head32Pic }"/> ${i.friend.nickName }</a>
					<fmt:formatDate var="t" value="${i.createTime}" pattern="yy-MM-dd HH:mm" scope="request"/>
					<hk:data key="view2.invite.info" arg0="${t}"/>
				</div>
			</c:forEach>
		</c:if>
		<c:set var="url_rewrite" scope="request">true</c:set>
		<c:set var="page_url" scope="request">/invite</c:set>
	<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
var browser="";
if(navigator.userAgent.toLowerCase().indexOf('msie') != -1){browser="ie";}else{browser="n";}
function cplink(v){
	if(browser!="ie"){
		alert("您的浏览器不支持复制，请手动选中链接文字，进行复制");
		return;
	}
  	window.clipboardData.setData("Text",v);
  	window.alert("地址已复制到剪贴板，您可以通过MSN QQ发送给好友");
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>