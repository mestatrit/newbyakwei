<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">
		<c:if test="${cmpAdGroup==null}">企业广告</c:if>
		<c:if test="${cmpAdGroup!=null}">${cmpAdGroup.name}</c:if>
		</div>
		<div class="mod_content">
			<div class="divrow">
			<c:if test="${o.cmpFlgEnterprise}">
				<hk:data key="epp.cmpad.limit" arg0="5"/>
			</c:if>
			</div>
			<div>
				<c:if test="${fn:length(list)<5 && o.cmpFlgEnterprise}">
					<input value="添加广告" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId}&groupId=${groupId }')"/>
				</c:if>
				<c:if test="${!o.cmpFlgEnterprise}">
					<input value="添加广告" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId}&groupId=${groupId }')"/>
					<input value="添加广告代码" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_create.do?companyId=${companyId}&groupId=${groupId }&htmlflg=1')"/>
				</c:if>
				<c:if test="${o.cmpEdu}">
				<a class="split-r" href="<%=path %>/epp/web/op/webadmin/cmpad_reflist.do?companyId=${companyId}">二级页面广告</a>
				</c:if>
				<a class="split-r" href="<%=path %>/epp/web/op/webadmin/cmpad_grouplist.do?companyId=${companyId}">广告组管理</a>
				<c:if test="${groupId>0}">
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">所有广告</a>
				</c:if>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:300px">
								<c:if test="${not empty n.name}">
									${n.name }<br/>
								</c:if>
								${n.url }
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<c:if test="${o.cmpEdu}">
								<a href="javascript:setblock(${n.adid })">推荐到区块</a> / 
								</c:if>
								<a href="<%=path %>/epp/web/op/webadmin/cmpad_update.do?adid=${n.adid }&companyId=${companyId}">修改</a> / 
								<a href="javascript:delcmpad(${n.adid })">删除</a>
								/ <a href="<%=path %>/epp/web/op/webadmin/cmpad_grouplist.do?companyId=${companyId}&adid=${n.adid}">选择组</a>
								<c:if test="${n.page1Block!=null}">
								<div id="cmpad${n.adid }_page1">已推荐到：${n.page1Block.name } <a href="javascript:deladblock(${n.adid },${n.page1BlockId },'cmpad${n.adid }_page1')">取消推荐</a></div>
								</c:if>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何广告</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function deladblock(adid,blockId,id){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpad_deladblock2.do?companyId=${companyId}&adid="+adid+"&blockId="+blockId,
		cache:false,
    	dataType:"html",
		success:function(data){
			delObj(id);
		}
	});
}
function setblock(adid){
	tourl('<%=path %>/epp/web/op/webadmin/cmpad_selblock.do?adid='+adid+'&companyId=${companyId}&page=${page}&return_url='+encodeLocalURL());
}
function delcmpad(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpad_del.do?companyId=${companyId}&adid="+oid,
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