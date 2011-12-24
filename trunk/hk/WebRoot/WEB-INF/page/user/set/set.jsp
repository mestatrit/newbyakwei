<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="设置 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<table class="list"><tbody>
	<tr class="even"><td>系统设置</td></tr>
	<tr class="odd"><td>
	<div class="hang">
	<form name="mod" method="get" action="<%=request.getContextPath() %>/user/set/set_setclient.do">
		选择主题:<br/>
		<hk:select name="cssColorId" checkedvalue="${hkStatus.cssColorId}">
			<c:forEach var="c" items="${csslist}">
				<hk:option value="${c.cssColorId}" data="${c.name}"/>
			</c:forEach>
		</hk:select><br/><br/>
		选择模式:<br/>
		<hk:select name="showModeId" checkedvalue="${hkStatus.showModeId}">
			<c:forEach var="c" items="${showlist}">
				<hk:option value="${c.modeId}" data="${c.name}"/>
			</c:forEach>
		</hk:select><br/>
		<hk:submit value="保存" name="save"/>
	</form>
	</div>
	
	</td></tr>
	<tr class="even"><td>个人设置</td></tr>
	<tr class="odd"><td>
	<div class="hang"><hk:a href="/user/set/set_toSetNickName.do">修改昵称</hk:a></div>
	<div class="hang">
		<img src="${user.head32Pic }?<%=Math.random() %>"/><hk:a href="/user/set/set_toSetHead.do">上传头像</hk:a>
	</div>
	<div class="hang">
		<c:if test="${validateemail}">E-mail已认证,<hk:a href="/user/set/set_toSetEmail.do">更换E-mail</hk:a></c:if>
		<c:if test="${!validateemail}">E-mail未认证,<hk:a href="/user/set/set_toSetEmail.do">马上认证</hk:a></c:if>
	</div>
	<c:if test="${!mobileBind}"><div class="hang"><hk:a href="/user/set/set_toSetMobile.do">绑定手机号</hk:a></div></c:if>
	<c:if test="${mobileBind}"><div class="hang">成功绑定,<hk:a href="/user/set/set_toChgMobile.do">更换手机号</hk:a></div></c:if>
	<div class="hang"><hk:a href="/user/set/set_toSetMsn.do">绑定MSN</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_toSetNoticeInfo.do">设置消息提醒</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_toSetInfo.do">修改个人信息</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_toSetPwd.do">修改密码</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_toSetProtect.do">设置密码保护</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_toAbolishUser.do">合并账号</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set_tosetlabartflg.do">引用回复符号设置</hk:a></div>
	<c:if test="${regCode==null}"><div class="hang"><hk:a href="/user/set/set_tosetregcode.do?noac_method=set">设置邀请码</hk:a></div></c:if>
	</td></tr>
	</tbody></table>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>