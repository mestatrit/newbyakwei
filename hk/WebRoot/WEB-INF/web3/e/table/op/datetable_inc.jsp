<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<style>
ul.ul-menu-1{
font-size: 14px;
font-weight: bold;
width:166px;
}
ul.ul-menu-1 li a{
display: block;
padding: 0px 0px 0px 5px;
width:161px;
}
ul.ul-menu-1 ul li a{
width:136px;
padding-left: 30px;
font-weight: normal;
}
</style>
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit">座位分类</div>
		<div class="cont">
			<ul class="ul-menu-1">
				<c:set var="basecounturl"><%=path %>/e/op/auth/table_countinfo.do?companyId=${companyId}&dateflg=${dateflg}&begin_date=${begin_date }&end_date=${end_date }</c:set>
				<li><a class="n1" href="${basecounturl }&t=all"><hk:data key="view.company.allcmptable"/></a></li>
				<c:forEach var="cmpTableSort" items="${sortlist}">
					<li><a class="n1" href="${basecounturl }&t=sort&sortId=${cmpTableSort.sortId}">${cmpTableSort.name } <span>(${cmpTableSort.freeCount }/${cmpTableSort.totalCount })</span></a>
						<c:if test="${cmpTableSort.sortId==cmpTable.sortId || cmpTableSort.sortId==sortId}">
						<ul>
							<c:forEach var="table" items="${tablelist}">
								<c:set var="css_class"><c:if test="${tableId==table.tableId}">active</c:if></c:set>
								<li>
									<a class="n1 ${css_class }" href="<%=path %>/e/op/auth/table_datetable.do?companyId=${companyId}&tableId=${table.tableId}&dateflg=${dateflg}&begin_date=${begin_date }&end_date=${end_date }">${table.tableNum }</a>
								</li>
								<c:set var="css_class"></c:set>
							</c:forEach>
						</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>