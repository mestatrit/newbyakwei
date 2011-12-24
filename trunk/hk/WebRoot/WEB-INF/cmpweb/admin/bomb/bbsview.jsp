<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">被炸掉的话题
		<a class="more" href="<%=path%>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}">返回</a>
		</div>
		<div class="mod_content">
			<a target="_blank" href="<%=path %>/epp/web/user.do?userId=${cmpBbsDel.userId}">${bbsUser.nickName }</a>：<strong>${cmpBbsDel.title }</strong> <fmt:formatDate value="${cmpBbsDel.createTime}" pattern="yyyy-MM-dd HH:mm"/>
			<a href="javascript:recovercmpbbs(${bbsId })">恢复</a> / 
			<a href="javascript:delcmpbbs(${bbsId })">删除</a>
			<br/>
			<div>${cmpBbsDel.content }</div>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div>
							<a target="_blank" href="<%=path %>/epp/web/user.do?userId=${n.userId}">${n.user.nickName }</a> 
							<fmt:formatDate value="${n.createTime}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
						<div>
							${n.content }
						</div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有回复</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/bomb_bbsview.do?companyId=${companyId}&bbsId=${bbsId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpbbs(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/bomb_delbombbbs.do?companyId=${companyId}&bbsId="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl('<%=path%>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}');
			}
		});
	}
}
function recovercmpbbs(oid){
	if(window.confirm('确实要恢复？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/bomb_recoverbombbbs.do?companyId=${companyId}&bbsId="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl('<%=path%>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}');
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>