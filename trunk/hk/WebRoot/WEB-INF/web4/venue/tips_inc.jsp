<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div class="mod">
	<div class="f_l" style="width: 600px;">
		<div id="do" class="active_tips_tab"><a href="/venue/${companyId }/tips"><hk:data key="view2.people.tips"/></a></div>
		<div id="todo" class="inactive_tips_tab"><a href="/venue/${companyId }/todo"><hk:data key="view2.venue_user_todolist"/></a></div>
		<div class="clr"></div>
		<div class="listbox">
			<c:if test="${fn:length(cmptipvolist)==0}">
				<div><hk:data key="view2.no_tips_in_this_place"/></div>
			</c:if>
			<jsp:include page="../inc/cmptipvolist_inc.jsp"></jsp:include>
			<c:if test="${more_tip}">
				<a class="more2" href="/venue/${companyId }/tips/2"><hk:data key="view2.more"/></a>
			</c:if>
		</div>
	</div>
	<div class="clr"></div>
</div>
<c:if test="${loginUser!=null}">
<div class="mod" onkeydown="subtipfrm2(event)">
	<form id="tipfrm" method="post" onsubmit="return subtipfrm(this.id)" action="/createtip" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<table class="nt reg" cellpadding="0" cellspacing="0">
	<tr>
		<td style="font-size: 14px;"><br/>
			<hk:radioarea name="doneflg">
				<span class="split-r">
					<input id="_done" type="radio" name="doneflg" value="<%=CmpTip.DONEFLG_DONE %>"/><label for="_done"><hk:data key="view2.i_did_this_and_it_was_awesome"/></label>
				</span>
				<span class="split-r">
					<input id="_todo" type="radio" name="doneflg" value="<%=CmpTip.DONEFLG_TODO %>"/><label for="_todo"><hk:data key="view2.add_this_to_my_todo_list"/></label>
				</span>
			</hk:radioarea>
		</td>
	</tr>
	</table>
	<div class="rounded" style="background-color: #DDDDDD;padding: 10px;overflow: hidden;">
	<table class="nt reg" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<br/>
			<div  style="margin-bottom:10px;">
			<textarea id="_content" name="content" class="text_area" style="height: 200px"/></textarea>&nbsp;&nbsp;&nbsp;<span id="numcount" class="numcount">500</span>
			</div>
			<div id="contentwarn" class="infowarn"></div>
			</td>
		</tr>
		<tr>
			<td align="center"><hk:submit value="view2.submit" res="true" clazz="btn split-r"/>
			</td>
		</tr>
	</table>
	</div>
	</form>
</div><div id="script_con"></div>
</c:if>