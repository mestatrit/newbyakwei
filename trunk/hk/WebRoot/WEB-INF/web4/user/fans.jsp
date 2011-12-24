<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.friend"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px">
<c:set var="html_nickName"><a href="/user/${userId }/">${user.nickName}</a></c:set>
<h1 class="title4"><hk:data key="view2.fans2" arg0="${html_nickName}"/>
<a class="return" href="/user/${userId }/"><hk:data key="view2.return"/></a>
</h1>
<ul class="userlist">
	<c:forEach var="f" items="${uservolist}" varStatus="idx">
	<c:if test="${idx.index%2!=0}"><c:set var="css_clazz">bg1</c:set></c:if>
	<c:if test="${idx.index%2==0}"><c:set var="css_clazz">bg0</c:set></c:if>
	<li class="${css_clazz }" onmouseover="this.className='${css_clazz } bg2';" onmouseout="this.className='${css_clazz }';">
		<div class="libody">
			<div class="img">
				<a href="/user/${f.user.userId }/"><img alt="${f.user.nickName }" title="${f.user.nickName }" src="${f.user.head48Pic }"/></a>
			</div>
			<div class="con" style="width: 610px;">
				<a class="b" href="/user/${f.user.userId }/">${f.user.nickName }</a><c:if test="${f.user.pcityId>0}"> (${f.user.pcity.name})</c:if><br/>
				<c:if test="${not empty f.labaVo.content}">${f.labaVo.content }</c:if>
			</div>
			<div class="clr"></div>
		</div>
	</li>
	</c:forEach>
</ul>
<c:set var="page_url" scope="request">/user/${userId}/fans</c:set>
<c:set var="url_rewrite" scope="request" value="true"/>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
<script type="text/javascript">
function delfriend(id,idx){
	if(window.confirm("确实要取消关注?")){
		showGlass('row_'+id);
		$.ajax({
			type:"POST",
			url:"<%=path%>/follow/op/op_delweb.do?userId=",
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>