<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="web.pub.util.EppViewUtil"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${cmpBbs.title} - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter"><%EppViewUtil.loadCmpNavTop(request); %>
<div  class="divrow" style="margin-bottom: 20px;">
	<a class="b" href="/column/${companyId }/${cmpnav_cmpbbs_column.oid}">${cmpnav_cmpbbs_column.name }</a>  &gt; 
	<a class="b" href="<%=path %>/epp/web/cmpbbs_kind.do?companyId=${companyId}&kindId=${cmpBbsKind.kindId}">${cmpBbsKind.name }</a> &gt;
	<a class="b" href="<%=path %>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${bbsId}">${cmpBbs.title }</a>
</div>
<div class="bbs">
	<div class="user">
	<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${cmpBbs.userId}">
	<img alt="${bbsUser.nickName }" title="${bbsUser.nickName }" src="${bbsUser.head80Pic }" width="80" height="80"/><br/>
	${bbsUser.nickName }</a>
	</div>
	<div class="content">
		<table class="bbst" cellspacing="0" cellpadding="0" border="0">
		  <tbody>
		  	<tr><td class="lt" width="40" height="30"></td><td class="ct" height="30"></td><td class="rt" width="40" height="30"></td></tr>
		    <tr>
		    	<td class="lm" valign="top" width="40">&nbsp;</td>
				<td class="origin" valign="top">
					<div class="divrow">
						<h1>${cmpBbs.title }</h1>
						<c:set var="createtime" scope="request" value="${cmpBbs.createTime}"></c:set>
						<span class="ruo">
						<hk:data key="epp.bbs.replycount" arg0="${cmpBbs.replyCount}"/> 
						<hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></span> 
						<div>
						<c:if test="${loginUser.userId==cmpBbs.userId}">
							<span class="split-r"><a href="javascript:updatebbs(${bbsId })"><hk:data key="epp.update"/></a></span>
						</c:if>
						<c:if test="${loginUser.userId==cmpBbs.userId || userAdmin}">
							<span class="split-r"><a href="javascript:delbbs(${bbsId })"><hk:data key="epp.delete"/></a></span>
						</c:if>
						<c:if test="${cmpBomber!=null}">
							<span class="split-r"><a href="javascript:bombBbs(${bbsId })">炸掉</a></span>
						</c:if>
						</div>
					</div>
					${cmpBbsContent.content }
					<c:if test="${not empty cmpBbs.picpath}">
						<div class="divrow">
						<img src="${cmpBbs.pic600Url }"/>
						</div>
					</c:if>
				</td>
				<td class="rm" width="40"></td></tr>
		    <tr><td class="lb" width="40" height="20"></td><td class="cb" height="20"></td><td class="rb" width="40" height="20"></td></tr>
			</tbody>
		</table>
	</div>
	<div class="clr"></div>
</div>
<a name="reply"></a>
<c:forEach var="reply" items="${list}" varStatus="idx">
<div class="reply" id="reply${reply.replyId }">
	<div class="user">
		<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${reply.userId}">
		<img alt="${reply.user.nickName }" title="${reply.user.nickName }" src="${reply.user.head80Pic }" width="80" height="80"/><br/>
		${reply.user.nickName }</a>
	</div>
	<div class="content"<c:if test="${lastreply_idx==idx.index}"> id="lastreply_content"</c:if>>
		<div class="info">
			<c:if test="${lastreply_idx==idx.index}">
			<a name="lastreply"></a>
			</c:if>
			<a name="reply_n_${reply.replyId }"></a>
			<c:if test="${loginUser.userId==reply.userId}">
				<span class="split-r">
					<a href="javascript:updatereply(${reply.replyId })"><hk:data key="epp.update"/></a>
				</span>
			</c:if>
			<c:if test="${loginUser.userId==reply.userId || userAdmin}">
				<span class="split-r">
					<a href="javascript:delreply(${reply.replyId })"><hk:data key="epp.delete"/></a>
				</span>
			</c:if>
			<c:if test="${cmpBomber!=null}">
				<span class="split-r">
					<a href="javascript:bombReply(${reply.replyId })">炸掉</a>
				</span>
			</c:if>
			<hk:data key="epp.bbs.user_reply_on"/> <fmt:formatDate value="${reply.createTime}" pattern="yy-MM-dd HH:mm"/>
		</div>
		<div class="incon">
		${reply.content }
		<c:if test="${not empty reply.picpath}">
		<div class="divrow">
		<img src="${reply.pic600Url }"/>
		</div>
		</c:if>
		</div>
		<div class="floor">${(page-1)*20+(idx.index+1)}</div>
	</div>
	<div class="clr"></div>
</div>
</c:forEach>
<div>
<c:set var="page_url" scope="request"><%=path%>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${bbsId}</c:set>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
<c:if test="${loginUser!=null}">
<br/>
<div class="reply">
	<div class="user">
		<img alt="${loginUser.nickName }" title="${loginUser.nickName }" src="${loginUser.head80Pic }" width="80" height="80"/><br/>
		${loginUser.nickName }
	</div>
	<div class="content">
		<form id="replyfrm" method="post" enctype="multipart/form-data" onsubmit="return subreplyfrm(this.id)" action="<%=path %>/epp/web/cmpbbs_createreply.do" target="hideframe">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="bbsId" value="${bbsId}"/>
			<strong><hk:data key="epp.cmpbbs.reply.content"/> ：</strong><br/>
			<textarea onkeydown="keysubmit(event)" name="content" style="width: 700px;height: 300px"></textarea>
			<div class="divrow">
				<hk:data key="epp.imgfile"/>：
				<input type="file" name="f" size="50"/>
				<div class="b"><hk:data key="epp.cmpbbs.uploadimg.tip"/></div>
				<div class="infowarn" id="replymsg"></div>
			</div>
			<div align="center">
				<hk:submit clazz="btn" value="epp.submit" res="true"/>
			</div>
		</form>
	</div>
	<div class="clr"></div>
</div>
</c:if>
</div>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript">
var err_code_<%=Err.CMPBBSREPLY_CONTENT_ERROR %>={objid:"replymsg"};
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"replymsg"};
var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"replymsg"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"replymsg"};
var err_code_<%=Err.MUST_PIC_UPLOAD %>={objid:"replymsg"};
var err_code_<%=Err.ONLY_PIC_UPLOAD %>={objid:"replymsg"};
function bombBbs(bbsId){
	if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
		tourl('<%=path%>/epp/web/cmpbbs_prvbombbbs.do?companyId=${companyId}&bbsId=${bbsId}');
	}
}
function bombReply(replyId){
	if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/cmpbbs_prvbombreply.do?companyId=${companyId}&replyId="+replyId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function delbbs(bbsId){
	if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
		tourl('<%=path%>/epp/web/cmpbbs_del.do?companyId=${companyId}&bbsId=${bbsId}');
	}
}
function updatebbs(bbsId){
	tourl('<%=path%>/epp/web/cmpbbs_update.do?companyId=${companyId}&bbsId='+bbsId+'&return_url='+encodeLocalURL());
}
function updatereply(replyId){
	tourl('<%=path%>/epp/web/cmpbbs_updatereply.do?companyId=${companyId}&page=${page}&replyId='+replyId+'&return_url='+encodeLocalURL());
}
function delreply(replyId){
	if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/cmpbbs_delreply.do?companyId=${companyId}&replyId="+replyId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function subreplyfrm(frmid){
	setHtml('replymsg','');
	showGlass(frmid);
	return true;
}
function createerror(error,msg,v){
	setHtml('replymsg',msg);
	hideGlass();
}
function createok(error,msg,v){
	tourl('<%=path%>/epp/web/cmpbbs_lastpage.do?companyId=${companyId}&bbsId=${bbsId}');
}
function keysubmit(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subreplyfrm('replyfrm')){
			getObj('replyfrm').submit();
		}
	}
}
<c:if test="${replyId>0}">
window.location.hash='reply_n_${replyId}';
$('#reply${replyId}').effect('highlight',{},2000,function(){});
</c:if>
<c:if test="${replyId==null}">
	<c:if test="${page>1 && reply==null}">window.location.hash='reply';</c:if>
	<c:if test="${reply==1}">
	window.location.hash='lastreply';
	$('#lastreply_content').effect('highlight',{},2000,function(){});
	</c:if>
</c:if>

</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>