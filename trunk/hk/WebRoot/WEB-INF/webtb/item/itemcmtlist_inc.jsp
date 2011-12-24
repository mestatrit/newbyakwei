<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><hk:actioninvoke mappinguri="/tb/item_checkusersysadmin"/>
<ul class="itemcmtlist">
<c:forEach var="cmt" items="${cmtlist}">
<li id="li_${cmt.cmtid }" idx="${cmt.cmtid }" status="0">
	<div class="head">
		<a href="/p/${cmt.userid }"><img src="${cmt.tbUser.pic_url_80 }" width="60" height="60"/></a>
		<c:if test="${cmt.tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
	</div>
	<div class="body">
		<div class="body_head">
			<div class="nick"><a href="/p/${cmt.userid }">${cmt.tbUser.show_nick }</a></div>
			<c:if test="${cmt.score>0 && cmt.score<6}">
			<div class="score">
			<img src="${ctx_path }/webtb/img/star/star${cmt.score }.gif"/>
			</div>
			</c:if>
			<div class="time"><fmt:formatDate value="${cmt.create_time}" pattern="yyyy-MM-dd HH:mm"/> </div>
			<div class="clr"></div>
		</div>
		<div>${cmt.content }</div>
		<div id="cmt_op_${cmt.cmtid }" class="op">
			<c:if test="${login_user!=null}">
				<c:if test="${cmt.userid==login_user.userid || user_sysadmin}">
				<a class="opa" href="javascript:delcmt(${cmt.cmtid })">删除</a>
				</c:if>
				<c:if test="${cmt.userid==login_user.userid}">
				<a class="opa" href="javascript:updatecmt(${cmt.cmtid })">修改</a>
				</c:if>
				<a class="opa" href="javascript:replystatus(${cmt.cmtid })">回复<c:if test="${cmt.reply_count>0}">(${cmt.reply_count })</c:if></a>
			</c:if>
		</div>
		<div id="reply_area${cmt.cmtid }" status="0" class="row" style="display: none;"></div>
	</div>
	<div class="clr"></div>
</li>
</c:forEach>
</ul>
<c:if test="${login_user!=null}">
我也说两句：<span class="ruo">最多500字</span><a name="create_cmt_area"></a><br />
<form id="cmtfrm" onsubmit="return subcmtfrm(this.id)" method="post" action="${ctx_path }/tb/itemcmt_prvcreate" target="hideframe">
	<hk:hide name="itemid" value="${itemid}"/>
	<div class="row">
		<textarea name="content" onkeyup="keysubcmtfrm('cmtfrm',event)" style="width: 560px; height: 120px;"></textarea>
		<div class="infowarn" id="info"></div>
	</div>
	<div class="row" align="right" style="width:560px;">
		<c:if test="${user_has_sinaapi}">
			<input id="_tosina" type="checkbox" name="create_to_sina_weibo" value="true"/><label for="_tosina">发送到新浪微博</label>
		</c:if>
		<input type="submit" class="btn" value="提交点评" />
	</div>
</form>
</c:if>
<c:if test="${login_user==null}"></c:if>