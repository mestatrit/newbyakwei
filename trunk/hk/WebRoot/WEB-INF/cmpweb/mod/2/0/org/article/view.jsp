<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name }
		<a class="more" href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
	</div>
	<div class="content2">
		<c:if test="${adminorg}">
		<div class="divrow">
			<a class="split-r" href="<%=path %>/epp/web/org/article_update.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&oid=${oid}">修改</a>
			<a href="javascript:delarticle()">删除</a>
		</div>
		</c:if>
		<c:if test="${fn:length(nextlist)>0}">
			<div class="fr" style="font-weight: bold;font-size: 30px;">
				<c:forEach var="next" items="${nextlist}">
				<a href="/edu/${companyId }/${orgId}/article/${orgnavId}/${next.oid }.html">&gt;&gt;</a>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${!article.hideTitle}">
			<div style="margin-right: 50px;text-align: center;"><h1>${article.title }</h1></div>
			<div class="divrow" style="text-align: center;"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></div>
		</c:if>
		<c:if test="${topFile!=null}">
			<div class="divrow" style="text-align: center;">
				<img src="${topFile.pic600 }"/>
				<div>
					<a class="split-r" href="javascript:delfile(${topFile.oid })">删除</a> 
					<a class="split-r" href="javascript:canceltop(${topFile.oid })">取消置顶</a>
					<c:if test="${topFile.path==article.path}">
						<a class="split-r" href="javascript:cancelmain(${topFile.oid })">取消头图</a>
					</c:if>
					<c:if test="${topFile.path!=article.path}">
						<a class="split-r" href="javascript:setmain(${topFile.oid })" title="如果该文章栏目下的内容为图片列表显示时，会显示文章的头图">设为头图</a>
					</c:if>
				</div>
			</div>
		</c:if>
		<div>${articleContent.content }</div>
		<c:forEach var="cf" items="${list}">
			<div class="divrow" style="text-align: center;">
				<img src="${cf.pic600 }"/><br/>
				<a class="split-r" href="javascript:delfile(${cf.oid })">删除</a> 
				<c:if test="${cf.topInFile}">
					<a class="split-r" href="javascript:canceltop(${cf.oid })">取消置顶</a>
				</c:if>
				<c:if test="${!cf.topInFile}">
					<a class="split-r" href="javascript:settop(${cf.oid })" title="图片将显示在文章内容的前面">置顶</a>
				</c:if>
				<c:if test="${cf.path==article.path}">
					<a class="split-r" href="javascript:cancelmain(${cf.oid })">取消头图</a>
				</c:if>
				<c:if test="${cf.path!=article.path}">
					<a class="split-r" href="javascript:setmain(${cf.oid })" title="如果该文章栏目下的内容为图片列表显示时，会显示文章的头图">设为头图</a>
				</c:if>
			</div>
		</c:forEach>
	</div>
</div>
<script type="text/javascript">
function delarticle(){
	if(window.confirm('确实要删除文章？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/org/article_del.do?companyId=${companyId}&orgId=${orgId}&oid=${oid}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl('<%=path%>/epp/web/org/article.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}');
			}
		});
	}
}
function delfile(cmpFileOid){
	if(window.confirm('确实要删除图片？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/org/article_delfile.do?companyId=${companyId}&orgId=${orgId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
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
		url:"<%=path %>/epp/web/org/article_prvsettop.do?companyId=${companyId}&orgId=${orgId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
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
		url:"<%=path %>/epp/web/org/article_prvcanceltop.do?companyId=${companyId}&orgId=${orgId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
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
		url:"<%=path %>/epp/web/org/article_prvsetmain.do?companyId=${companyId}&orgId=${orgId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
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
		url:"<%=path %>/epp/web/org/article_prvcancelmain.do?companyId=${companyId}&orgId=${orgId}&cmpArticleOid=${oid}&cmpFileOid="+cmpFileOid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>

</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>