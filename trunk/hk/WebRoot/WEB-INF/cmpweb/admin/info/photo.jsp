<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_content">
				<img src="${companyPhoto.pic320 }"/><br/>
				<c:forEach var="ref" items="${reflist}">
					<div class="divrow">
						${ref.cmpPhotoSet.name } <a href="javascript:rmphotofromset(${ref.oid })">从图集中移除</a>
					</div>
				</c:forEach>
				<div>
					<a href="${denc_return_url }">返回</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function rmphotofromset(oid){
	if(window.confirm('确实要从图集中移除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/info_rmphotofromset.do?companyId=${companyId}&oid="+oid,
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