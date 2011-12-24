<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name }
			<c:if test="${cmpNav.articleList}">
				<a class="more" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?navoid=${cmpNav.oid }&companyId=${companyId}">返回</a>
			</c:if>
		</div>
		<div class="mod_content">
			<c:if test="${cmpNav.hasApplyForm}">
				<div class="divrow">
					<a href="<%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&navoid=${cmpNav.oid}&readed=<%=CmpJoinInApply.READED_N %>">申请管理</a>
				</div>
			</c:if>
			<div>
				<div style="text-align: center;">
					<span class="b" >${cmpArticle.title } </span><br/>
					<c:if test="${fn:length(tagreflist)>0}">
					<div>
					关键词：<c:forEach var="ref" items="${tagreflist}"><span class="split-r">${ref.cmpArticleTag.name }</span></c:forEach>
					</div>
					</c:if>
					<a class="split-r" href="<%=path %>/epp/web/op/webadmin/cmparticle_update.do?companyId=${companyId}&oid=${oid}&navoid=${navoid}">修改文章</a>
					<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==0}">
						<c:if test="${!cmpArticle.pinkForCmp}">
							<a class="split-r" href="javascript:setcmppink(${cmpArticle.oid})" title="在此推荐的文章将会在首页相应模块中显示">推荐</a>
						</c:if> 
						<c:if test="${ cmpArticle.pinkForCmp}">
							<a class="split-r" href="javascript:delcmppink(${cmpArticle.oid})">取消推荐</a>
						</c:if>
					</c:if>
					<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
						<c:if test="${cmpArticleNavPink==null}">
							<a class="split-r" href="javascript:tonavpink(${oid })" title="推荐到文章所属栏目的页面">栏目推荐</a>
						</c:if>
						<c:if test="${cmpArticleNavPink!=null}">
							<a class="split-r" href="javascript:delcmparticlenavpink(${cmpArticleNavPink.oid })">取消栏目推荐</a>
						</c:if>
					</c:if>
					<c:if test="${o.cmpEdu || (o.cmpFlgEnterprise && cmpInfo.tmlflg==1)}">
						<a class="split-r" href="javascript:setblock(${oid })" title="推荐文章到所选区块，区块将在首页显示">推荐到区块</a>
					</c:if>
					<a href="javascript:delarticle()">删除</a>
					<c:if test="${cmpArticleBlock!=null}">
					<div id="cmpad${at.oid }_page1">已推荐到区块：${cmpPageBlock.name } <a class="split-r" href="javascript:delarticleblock(${oid },${cmpArticleBlock.blockId },'cmpad${oid }_page1')">取消推荐</a></div>
					</c:if>
				</div>
				<c:if test="${topCmpFile!=null}">
					<div class="divrow" style="text-align: center;">
						<c:if test="${topCmpFile.imageShow}">
							<img src="${topCmpFile.cmpFilePic600 }"/>
						</c:if>
						<c:if test="${topCmpFile.flashShow}">
							<embed width="400" height="200" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${topCmpFile.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
						</c:if><br/>
						<a class="split-r" href="javascript:delfile(${topCmpFile.oid })">删除</a> 
						<a class="split-r" href="javascript:canceltop(${topCmpFile.oid })">取消置顶</a>
						<c:if test="${topCmpFile.path==cmpArticle.filepath}">
							<a class="split-r" href="javascript:cancelmain(${topCmpFile.oid })">取消头图</a>
						</c:if>
						<c:if test="${topCmpFile.path!=cmpArticle.filepath}">
							<a class="split-r" href="javascript:setmain(${topCmpFile.oid })" title="如果该文章栏目下的内容为图片列表显示时，会显示文章的头图">设为头图</a>
						</c:if>
					</div>
				</c:if>
				<div>
					${cmpArticleContent.content }
				</div>
				<c:forEach var="cf" items="${list}">
					<div class="divrow" style="text-align: center;">
						<c:if test="${cf.imageShow}">
							<img src="${cf.cmpFilePic600 }"/>
						</c:if>
						<c:if test="${cf.flashShow}">
							<embed width="400" height="200" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${cf.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
						</c:if><br>
					<a class="split-r" href="javascript:delfile(${cf.oid })">删除</a> 
					<c:if test="${cf.topInFile}">
						<a class="split-r" href="javascript:canceltop(${cf.oid })">取消置顶</a>
					</c:if>
					<c:if test="${!cf.topInFile}">
						<a class="split-r" href="javascript:settop(${cf.oid })" title="图片将显示在文章内容的前面">置顶</a>
					</c:if>
					<c:if test="${cf.path==cmpArticle.filepath}">
						<a class="split-r" href="javascript:cancelmain(${cf.oid })">取消头图</a>
					</c:if>
					<c:if test="${cf.path!=cmpArticle.filepath}">
						<a class="split-r" href="javascript:setmain(${cf.oid })" title="如果该文章栏目下的内容为图片列表显示时，会显示文章的头图">设为头图</a>
					</c:if>
					</div>
				</c:forEach>
			</div>
			<div>
				<c:if test="${cmpNav.articleList}">
					<a class="more2" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?navoid=${cmpNav.oid }&companyId=${companyId}">返回</a>
				</c:if>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delcmparticlenavpink(oid){
	if(window.confirm('确实要取消推荐？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmparticle_delcmparticlenavpink.do?companyId=${companyId}&ajax=1&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function delarticle(){
	if(window.confirm('确实要删除？')){
		tourl("<%=path%>/epp/web/op/webadmin/cmparticle_del.do?companyId=${companyId}&oid=${oid}");
	}
}
function delfile(cmpFileOid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/cmparticle_delfile.do?companyId=${companyId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function settop(cmpFileOid){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_settop.do?companyId=${companyId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function canceltop(cmpFileOid){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_canceltop.do?companyId=${companyId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setmain(cmpFileOid){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_setmain.do?companyId=${companyId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function cancelmain(cmpFileOid){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmparticle_cancelmain.do?companyId=${companyId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
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
function delarticleblock(oid,blockId,id){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delarticleblock2.do?companyId=${companyId}&oid="+oid+"&blockId="+blockId,
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