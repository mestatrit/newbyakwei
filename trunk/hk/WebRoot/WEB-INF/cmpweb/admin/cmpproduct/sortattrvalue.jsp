<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">产品分类属性管理
		<a class="more" href="<%=path %>/epp/web/op/webadmin/cmpproduct_saveattrmodule.do?companyId=${companyId }&sortId=${sortId }&parentId=${parentId}">返回</a>
		</div>
		<div class="mod_content">
			<div class="divrow">
			${cmpProductSort.name } ：${attrName }
			</div>
			<div class="divrow">
			<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpproduct_createsortattrvalue.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="sortId" value="${sortId}"/>
				<hk:hide name="attrflg" value="${attrflg}"/>
				名称：<hk:text name="name" value="" clazz="text"/>
				<hk:submit value="添加" clazz="btn"/>
				<div class="infowarn" id="msg"></div>
			</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="n" items="${list}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width:200px">
							${n.name }
						</div>
						<div class="f_l" style="width:300px;padding-left: 20px;">
						<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_updatesortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=${attrflg }&attrId=${n.attrId }">修改</a> 
						/
						<a href="javascript:delattr(${n.attrId })">删除</a>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任数据</div>
			</c:if>
			<a class="more2" href="<%=path %>/epp/web/op/webadmin/cmpproduct_saveattrmodule.do?companyId=${companyId }&sortId=${sortId }&parentId=${parentId}">返回</a>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	setHtml('msg','');
	showGlass(frmid);
	return true;
}
function createerror(error,msg,v){
	setHtml('msg',msg);
	hideGlass();
}
function createok(error,msg,v){
	refreshurl();
}
function delattr(attrId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpproduct_delsortattrvalue.do?companyId=${companyId}&attrId="+attrId,
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