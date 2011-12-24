<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="epp.updatebbsreply"/> - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="">
	<div class="mod"><%EppViewUtil.loadCmpNavTop(request); %>
		<div class="mod_title"><hk:data key="epp.updatebbsreply"/></div>
		<div class="mod_content">
		<br/>
			<form id="replyfrm" method="post" enctype="multipart/form-data" onsubmit="return subreplyfrm(this.id)" action="<%=path%>/epp/web/cmpbbs_updatereply.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="ch" value="1"/>
				<hk:hide name="replyId" value="${replyId}"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">
							<hk:data key="cmpbbs.content"/>：
						</td>
						<td>
							<hk:textarea oid="reply_content" name="content" style="width: 700px;height: 300px" value="${cmpBbsReply.content}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<hk:data key="epp.imgfile"/>：
						</td>
						<td>
							<c:if test="${not empty cmpBbsReply.picpath}">
								<div class="divrow" id="imgcon">
									<img src="${cmpBbsReply.pic120Url }"/> 
									<a href="javascript:clearpic(${replyId })"><hk:data key="epp.deletepic"/></a>
								</div>
							</c:if>
							<input type="file" name="f" size="50"/>
							<div class="b"><hk:data key="epp.cmpbbs.uploadimg.tip"/></div>
							<div class="infowarn" id="replymsg"></div>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
							<div align="center">
							<hk:submit clazz="btn" res="true" value="epp.submit"/> 
							<a href="${denc_return_url }"><hk:data key="epp.return"/></a>
							</div>
						</td>
					</tr>
				</table>
			</form>
			<script type="text/javascript">
			var err_code_<%=Err.CMPBBSREPLY_CONTENT_ERROR %>={objid:"replymsg"};
			var err_code_<%=Err.UPLOAD_ERROR %>={objid:"replymsg"};
			var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"replymsg"};
			var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"replymsg"};
			var err_code_<%=Err.MUST_PIC_UPLOAD %>={objid:"replymsg"};
			var err_code_<%=Err.ONLY_PIC_UPLOAD %>={objid:"replymsg"};
			function subreplyfrm(frmid){
				setHtml('replymsg','');
				showGlass(frmid);
				return true;
			}
			function updateerror(error,msg,v){
				setHtml('replymsg',msg);
				hideGlass();
			}
			function updateok(error,msg,v){
				tourl('<%=path%>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${cmpBbsReply.bbsId}&page=${page}&replyId=${replyId}');
			}
			function clearpic(bbsId){
				if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
					$.ajax({
						type:"POST",
						url:"<%=path%>/epp/web/cmpbbs_clearreplypic.do?companyId=${companyId}&replyId=${replyId}",
						cache:false,
				    	dataType:"html",
						success:function(data){
							setHtml('imgcon','');
						}
					});
				}
			}
			function keysubmit(event){
				if((event.ctrlKey)&&(event.keyCode==13)){
					if(subreplyfrm('replyfrm')){
						getObj('replyfrm').submit();
					}
				}
			}
			$(document).ready(function() {
				$('#reply_content').bind('keydown',function(event){
					keysubmit(event);
					});
			});
			</script>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>