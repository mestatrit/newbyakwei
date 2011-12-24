<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><c:set var="html_title" scope="request">${o.name}</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">自定义表格</div>
		<div class="mod_content">
			<div>
				<input value="创建字段" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpusertable_createusertabledata.do?companyId=${companyId}&navoid=${navoid }')"/>
				<a href="<%=path %>/epp/web/op/webadmin/cmpusertable_valuelist.do?companyId=${companyId}&navoid=${navoid }">查看数据</a>
			</div>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<c:forEach var="data" items="${list}">
						<li>
							<div class="f_l" style="width: 200px;margin-right: 20px;">
							${data.name }
							</div>
							<div class="f_l" style="width: 100px;margin-right: 20px;">
								<hk:data key="epp.cmpusertablefield.field_type${data.field_type}"/>
							</div>
							<div class="f_l" style="width: 100px;">
								<a class="split-r" href="javascript:toupdate(${data.fieldId })">修改</a>
								<a class="split-r" href="javascript:deldata(${data.fieldId })">删除</a>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何字段</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#ffffcc');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});

function toupdate(fieldId){
	tourl("<%=path %>/epp/web/op/webadmin/cmpusertable_updateusertablefield.do?companyId=${companyId}&navoid=${navoid}&fieldId="+fieldId);
}

function deldata(fieldId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/cmpusertable_deleteusertablefield.do?companyId=${companyId}&fieldId="+fieldId,
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