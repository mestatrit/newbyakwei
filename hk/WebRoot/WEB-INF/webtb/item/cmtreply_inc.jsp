<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<div class="row" id="cmt_reply_area_${cmtid }">
<form id="replyfrm_${cmtid }" method="post" onsubmit="return subreplyfrm(this.id)" action="${ctx_path }/tb/itemcmt_prvcreatereply" target="hideframe">
	<hk:hide name="cmtid" value="${cmtid}"/>
	<div class="f_l">
		<textarea onkeyup="keysubreply(${cmtid },event)" id="reply_content_${cmtid }" name="content" style="width:380px;height: 40px;"></textarea> 
	</div>
	<div class="f_l" style="margin-left: 5px">
		<input type="submit" class="btn2" value="回复"/>
	</div>
	<div class="clr"></div>
</form>
</div>
<c:if test="${fn:length(list)>0}">
<div class="listbox">
<ul id="ul_replylist_${cmtid }" class="replylist row">
<li id="beginpos_${cmtid }" class="beginpos"></li>
<c:forEach var="reply" items="${list}">
<li id="reply_${reply.replyid }">
	<div class="reply_head">
		<a href="/p/${reply.userid }"><img src="${reply.tbUser.pic_url_48 }"/></a>
	</div>
	<div class="reply_body">
		<a href="/p/${reply.userid }">${reply.tbUser.show_nick }</a>：
		${reply.content }
		<span class="split-l ruo"><fmt:formatDate value="${reply.create_time}" pattern="yy-MM-dd HH:mm"/></span>
		<div align="right">
			<c:if test="${login_user.userid==reply.userid}"><a class="split-r" href="javascript:delreply(${reply.replyid })">删除</a></c:if>
			<c:if test="${login_user.userid!=reply.userid}"><a class="split-r" href="javascript:replyuser(${cmtid },${reply.userid },'${reply.tbUser.show_nick }');">回复</a></c:if>
		</div>
	</div>
	<div class="clr"></div>
</li>
</c:forEach>
</ul>
</div>
</c:if>
<c:if test="${fn:length(list)==0}">
<div id="listbox_${cmtid }" class="listbox" style="display: none;"><ul class="replylist row"><li id="beginpos_${cmtid }" class="beginpos"></li></ul></div>
</c:if>