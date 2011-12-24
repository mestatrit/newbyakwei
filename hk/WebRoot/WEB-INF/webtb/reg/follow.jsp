<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">关注朋友
</c:set><c:set var="html_body_content" scope="request">
	<div class="reg_l">
		<div class="step"><h2>注册</h2></div>
		<div class="step selected"><h2>关注朋友</h2><img class="selected" src="${ctx_path }/webtb/img/signup_arrow.gif" /></div>
		<div class="step"><h2>绑定应用</h2></div>
	</div>
	<div class="reg_r">
		<h1 class="title1">请选择您感兴趣类别，我们将推荐一些有公信力的行家给您认识</h1>
		<div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>数码
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>手机
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>游戏
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>女性服饰
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>男性服饰
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>鞋子
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>饰品
			</div>
			<div class="tag_sel">
				<input type="checkbox" id=""/>电脑
			</div>
			<div class="clr"></div>
		</div>
		<div class="mod">
			<div class="mod_content">
				<form method="post" action="#">
					<ul class="userlist">
						<li>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="checkbox" name="userid" checked="checked"/>
									</td>
									<td>
										<div class="head">
											<img src="${ctx_path }/webtb/img/h60.jpg"/>
											<br/>
											<a href="#">昵称</a>
										</div>
										<div class="body">
											粉丝：2340
											<br/>
											顾问家评论：酷的目的通过有公信力和影响力的人来让那些货真价实的淘宝商家和商品浮出水平，并对推荐者给与荣誉和奖励，请认证填写其信息
										</div>
									</td>
								</tr>
							</table>
						</li>
					</ul>
					<div style="margin-top:20px" align="center">
						<input type="submit" value="关注他们" class="btn split-r"/><input type="button" value="跳过" class="btn" onclick="tourl('${ctx_path}/tb/op/guide_bindapp')"/>
					</div>
				</form>
			</div>
		</div>
	</div>
<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.userlist li').bind('mouseenter', function(){
		$(this).css('background-color', '#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color', '#ffffff');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>