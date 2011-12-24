<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">${cmpNav.name }
			</div>
			<div class="mod_content">
				<div>
					<input value="发布文章" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmparticle_create.do?navoid=${navoid }&companyId=${companyId}')"/>
					<a class="split-r" href="<%=path %>/epp/web/op/webadmin/cmparticle_grouplist.do?navoid=${navoid }&companyId=${companyId}">组管理</a>
					<a href="<%=path %>/epp/web/op/webadmin/cmparticle_cmparticlenavpinklist.do?navoid=${navoid }&companyId=${companyId}">栏目文章推荐管理</a>
				</div>
				<div class="divrow b">
					<form action="<%=path %>/epp/web/op/webadmin/cmparticle.do">
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="navoid" value="${navoid}"/>
						标题：<hk:text name="title" clazz="text" value="${title}"/>
						<hk:submit clazz="btn" value="搜索"/>
					</form>
				</div>
				<div class="divrow b">
				在此推荐的文章将会在首页相应模块中显示
				</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="at" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:330px">
									<div style="padding:0 20px 0 0">
									<a href="<%=path %>/epp/web/op/webadmin/cmparticle_view.do?oid=${at.oid }&companyId=${companyId}&navoid=${navoid}">${at.title }</a>
									<c:if test="${at.groupId>0}">(${at.cmpArticleGroup.name })</c:if>
									</div>
								</div>
								<div class="f_l" style="width:100px">
									<fmt:formatDate value="${at.createTime}" pattern="yy-MM-dd HH:mm"/>
								</div>
								<div class="f_l" style="width:150px;padding-left: 10px">
									<a href="<%=path %>/epp/web/op/webadmin/cmparticle_update.do?oid=${at.oid }&companyId=${companyId}&navoid=${navoid}">修改</a> / 
									<c:if test="${!at.pinkForCmp}">
										<a href="javascript:setcmppink(${at.oid})" title="在此推荐的文章将会在首页相应模块中显示">推荐</a> / 
									</c:if> 
									<c:if test="${at.pinkForCmp}">
										<a href="javascript:delcmppink(${at.oid})">取消推荐</a> / 
									</c:if> 
									<a href="javascript:delarticle(${at.oid })">删除</a><br/>
									<a href="javascript:showorderflgwin(${at.oid },${at.orderflg })">设置序号</a>
									<c:if test="${o.cmpFlgE_COMMERCE}">
										<c:if test="${cmpInfo.tmlflg==0}">
											/ <a href="<%=path %>/epp/web/op/webadmin/cmparticle_setproductref.do?companyId=${companyId}&navoid=${navoid}&oid=${at.oid}">关联产品</a>
										</c:if>
									</c:if>
									<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
										/ <a href="javascript:tonavpink(${at.oid })" title="推荐到文章所属栏目的页面">栏目推荐</a>
									</c:if>
									<c:if test="${o.cmpEdu || (o.cmpFlgEnterprise && cmpInfo.tmlflg==1)}">
									 / <a href="javascript:setblock(${at.oid })" title="推荐文章到所选区块，区块将在首页显示">推荐到区块</a>
									</c:if>
									<c:if test="${at.page1Block!=null}">
									<div id="cmpad${at.oid }_page1">已推荐到：${at.page1Block.name } <a href="javascript:delarticleblock(${at.oid },${at.page1BlockId },'cmpad${at.oid }_page1')">取消推荐</a></div>
									</c:if>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有添加任何文章</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmparticle.do?navoid=${navoid }&companyId=${companyId}&title=${enc_title}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delarticleblock(oid,blockId,id){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delarticleblock2.do?companyId=${companyId}&oid="+oid+"&blockId="+blockId,
		cache:false,
    	dataType:"html",
		success:function(data){
			delObj(id);
		}
	});
}
function delarticle(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmparticle_del.do?companyId=${companyId}&ajax=1&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function showorderflgwin(oid,orderflg){
	var title="设置序号";
	var html='<form id="orderflgfrm" method="post" onsubmit="return setorderflg(this.id)" action="<%=path %>/epp/web/op/webadmin/cmparticle_setorderflg.do" target="hideframe">';
	html+='<div class="b">序号越大，排序越靠前</div>';
	html+='<hk:hide name="companyId" value="${companyId}"/>';
	html+='<input type="hidden" name="oid" value="'+oid+'"/>';
	html+='序号：<input type="text" name="orderflg" value="'+orderflg+'" class="text2"/>';
	html+='<hk:submit value="view2.submit" res="true" clazz="btn"/>';
	html+='</form>';
	createSimpleCenterWindow('orderwin',400, 200, title, html,"hideWindow('orderwin')");
}
function setorderflg(){
	return true;
}
function setorderflgok(error,msg,v){
	refreshurl();
}
function setcmppink(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_setcmppink.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delcmppink(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delcmppink.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setblock(oid){
	tourl('<%=path %>/epp/web/op/webadmin/cmparticle_selblock.do?oid='+oid+'&companyId=${companyId}&navoid=${navoid}&page=${page}&return_url='+encodeLocalURL());
}
function tonavpink(oid){
	tourl('<%=path %>/epp/web/op/webadmin/cmparticle_createcmparticlenavpink.do?oid='+oid+'&companyId=${companyId}&navoid=${navoid}&return_url='+encodeLocalURL());
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>