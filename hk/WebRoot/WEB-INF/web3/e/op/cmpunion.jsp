<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">申请加入联盟</c:set>
<c:set var="mgr_content" scope="request">
	<c:if test="${cmpUnion!=null}">
		<div class="text_14"><strong>您现在已经加入的联盟是 <a href="http://mall.huoku.com/u/${cmpUnion.uid }" target="_blank">${cmpUnion.name }</a> </strong></div>
	</c:if>
	<div class="text_14">
		<strong>搜索联盟</strong><br/>
		<hk:form method="get" action="/e/op/op_cmpunionlist.do">
			<hk:hide name="s" value="1"/>
			<hk:hide name="companyId" value="${companyId}"/>
			联盟名称：<hk:text name="name" value="${name}" clazz="text split-r"/><br/><br/>
			地区：
			
			<jsp:include page="../../../web4/inc/zonesel.jsp"></jsp:include>
			<script type="text/javascript">
			initselected(${pcityId});
			</script>
			<hk:submit value="搜索" clazz="btn"/>
		</hk:form><br/>
	</div>
	<div>
		<c:if test="${s==1 && fn:length(list)==0}"><div class="heavy">没有找到符合条件的数据</div></c:if>
		<c:if test="${s==1 && fn:length(list)>0}">
			<ul class="orderlist">
				<li class="bg1">
					<table class="infotable" cellpadding="0" cellspacing="0">
						<tr>
							<td width="200px">名称</td>
							<td width="300px">地址</td>
							<td width="80px">
							</td>
						</tr>
					</table>
				</li>
				<c:forEach var="u" items="${list}">
					<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
						<table class="infotable" cellpadding="0" cellspacing="0">
							<tr>
								<td width="200px">${u.name }</td>
								<td width="300px">${u.addr }</td>
								<td width="80px">
								<a id="join${u.uid }" href="javascript:join(${u.uid })">申请加入</a>
								</td>
							</tr>
						</table>
					</li>
				</c:forEach>
			</ul>
			<div>
				<hk:page midcount="10" url="/e/op/op_cmpunionlist.do?companyId=${companyId}&name=${enc_name }"/>
				<div class="clr"></div>
			</div>
		</c:if>
	</div>
<script type="text/javascript">
function join(id){
	showSubmitDivForObj("join"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/op_requestjoinincmpunion.do?companyId=${companyId}&uid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}

</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>