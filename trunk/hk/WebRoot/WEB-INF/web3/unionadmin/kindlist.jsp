<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">分类管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<div class="text_14 heavy">
		<c:if test="${fn:length(list2)>0}">
			<a href="<%=path %>/cmpunion/op/union_kindlist.do?uid=${uid}">分类管理 </a>&gt;
			<c:forEach var="kk" items="${list2}">
				<a href="<%=path %>/cmpunion/op/union_kindlist.do?uid=${uid}&parentId=${kk.kindId}">${kk.name }</a> &gt;
			</c:forEach>
		</c:if>
	</div>
	<div class="text_14">
		<hk:form oid="kindfrm" onsubmit="return subkindfrm(this.id)" action="/cmpunion/op/union_createkind.do" target="hideframe">
			<hk:hide name="uid" value="${uid}"/>
			<hk:hide name="parentId" value="${parentId}"/>
			名称：<hk:text name="name" clazz="text_short_2"/>
			<hk:submit value="创建分类" clazz="btn2"/>
		</hk:form>
		<br/>
	</div>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px">名称</td>
				<td></td>
			</tr>
			</table>
		</li>
		<c:if test="${fn:length(list)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="k" items="${list}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px">${k.name }</td>
						<td>
							<a href="<%=path %>/cmpunion/op/union_kindlist.do?uid=${uid}&parentId=${k.kindId}">查看子分类</a>
							/
							<a id="edit${k.kindId }" href="javascript:toedit(${k.kindId })">修改</a>
							<c:if test="${!k.hasChild}">
							/	<a id="del${k.kindId }" href="javascript:del(${k.kindId })">删除</a>
							</c:if>
							<c:if test="${k.kindLevel>0}">
							/ <a id="cmd${k.kindId }" href="javascript:setcmdkind(${k.kindId })">推荐</a>
							</c:if>
							<span id="info${k.kindId }"></span>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/union_kindlist.do?uid=${uid}&parentId=${parentId }"/>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
function subkindfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function subkindeditfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function setcmdkind(id){
	showSubmitDivForObj("cmd"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/union_createcmdkind.do?uid=${uid}&kindId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv("cmd"+id);
			setHtml("info"+id,"推荐成功");
		}
	});
}
function del(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj("del"+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/cmpunion/op/union_delkind.do?uid=${uid}&kindId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				if(data=='1'){
					alert("由于此分类中含有多个子分类，不能马上删除。请先删除子分类，然后再进行分类的删除操作");
				}
				else{
					refreshurl();
				}
			}
		});
	}
}
function toedit(id){
	showSubmitDivForObj("edit"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/union_loadkind.do?uid=${uid}&kindId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("table_update_win",500,200,'修改分类',data,"hideWindow('table_update_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
function addkindsuccess(error,error_msg,respValue){
	refreshurl();
}
function addkinderror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>