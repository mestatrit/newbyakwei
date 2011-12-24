<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="tagdata"><c:forEach var="ref" items="${tagreflist}">${ref.cmpArticleTag.name }|</c:forEach></c:set>
<c:set var="title_value" scope="request">${cmpArticle.title }|${cmpNav.name }|${tagdata}</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpArticle.title }|${cmpNav.name }|${o.name}|${tagdata}"/>
<meta name="description" content="${cmpArticle.title }|${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="page_l">
	<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
</div>
<div class="page_r">
	<div>
	<c:if test="${fn:length(after_cmparticle_list)>0}">
	<div class="f_r" style="font-weight: bold;font-size: 30px;">
		<a href="<%=path %>/epp/web/cmparticle_next.do?companyId=${companyId }&oid=${oid }&navId=${navId}">&gt;&gt;</a>
	</div>
	</c:if>
	<c:if test="${!cmpArticle.hideTitle}">
		<div style="margin-right: 50px;text-align: center;"><h1 style="display: inline;">${cmpArticle.title }</h1></div>
	</c:if>
	</div>
	<c:if test="${!cmpArticle.hideTitle}">
	<div class="divrow" style="text-align: center;"><fmt:formatDate value="${cmpArticle.createTime}" pattern="yyyy-MM-dd"/></div>
	</c:if>
	<c:if test="${cmpNav.hasApplyForm}">
		<a href="#form"><hk:data key="epp.input.cmpjoininapply.form"/></a>
	</c:if>
	<c:if test="${topCmpFile!=null}">
		<div class="divrow" style="text-align: center;">
			<c:if test="${topCmpFile.imageShow}">
				<img src="${topCmpFile.cmpFilePic600 }"/>
			</c:if>
			<c:if test="${topCmpFile.flashShow}">
				<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${topCmpFile.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
			</c:if><br/>
		</div>
	</c:if>
	<div>${cmpArticleContent.content }</div>
	<c:forEach var="cf" items="${list}">
		<div class="divrow" style="text-align: center;">
			<c:if test="${cf.imageShow}">
				<img src="${cf.cmpFilePic600 }"/>
			</c:if>
			<c:if test="${cf.flashShow}">
				<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${cf.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
			</c:if><br>
		</div>
	</c:forEach>
	<c:if test="${cmpNav.hasApplyForm}">
		<div class="divrow">
			<div class="mod">
				<div class="mod_title"><hk:data key="epp.input.cmpjoininapply.form"/></div>
				<div class="mod_content">
					<br/><a name="form"></a>
					<form id="applyfrm" method="post" onsubmit="return subapplyform(this.id)" action="<%=path %>/epp/web/cmpjoininapply_create.do" target="hideframe">
						<hk:hide name="companyId" value="${companyId}"/>
						<table class="nt all" cellpadding="0" cellspacing="0">
							<tr>
								<td width="120px" align="right">
									<hk:data key="epp.cmpjoininapply.name"/>：
								</td>
								<td>
									<input type="text" name="name" class="text"/>
									<div class="infowarn" id="_name"></div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<hk:data key="epp.cmpjoininapply.tel"/>：
								</td>
								<td>
									<input type="text" name="tel" class="text"/>
									<div class="infowarn" id="_tel"></div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<hk:data key="epp.cmpjoininapply.mobile"/>：
								</td>
								<td>
									<input type="text" name="mobile" class="text"/>
									<div class="infowarn" id="_mobile"></div>
								</td>
							</tr>
							<tr>
								<td width="120px" align="right">
									<hk:data key="epp.cmpjoininapply.cmpname"/>：
								</td>
								<td>
									<input type="text" name="cmpname" class="text"/>
									<div class="infowarn" id="_cmpname"></div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<hk:data key="epp.cmpjoininapply.content"/>：
								</td>
								<td>
									<textarea name="content" style="width: 500px;height: 200px"></textarea>
									<div class="infowarn" id="_content"></div>
								</td>
							</tr>
							<tr>
								<td align="right">
								</td>
								<td>
									<div align="center">
										<hk:submit value="epp.submit" clazz="btn" res="true"/>
									</div>
								</td>
							</tr>
						</table>
					</form>
					<script type="text/javascript">
					var err_code_<%=Err.CMPJOININAPPLY_NAME_ERROR %>={objid:"_name"};
					var err_code_<%=Err.CMPJOININAPPLY_TEL_ERROR %>={objid:"_tel"};
					var err_code_<%=Err.CMPJOININAPPLY_MOBILE_ERROR %>={objid:"_mobile"};
					var err_code_<%=Err.CMPJOININAPPLY_CMPNAME_ERROR %>={objid:"_cmpname"};
					var err_code_<%=Err.CMPJOININAPPLY_CONTENT_ERROR %>={objid:"_content"};
					function subapplyform(frmid){
						setHtml('_name','');
						setHtml('_tel','');
						setHtml('_mobile','');
						setHtml('_cmpname','');
						setHtml('_content','');
						showGlass(frmid);
						return true;
					}
					function createerror(error,msg,v){
						setHtml(getoidparam(error),msg);hideGlass();
					}
					function createok(error,msg,v){
						refreshurl();
					}
					</script>
				</div>
			</div>
		</div>
	</c:if>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>