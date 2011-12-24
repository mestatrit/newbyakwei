<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.box"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="mod">
				<div style="font-size: 20px" class="bdtm b">
					<c:if test="${cityId>0}">
						${sys_zone_pcity.name} |
						<a clazz="${all_css}" href="/box_list.do">全球</a>
					</c:if>
					<c:if test="${cityId==0}">
						<a clazz="${city_css}" href="/box_list.do?cityId=${sys_zone_pcityId}">${sys_zone_pcity.name}</a> |
						全球
					</c:if>
				</div>
				<div class="mod_content">
					<c:if test="${fn:length(boxlist)==0}">
					<div class="divrow b">暂时没有任何宝箱发布</div>
					</c:if>
					<c:if test="${fn:length(boxlist)>0}">
						<c:forEach var="box" items="${boxlist}" varStatus="idx">
							<c:set var="onmouseover"><c:if test="${idx.index%2!=0}">this.className='block box2 box2a bg1 bg2'</c:if><c:if test="${idx.index%2==0}">this.className='block box2 box2a bg2'</c:if></c:set>
							<c:set var="onmouseout"><c:if test="${idx.index%2!=0}">this.className='block box2 bg1'</c:if><c:if test="${idx.index%2==0}">this.className='block box2'</c:if></c:set>
							<div class="block box2 <c:if test="${idx.index%2!=0}">bg1</c:if>" onmouseover="${onmouseover}" onmouseout="${onmouseout}">
								<table cellpadding="0" cellspacing="0" class="nt">
									<tr>
										<td>
											<div class="box2_name"><a class="b" href="/box/${box.boxId}">${box.name }</a></div>
											<div class="box2_remain">剩余${box.totalCount-box.openCount }个</div>
											<div class="clr"></div>
											${box.simpleIntro }
											<div class="open"><input type="button" class="btn" value="开箱子" onclick="openbox(${box.boxId})"/> </div>
										</td>
										<td class="imgtd">
											<c:if test="${empty box.path}">
												<a href="/box/${box.boxId}"><img alt="${box.name }" src="<%=path %>/webst4/img/baoxiang_s.png"/></a>
											</c:if>
											<c:if test="${not empty box.path}">
												<a href="/box/${box.boxId}"><img alt="${box.name }" src="${box.pic }" width="60" height="60"/></a>
											</c:if>
										</td>
									</tr>
								</table>
							</div>
						</c:forEach>
					</c:if>
					<c:set var="url_rewrite" value="true" scope="request"/>
					<c:set var="page_url" scope="request">/boxes</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../inc/boxfeed_inc.jsp"></jsp:include>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function openbox(id){
	tourl("<%=path %>/h4/op/user_openbox.do?boxId="+id);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>