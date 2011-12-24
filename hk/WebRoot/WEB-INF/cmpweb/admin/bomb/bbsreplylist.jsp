<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">
		<a style="color: #2398C9;" href="<%=path %>/epp/web/op/webadmin/bomb_bbslist.do?companyId=${companyId}">被炸掉的话题</a> / 
		被炸掉的回复</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div>
							<a target="_blank" href="<%=path %>/epp/web/user.do?userId=${n.userId}">${n.user.nickName }</a> 
							<fmt:formatDate value="${n.createTime}" pattern="yyyy-MM-dd HH:mm"/> 
							<a href="javascript:recovercmpbbsreply(${n.bbsId},${n.replyId })">恢复</a> / 
							<a href="javascript:delcmpbbsreply(${n.bbsId},${n.replyId })">删除</a>
						</div>
						<!--
						<div>
							${n.opuser.nickName } 于 <fmt:formatDate value="${n.optime}" pattern="yyyy-MM-dd HH:mm"/> 炸掉<br/>
							${n.content }
						</div> 
						 -->
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有被删除的回复</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/bomb_bbsreplylist.do?companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpbbsreply(bbsId,replyId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/bomb_delbombbbsreply.do?companyId=${companyId}&bbsId="+bbsId+"&replyId="+replyId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function recovercmpbbsreply(bbsId,replyId){
	if(window.confirm('确实要恢复？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/bomb_recoverbombbbsreply.do?companyId=${companyId}&bbsId="+bbsId+"&replyId="+replyId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>