<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpProductSort.name }
		<a class="more" href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId}&parentId=${parentId}">返回</a>
		</div>
		<div class="mod_content">
			<c:if test="${fn:length(list)<2}">
			<div class="divrow">
				<input type="button" class="btn split-r" value="添加广告图片" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpproduct_createsortpic.do?companyId=${companyId }&sortId=${sortId }&parentId=${parentId }')"/>
			</div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width:200px">
							<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_updatesortpic.do?companyId=${companyId }&sortId=${n.sortId }&parentId=${parentId}&oid=${n.oid}">${n.name }</a>
						</div>
						<div class="f_l" style="width:300px;padding-left: 20px;">
							<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_updatesortpic.do?companyId=${companyId }&sortId=${n.sortId }&parentId=${parentId}&oid=${n.oid}">修改</a> 
							 / <a href="javascript:delcmpproductsortfile(${n.sortId })">删除</a>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何广告</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpproductsortfile(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpproduct_delsortpic.do?companyId=${companyId}&oid="+oid,
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