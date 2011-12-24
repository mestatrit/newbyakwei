<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="邀请 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">邀请</div>
	<div class="hang odd">
		<c:if test="${user!=null}">
			<div class="hang">
			<span class="orange">您邀请的好友已经在火酷注册:</span>
			<hk:a href="/home.do?userId=${user.userId}">${user.nickName}</hk:a>
			</div>
		</c:if>
		<!--
		<div class="hang">
			<hk:form action="/invite/invite_create.do">
				通过E-mail邀请朋友<br/>
				<hk:text name="email" maxlength="50"/><br/>
				<hk:submit value="发送E-mail"/>
			</hk:form>
		</div>
		<div class="hang">
			通过短信邀请朋友<br/>
			<hk:form action="/invite/invite_sendInviteSms.do">
				<hk:submit value="发送短信到手机"/>
			</hk:form>
		</div> 
		 -->
		 
		 <div>
		<c:if test="${userInviteConfig.inviteNum>0}">
			您目前还有 ${userInviteConfig.inviteNum } 个邀请码，<hk:a href="/invite/invite_createinvitecode.do">生成一个邀请链接</hk:a>
		</c:if>
		<c:if test="${userInviteConfig.inviteNum==0 && fn:length(invitecodelist)==0}">
			您已经没有邀请码了
		</c:if>
		<c:if test="${fn:length(invitecodelist)>0}">
			<div>我的邀请链接:
			<c:forEach var="i" items="${invitecodelist}">
				<div style="margin-bottom: 10px;"><input id="inviteCode_${i.oid }" type="text" onmouseover="this.focus();" onfocus="this.select();" style="margin-bottom: 0;width: 300px;" value="http://<%=HkWebConfig.getWebDomain() %>/r/invite/${i.data}"/> <a href="javascript:cplink(document.getElementById('inviteCode_${i.oid }').value)">复制</a></div>
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
		 <!-- 
			<div class="hang">
				链接邀请,复制并发送msn、qq好友<br/>
				<input type="text" id="url" size="100" value="http://<%=HkWebConfig.getWebDomain() %>/hi/${ouser.domain }"/><br/>
				<input type="button" value="复制" onclick="cplink(document.getElementById('url').value)"/>
			</div>
		  -->
	</div>
	<div class="hang"><hk:a href="/invite/invite.do">成功的邀请</hk:a>|<hk:a href="/invite/invite_all.do">邀请记录</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
<script type="text/javascript">
<!--
var browser="";
if(navigator.userAgent.toLowerCase().indexOf('msie') != -1){browser="ie";}else{browser="n";}
function cplink(v){
	if(browser!="ie"){
		alert("您的浏览器不支持复制，请手动选中文字，进行复制");
		return;
	}
  	window.clipboardData.setData("Text",v);
  	window.alert("地址已复制到剪贴板，您可以通过MSN QQ发送给好友");
}
//-->
</script>
</hk:wap>