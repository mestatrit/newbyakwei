<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%>
<%@page import="com.hk.bean.BoxPrize"%>
<%@page import="com.hk.bean.UserBoxPrize"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">兑奖</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon2" style="width: 400px">
		<div class="inner">
			<div class="mod">
				<div class="mod_title">
				<c:if test="${drawflg==0}">
					<h1 style="display: inline;">可兑换序列号</h1> /
					<h1 style="display: inline;"><a href="<%=path %>/h4/op/box_userprize.do?boxId=${boxId}&drawflg=<%=UserBoxPrize.DRAWFLG_Y %>">已兑换序列号</a></h1>
				</c:if>
				<c:if test="${drawflg==1}">
				<h1 style="display: inline;"><a href="<%=path %>/h4/op/box_userprize.do?boxId=${boxId}&drawflg=<%=UserBoxPrize.DRAWFLG_N %>">可兑换序列号</a>
				</h1> /
				<h1 style="display: inline;">
					已兑换序列号
				</h1>
				</c:if>
				</div>
				<div class="mod_content">
					<c:if test="${fn:length(list)==0}">
					<div class="nodata">暂时没有数据</div>
					</c:if>
					<c:if test="${fn:length(list)>0}">
					<c:forEach var="up" items="${list}">
						<c:if test="${up.boxPrize.useSignal || not empty up.prizeNum}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								${up.boxPrize.name } 
								序列号：<a href="javascript:autoadd(${up.prizeNum })">${up.prizeNum }</a>
							</div>
						</c:if>
					</c:forEach>
					</c:if>
					<c:set var="page_url" scope="request"><%=path%>/h4/op/box_userprize.do?boxId=${boxId}&drawflg=${drawflg}</c:set>
					<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="rcon2" style="width: 400px;">
		<div class="inner">
			<h1 class="bdtm">兑奖</h1>
			<div class="divrow">
			<c:if test="${ch==0 || (ch==1 && userBoxPrize==null)}">
				<form id="prizefrm" method="post" action="<%=path %>/h4/op/box_userprize.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="boxId" value="${boxId}"/>
					<table cellpadding="0" cellspacing="0" class="nt">
						<tr>
							<td width="80px" align="right">序列号：</td>
							<td><hk:text name="num" clazz="text"/></td>
						</tr>
						<tr>
							<td align="right">暗号：</td>
							<td><hk:text name="pwd" clazz="text"/></td>
						</tr>
						<tr>
							<td></td>
							<td>
							<c:if test="${ch==1 && userBoxPrize==null}">
								<div class="infowarn">序列号或者暗号错误</div>
							</c:if>
							<div style="padding-left: 180px;"><hk:submit value="提交" clazz="btn"/></div>
							</td>
						</tr>
					</table>
				</form>
			</c:if>
			<c:if test="${ch==1}">
				<c:if test="${userBoxPrize!=null}">
					<div class="mod">
						<div class="mod_title">
							奖品信息 
						</div>
						<div class="mod_content">
							<div class="divrow">
								<c:if test="${not empty boxPrize.path}"><img src="${boxPrize.h_0Pic }"/></c:if>
								${boxPrize.name }
								<c:if test="${userBoxPrize.drawed}">
									<span class="infowarn" style="font-size: 16px">已于<fmt:formatDate value="${userBoxPrize.drawTime}"  pattern="yyyy年MM月dd日 HH:mm"/>兑奖</span>
								</c:if>
							</div>
							<c:if test="${!userBoxPrize.drawed}">
								<div class="divrow">
									<form action="<%=path %>/h4/op/box_setuserprizeok.do">
										<hk:hide name="boxId" value="${boxId}"/>
										<hk:hide name="sysId" value="${userBoxPrize.sysId}"/>
										<hk:submit value="设置已经兑奖" clazz="btn"/>
									</form>
								</div>
							</c:if>
						</div>
					</div>
				</c:if>
				<a href="<%=path %>/h4/op/box_userprize.do?boxId=${boxId}">继续兑奖</a>
			</c:if>
			</div>
			<a class="more2" href="/box/${boxId }">返回</a>
		</div>
	</div>
</div>
<script type="text/javascript">
function autoadd(num){
	getObj('prizefrm').num.value=num;
}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>