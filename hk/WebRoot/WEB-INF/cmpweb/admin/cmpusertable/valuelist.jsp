<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><c:set var="html_title" scope="request">${o.name}</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name } - 用户数据</div>
		<div class="mod_content">
			<c:if test="${fn:length(volist)>0}">
				<ul class="datalist">
					<c:forEach var="vo" items="${volist}">
						<li>
							<div>
								<c:forEach var="v" items="${vo.userTableFieldValues}">
									<strong>${v.cmpUserTableField.name }：</strong> 
									<c:if test="${v.cmpUserTableField.hasOption}">
										<c:forEach var="option" items="${v.selectedOptionList}">
											<span class="split-r">${option.data }</span>
										</c:forEach>
									</c:if>
									<c:if test="${!v.cmpUserTableField.hasOption}">${v.value }</c:if><br/>
								</c:forEach>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(volist)==0 && page==1}">
				<div class="nodata">还没有任何数据</div>
			</c:if>
			<c:if test="${fn:length(volist)==0 && page>1}">
				<div class="nodata">本页没有数据</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#ffffcc');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>