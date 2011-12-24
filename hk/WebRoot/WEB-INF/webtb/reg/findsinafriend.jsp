<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">绑定应用
</c:set><c:set var="html_body_content" scope="request">
	<div class="reg_l">
		<div class="step"><h2>注册</h2></div>
		<div class="step"><h2>关注朋友</h2></div>
		<div class="step selected"><h2>绑定应用</h2><img class="selected" src="${ctx_path }/webtb/img/signup_arrow.gif" /></div>
	</div>
	<div class="reg_r">
		<div class="row">
			<div class="f_l">
				<img src="${sina_user.profileImageURL.protocol}://${sina_user.profileImageURL.host }${sina_user.profileImageURL.path  }"/>
			</div>
			<div class="f_l">
				${sina_user.screenName }<br/>
				${sina_user.location }<br/>
				<span class="split-r">${sina_user.friendsCount }关注</span>
				<span class="split-r">${sina_user.followersCount }粉丝</span>
				<span class="split-r">${sina_user.statusesCount }微博</span>
			</div>
			<div class="clr"></div>
		</div>
			<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${ctx_path }/tb/op/guide_followsinafriend" target="hideframe">
				<c:if test="${fn:length(tb_sina_userlist)>0}">
					<div class="row b">
					您在新浪微博的好友注册了顾问家
					</div>
					<div class="row">
						<ul class="userlist2">
							<c:forEach var="tb_sina_user" items="${tb_sina_userlist}">
								<li>
									<div class="f_l">
										<img src="${tb_sina_user.tbUser.pic_url_80}" width="60" height="60"/>
									</div>
									<div class="f_l">
										${tb_sina_user.tbUser.show_nick }
										<input checked="checked" type="checkbox" name="userid" value="${tb_sina_user.userid }"/><br/>
										${tb_sina_user.tbUser.location }<br/>
									</div>
									<div class="clr"></div>
								</li>
							</c:forEach>
						</ul>
						<div class="clr"></div>
					</div>
				</c:if>
				<div class="row">
					<input type="submit" class="btn split-r" value="关注他们"/>
					<input type="submit" class="btn" value="下一步" onclick="tourl('${ctx_path}/tb/op/guide_bindapp')"/>
				</div>
			</form>
	</div>
<div class="clr"></div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function followok(){
	tourl('${ctx_path}/tb/op/guide_bindapp');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>