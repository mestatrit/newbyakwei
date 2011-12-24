<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/box.css" />
</c:set>
<c:set var="html_title" scope="request">${box.name}</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="mod">
				<div class="boxcon" style="width: 540px">
					<c:if test="${box!=null}">
						<div class="box_name">
						<h1 style="display: inline;"><a href="/box/${box.boxId }">${box.name }</a></h1>
						<c:if test="${canadminbox}">
						<div class="f_r"><a href="<%=path %>/h4/op/box_userprize.do?boxId=${boxId}">兑奖</a>
						</div>
						</c:if>
						</div>
						<div class="f_l" style="width: 200px">
							<div class="boxprize_tit">
								奖品
							</div>
							<div class="boxprize_con">
								<c:if test="${firstPrize!=null}">
									<div class="bdtm" style="padding: 3px;">
										<div class="f_l" style="width: 150px;overflow: hidden;">${firstPrize.name }</div>
										<div class="f_r b">${firstPrize.pcount }个</div>
										<div class="clr"></div>
									</div>
								</c:if>
								<div class="box_tit">
									<div class="divrow">${box.totalCount-box.openCount }个</div>
									<c:if test="${!onlysmsopen && begin && !stop}">
									<form method="post" action="<%=path %>/h4/op/user_openbox.do">
										<hk:hide name="boxId" value="${boxId}"/>
										<hk:submit clazz="btn2" value="开箱子"/>
									</form>
									</c:if>
								</div>
								<div class="box_time">
									<c:if test="${box.pretype!=0}">
										每人每${boxPretype.name }开${box.precount }个箱子<br/>
									</c:if>
									<c:if test="${userLogin && canOpenBoxCount>0}">
										<hk:data key="view2.usercanopenbox" arg0="${canOpenBoxCount}"/>
									</c:if>
								</div>
							</div>
						</div>
						<div class="f_r">
							<div id=imgstore  style="display:none">
								<c:forEach var="prize" items="${list}">
									<img src="${prize.h_2Pic }" title="${prize.name }"/>
								</c:forEach>
							</div>
							<div id="showhere">
								<c:if test="${piccount==1}">
								<c:forEach var="prize" items="${list}">
									<c:if test="${not empty prize.path}">
										<img src="${prize.h_2Pic }" title="${prize.name }" alt="${prize.name }" rel="/box/${box.boxId }"/>
									</c:if>
								</c:forEach>
							</c:if>
							</div>
						</div>
						<div class="clr"></div>
						<c:if test="${fn:length(list)>1}"></c:if>
						<div class="divrow">
							<div class="mod">
								<div class="mod_title">更多奖品</div>
								<div class="mod_content">
									<div class="boxprizelist">
										<c:forEach var="prize" items="${list}">
											<div class="prize">
												<div class="prizepic">
													<c:if test="${not empty prize.path}">
														<img alt="${prize.name }" title="${prize.name }" src="${prize.h_0Pic }">
													</c:if>
												</div>
												<div class="prizebody">
													<span class="b">${prize.name }</span> ${prize.pcount }个<br/>
													${prize.tip }
												</div>
												<div class="clr"></div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
						<div class="divrow">
						${box.intro }
						</div>
						<a class="more2" href="/boxes/">返回宝箱列表</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../inc/boxfeed_inc.jsp"></jsp:include>
	<div class="clr"></div>
</div>
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/jquery.myslide.js"></script>
<script type="text/javascript">
<c:if test="${piccount>1}">
$(document).ready(function(){
	$.init_slide('imgstore','showhere',0,0,1000,1,6000,0,'_self');
	});
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>