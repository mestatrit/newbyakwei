<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">选择区块的内容
		<a class="more" href="<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${blockId}&navId=${navId}&change=${change}">返回</a>
		</div>
		<div class="mod_content">
			<div class="divrow b">
				${cmpPageBlock.name }
			</div>
			<c:if test="${fn:length(navlist)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${navlist}">
					<c:if test="${n.articleList || n.directory}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:200px">
								${n.name }
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<c:if test="${n.articleList}">
									<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_selnav.do?companyId=${companyId}&blockId=${blockId}&navId=${n.oid}">选择</a> / 
									<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_grouplist.do?companyId=${companyId}&blockId=${blockId}&navId=${n.oid}&change=${change}">查看组</a>
								</c:if>
								<c:if test="${n.directory}">
									<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_navlist.do?companyId=${companyId}&blockId=${blockId}&navId=${n.oid}&change=${change}">查看二级栏目</a>
								</c:if>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(navlist)==0}">
				<div class="nodata">还没有添任何栏目</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>