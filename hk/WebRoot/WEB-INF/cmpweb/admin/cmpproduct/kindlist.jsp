<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">产品分类管理
		</div>
		<div class="mod_content">
			<div class="divrow">
				<c:if test="${parent.nlevel<3 || parent==null}">
				<input type="button" class="btn split-r" value="添加分类" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpproduct_createkind.do?companyId=${companyId }&parentId=${parentId }')"/>
				</c:if>
				<input type="button" class="btn" value="管理产品" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId }&navoid=${navoid }')"/>
			</div>
			<div class="divrow b">
				<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId}">分类</a>
				<c:forEach var="p" items="${parentlist}" varStatus="idx">
					 &gt; <a href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId}&parentId=${p.sortId}">${p.name }</a>
				</c:forEach>
				<c:if test="${parent!=null}">&gt; ${parent.name }</c:if>
			</div>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width:200px">
							<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId }&parentId=${n.sortId }">${n.name }</a>
						</div>
						<div class="f_l" style="width:300px;padding-left: 20px;">
							<c:if test="${n.nlevel<3}">
								<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_createkind.do?companyId=${companyId }&parentId=${n.sortId }">创建子分类</a> /
								<c:if test="${n.hasChildren}">
									<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId }&parentId=${n.sortId }">修改子分类</a> /
								</c:if>
							</c:if>
							<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_updatekind.do?companyId=${companyId }&sortId=${n.sortId }&parentId=${parentId}">修改</a> 
							<c:if test="${!n.hasChildren}">
							 / <a href="javascript:delcmpproductkind(${n.sortId })">删除</a>
							 <c:if test="${o.cmpFlgE_COMMERCE}">
							 / <a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortpic.do?companyId=${companyId}&sortId=${n.sortId}&parentId=${parentId}">设置首页广告图片</a>
							 </c:if>
							<c:if test="${!n.hasChildren && o.openProductattrflg}">
								<br/><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_saveattrmodule.do?companyId=${companyId }&sortId=${n.sortId }&parentId=${parentId}">设置分类属性</a>
							</c:if>
							</c:if>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何分类数据</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmpproductkind(sortId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpproduct_delkind.do?companyId=${companyId}&sortId="+sortId,
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