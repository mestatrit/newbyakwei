<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<div class="boxcon" style="width: 540px">
				<c:if test="${box!=null}">
					<div class="box_name">
					<h1 style="display: inline;">${box.name }</h1>
					</div>
					<div class="f_l" style="width: 200px">
						<div class="boxprize_tit">
							<hk:data key="epp.box.boxprize"/>
						</div>
						<div class="boxprize_con">
							<c:if test="${firstPrize!=null}">
								<div class="bdtm" style="padding: 3px;">
									<div class="f_l" style="width: 150px;overflow: hidden;">${firstPrize.name }</div>
									<div class="f_r b"><hk:data key="epp.box.boxprize.count" arg0="${firstPrize.pcount }"/></div>
									<div class="clr"></div>
								</div>
							</c:if>
							<div class="box_tit">
								<div class="divrow"><hk:data key="epp.box.remain" arg0="${box.totalCount-box.openCount }"/></div>
								<c:if test="${!onlysmsopen && begin && !stop}">
								<form method="post" action="<%=path %>/epp/web/box_prvopenbox.do">
									<hk:hide name="navId" value="${navId}"/>
									<hk:hide name="companyId" value="${companyId}"/>
									<hk:hide name="boxId" value="${boxId}"/>
									<hk:submit clazz="btn2" value="epp.box.openbox" res="true"/>
								</form>
								</c:if>
							</div>
							<div class="box_time">
								<c:if test="${box.pretype!=0}">
									每人每${boxPretype.name }开${box.precount }个箱子<br/>
								</c:if>
								<c:if test="${userLogin && canOpenBoxCount>0}">
									<hk:data key="epp.box.usercanopenbox" arg0="${canOpenBoxCount}"/>
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
									<img src="${prize.h_2Pic }" title="${prize.name }" alt="${prize.name }"/>
								</c:if>
							</c:forEach>
						</c:if>
						</div>
					</div>
					<div class="clr"></div>
					<c:if test="${fn:length(list)>1}"></c:if>
					<div class="divrow">
						<div class="mod">
							<div class="mod_title"><hk:data key="epp.box.boxprize.more"/></div>
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
					<a class="more2" href="<%=path %>/epp/web/box.do?companyId=${companyId }&navId=${navId}"><hk:data key="epp.return"/></a>
				</c:if>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/jquery.myslide.js"></script>
<script type="text/javascript">
<c:if test="${piccount>1}">
$(document).ready(function(){
	$.init_slide('imgstore','showhere',0,0,1000,1,6000,0,'_self');
	});
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>