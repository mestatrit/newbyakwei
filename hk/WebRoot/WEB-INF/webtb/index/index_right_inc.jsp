<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><hk:actioninvoke mappinguri="/tb/index_indexrightinc"/>
<div class="mod">
	<div class="mod_content">
	<form id="askfrm" method="get" action="${ctx_path }/tb/ask_q">
		<div style="margin-bottom: 10px;"><input type="text" class="text" style="width: 230px" name="w"/></div>
		<input type="submit" class="btn split-r" value="搜索答案" name="search"/>
		<input type="submit" class="btn3" value="我要提问" name="ask"/>
	</form>
	</div>
</div>
<c:if test="${fn:length(newuserlist)>0}">
	<div class="mod">
		<div class="mod_title">新人秀</div>
		<div class="mod_content">
			<ul class="headlist3"><c:forEach var="user" items="${newuserlist}"><li><a href="/p/${user.userid }"><img src="${user.pic_url_48 }" title="${user.show_nick }" /></a><p><a href="/p/${user.userid }">${user.show_nick }</a></p></li></c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
	</div>
</c:if>
<c:if test="${fn:length(supermanlist)>0}">
	<div class="mod">
		<div class="mod_title">达人</div>
		<div class="mod_content">
			<ul class="headlist3"><c:forEach var="user" items="${supermanlist}"><li><a href="/p/${user.userid }"><img src="${user.pic_url_48 }" title="${user.show_nick }" /></a><p><a href="/p/${user.userid }">${user.show_nick }</a></p></li></c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
	</div>
</c:if>
<c:if test="${fn:length(asklist)>0}">
	<div class="mod">
		<div class="mod_title">最新问题
		<a class="more" href="${ctx_path }/tb/ask_list">更多</a>
		</div>
		<div class="mod_content">
			<ul class="rowlist">
				<c:forEach var="ask" items="${asklist}">
					<li>
					<a class="nick" href="/p/${ask.userid }"><img src="${ask.tbUser.pic_url_48 }" width="16"/>${ask.tbUser.show_nick }：</a><a href="${ctx_path }/tb/ask?aid=${ask.aid}">${ask.title }</a>
					</li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
	</div>
</c:if>