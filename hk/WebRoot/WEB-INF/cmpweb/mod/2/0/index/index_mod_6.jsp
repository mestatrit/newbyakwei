<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%>
<%@page import="web.pub.util.CmpCssIterator"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<script type="text/javascript">
function tagsel(blockId){
    $('.block' + blockId + '_taba').each(function(i){
        if ($(this).attr('class').indexOf('active') != -1) {
            var content_id = $(this).attr('id') + "_content";
            $('#' + content_id).css('display', 'none');
            $(this).removeClass('active');
        }
    });
}

function tagselinit(blockId){
    $('.tab .block' + blockId + '_taba').hover(function(){
        tagsel(blockId);
        $('#' + $(this).attr('id') + "_content").css('display', 'block');
        $(this).addClass('active');
    }, function(){
    });
}
</script>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==6}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,false,48);
		EppViewUtil.loadCmpNavFromArticle(request);%>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
			<div class="mod">
				<div class="tab">
					<c:forEach var="tagnav" items="${tagnavlist}" varStatus="idx">
						<a id="taba_${block.blockId}_${tagnav.oid }" href="javascript:void(0)" class="taba block${block.blockId }_taba<c:if test="${idx.index==0}"> active</c:if>">${tagnav.name }</a>
					</c:forEach>
					<div class="clr"></div>
					<div class="content" style="height: 150px;">
						<c:forEach var="tagnav" items="${tagnavlist}" varStatus="idx">
							<div id="taba_${block.blockId}_${tagnav.oid }_content" style="display: <c:if test="${idx.index==0}">block</c:if><c:if test="${idx.index!=0}">none</c:if>;">
								<ul class="list2">
								<%request.setAttribute("tagnav_css_iterator", new CmpCssIterator()); %>
									<c:forEach var="article" items="${block_cmparticlelist}">
										<c:if test="${tagnav.oid==article.cmpNavOid}">
											<li<c:if test="${tagnav_css_iterator.show==0}"> class="odd"</c:if>>
												<a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">${article.title }</a>
											</li>
										</c:if>
									</c:forEach>
									<c:remove var="tagnav_css_iterator" scope="request"/>
								</ul>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<script type="text/javascript">
			tagselinit(${block.blockId});
			</script>
		</c:if>
	</c:if>
</c:forEach>