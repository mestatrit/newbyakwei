<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpArticle"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" enctype="multipart/form-data" onsubmit="return subarticlefrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="navoid" value="${navoid}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				名称：<hk:text name="title" maxlength="50" clazz="text" value="${cmpArticle.title}" style="width:400px"/> 
				<div class="infowarn" id="_title"></div>
			</td>
		</tr>
		<tr>
			<td>
				名称是否显示： 
				<hk:radioarea name="hideTitleflg" checkedvalue="${cmpArticle.hideTitleflg}">
				<hk:radio oid="showtitle" value="<%=CmpArticle.HIDETITLEFLG_N %>"/><label for="showtitle">显示</label>
				<hk:radio oid="hidetitle" value="<%=CmpArticle.HIDETITLEFLG_Y %>"/><label for="hidetitle">隐藏</label>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td>
				关键词： <hk:text name="tagdata" clazz="text" value="${tagdata}"/><br/>
				多个关键词用 , 分隔，最多支持3个关键词，每个关键词的长度最多20个字符
			</td>
		</tr>
		<tr>
			<td>内容：<br/>
				<hk:textarea name="content" style="width: 600px;height: 300px" value="${cmpArticleContent.content}"/> 
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<c:if test="${fn:length(grouplist)>0}">
			<tr>
				<td>组(可选)：
				<hk:select name="groupId" checkedvalue="${cmpArticle.groupId}">
					<hk:option value="0" data="请选择"/>
					<c:forEach var="group" items="${grouplist}">
						<hk:option value="${group.groupId}" data="${group.name}"/>
					</c:forEach>
				</hk:select>
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<c:set var="end" value="${4-fn:length(list)}"></c:set>
				<c:if test="${end>=0}">
					图片或flash文件：<strong>（可选）</strong><br/>
					<c:forEach var="i" begin="0" end="${end}" step="1">
					<div class="divrow">
						<input type="file" size="50" name="f${i }"/>
						<input type="radio" name="topIdx" id="_idx${i }" value="${i}"/><label for="_idx${i }">此文件置顶</label>
					</div>
					</c:forEach>
					<div style="text-align: center;">
					<input type="radio" name="topIdx" id="_idx99" value="99"/><label for="_idx99">取消置顶</label>
					</div>
					图片只支持大小在2M以内的jpg，gif，png文件<br/>
					flash只支持大小在500k以内swf格式的文件<br/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<c:forEach var="cf" items="${list}">
					<div class="divrow" style="text-align: center;">
						<c:if test="${cf.imageShow}">
							<img src="${cf.cmpFilePic320 }"/>
						</c:if>
						<c:if test="${cf.flashShow}">
							<embed width="400" height="200" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${cf.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
						</c:if>
					</div>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn" value="提交"/> 
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPARTICLE_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPARTICLE_CONTENT_ERROR %>={objid:"_content"};
function subarticlefrm(frmid){
	setHtml('_title','');
	setHtml('_content','');
	showGlass(frmid);
	return true;
}
</script>