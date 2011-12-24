<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpArticle"%>
<%@page import="com.hk.bean.CmpOrgArticle"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" enctype="multipart/form-data" onsubmit="return subarticlefrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="orgnavId" value="${orgnavId}"/>
	<hk:hide name="orgId" value="${orgId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				名称：<hk:text name="title" maxlength="50" clazz="text" value="${cmpOrgArticle.title}" style="width:400px"/> 
				<div class="infowarn" id="_title"></div>
			</td>
		</tr>
		<tr>
			<td>
				名称是否显示： 
				<hk:radioarea name="hideTitleflg" checkedvalue="${cmpOrgArticle.hideTitleflg}" forcecheckedvalue="<%=CmpOrgArticle.HIDETITLEFLG_N %>">
				<hk:radio oid="showtitle" value="<%=CmpOrgArticle.HIDETITLEFLG_N %>"/><label for="showtitle">显示</label>
				<hk:radio oid="hidetitle" value="<%=CmpOrgArticle.HIDETITLEFLG_Y %>"/><label for="hidetitle">隐藏</label>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td>内容：<br/>
				<hk:textarea name="content" style="width: 600px;height: 300px" value="${cmpOrgArticleContent.content}"/> 
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<tr>
			<td>
				<c:set var="end" value="${4-fn:length(list)}"></c:set>
				<c:if test="${end>=0}">
					图片：<strong>（可选）</strong><br/>
					<c:forEach var="i" begin="0" end="${end}" step="1">
					<div class="divrow">
						<input type="file" size="50" name="f${i }"/>
						<input type="radio" name="topIdx" id="_idx${i }" value="${i}"/><label for="_idx${i }">此文件置顶</label>
					</div>
					</c:forEach>
					<div style="text-align: center;">
					<input type="radio" name="topIdx" id="_idx99" value="99"/><label for="_idx99">取消置顶</label>
					</div>
					图片只支持大小在1M以内的jpg，gif，png文件<br/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<c:forEach var="cf" items="${list}">
					<div class="divrow" style="text-align: center;">
						<img src="${cf.pic320 }"/><br/>
						<a class="split-r" href="javascript:delfile(${cf.oid })">删除</a> 
						<c:if test="${cf.topInFile}">
							<a class="split-r" href="javascript:canceltop(${cf.oid })">取消置顶</a>
						</c:if>
						<c:if test="${!cf.topInFile}">
							<a class="split-r" href="javascript:settop(${cf.oid })" title="图片将显示在文章内容的前面">置顶</a>
						</c:if>
						<c:if test="${cf.path==cmpOrgArticle.path}">
							<a class="split-r" href="javascript:cancelmain(${cf.oid })">取消头图</a>
						</c:if>
						<c:if test="${cf.path!=cmpOrgArticle.path}">
							<a class="split-r" href="javascript:setmain(${cf.oid })" title="如果该文章栏目下的内容为图片列表显示时，会显示文章的头图">设为头图</a>
						</c:if>
					</div>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn split-r" value="提交"/>
				<c:if test="${cmpOrgArticle==null}">
				<a href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
				</c:if>
				<c:if test="${cmpOrgArticle!=null}">
				<a href="/edu/${companyId }/${orgId}/article/${orgnavId}/${oid }.html">返回</a> 
				</c:if>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPORGARTICLE_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPORGARTICLE_CONTENT_ERROR %>={objid:"_content"};
function subarticlefrm(frmid){
	setHtml('_title','');
	setHtml('_content','');
	showGlass(frmid);
	return true;
}
function delarticle(){
	if(window.confirm('确实要删除文章？')){
		tourl("<%=path%>/epp/web/org/article_del.do?companyId=${companyId}&orgId=${orgId}&oid=${oid}");
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