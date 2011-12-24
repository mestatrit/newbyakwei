<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="${user.nickName }的首页 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div>
	<table class="list"><tbody>
	<tr class="reply"><td>
	<hk:form method="post" action="/laba/op/op_create.do">
		<hk:hide name="userhome" value="1"/>
		<hk:hide name="userId" value="${userId}"/>
		<input type="hidden" name="lastUrl" value="/home.do?userId=${userId }"/>
		<c:if test="${!me && userLogin}">
			<hk:text name="content" clazz="ipt" maxlength="140" value="@${user.nickName} "/>
		</c:if>
		<c:if test="${me}">
			<hk:text name="content" clazz="ipt" maxlength="140"/>
		</c:if>
		<hk:submit value="提交" /> 
		<c:if test="${loginUser.userId!=userId && followed}">
			<hk:submit name="msg_submit" value="私信" />
			<c:if test="${canSms}"><hk:submit name="msgandsms_submit" value="短信" /></c:if>
		</c:if>
	</hk:form>
	<table>
		<tbody>
			<tr>
				<td><img src="${user.head48Pic }?${sessionScope.newhead }" alt="${user.nickName }" /></td>
				<td>
					${user.nickName }<br/>
					<hk:a clazz="s" href="/follow/follow.do?userId=${userId}">${friendCount }关注</hk:a>/<hk:a clazz="s" href="/follow/follow_re.do?userId=${userId}">${followCount }粉丝</hk:a><br/>
					<c:if test="${proUser!=null}">
						<c:set var="regtime"><fmt:formatDate value="${userOtherInfo.createTime}" pattern="yy-MM-dd"/></c:set>
						<hk:a href="/prouser.do?prouserId=${proUser.oid}"><hk:data key="view.redcarpet.content" arg0="${regtime}"/></hk:a><br/>
					</c:if>
					<c:if test="${not empty userOtherInfo.intro}">
						${userOtherInfo.intro }<br/>
					</c:if>
					<c:if test="${fn:length(glist)>0 }">
						圈子:<c:forEach var="g" items="${glist}"><hk:a href="/group/gulist.do?gid=${g.groupId}">${g.name}</hk:a> </c:forEach>
						<br/>
					</c:if>
					<c:if test="${!me && !areFriend && !blocked}">
						<div><hk:form method="get" action="/follow/op/op_add.do">
							<hk:hide name="userId" value="${userId}"/>
							<hk:submit value="关注"/>
						</hk:form></div>
					</c:if>
					<c:if test="${!me && areFriend}">
						<div><hk:form method="get" action="/follow/op/op_del.do">
							<hk:hide name="userId" value="${userId}"/>
							<hk:submit value="取消关注"/>
						</hk:form></div>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	</td>
	</tr>
	</tbody></table>
	</div>
	<div class="hang">
		<c:if test="${me}"><div><hk:a href="/user/set/set.do">设置</hk:a></div></c:if>
		<div><hk:a href="/${userId}/replies">回应</hk:a></div>
		<div><hk:a href="/laba/fav.do?userId=${userId}">收藏</hk:a></div>
		<c:if test="${hasinfo}">
			<div><hk:a href="/info/info.do?userId=${userId}">信息台</hk:a></div>
		</c:if>
		<c:if test="${!me}">
			<c:if test="${userLogin && !me && !blockedUser && areFriend && !blocked}">
				<div><hk:a href="/user/block/op/op_add.do?userId=${userId}">屏蔽</hk:a></div>
			</c:if>
			<c:if test="${userLogin && !me && blockedUser}">
				<div><hk:a href="/user/block/op/op_del.do?userId=${userId}">解除屏蔽</hk:a></div>
			</c:if>
		</c:if>
	</div>
	<c:if test="${not empty userport}">
		<div class="hang even">
			<hk:rmBlankLines rm="true">
				<hk:data key="view.hkusersmsport"/>:
				${userport } <hk:a clazz="line" href="/op/user_sms.do?userId=${userId}">发短信</hk:a>
			</hk:rmBlankLines>
		</div>
	</c:if>
	<c:if test="${loginUser.userId==userId}">
		<div class="hang">
			<hk:a href="/notice/notice.do">通知</hk:a>|<hk:a href="/feed/feed.do">动态</hk:a>
		</div>
	</c:if>
	<div class="hang">
		<hk:a href="/home.do?userId=${userId}"><hk:data key="view2.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>