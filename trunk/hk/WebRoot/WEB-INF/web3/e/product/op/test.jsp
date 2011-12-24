<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.impl.CompanyScoreConfig"%>
<%@page import="java.util.List"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form oid="review_frm_2" onsubmit="return subproductreviewfrm2(this.id)" action="/op/product_createreview.do" target="hideframe">
	<hk:hide name="labaId" value="${labaId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0" width="600px">
			<tr>
				<td>
				<div class="f_l" style="width: 500px;">
					打分
					<hk:select oid="id_score_2" name="score">
						<hk:option value="0" data="view.notsetscore" res="true"/>
						<c:forEach var="conf" items="${companyScoreConfigList}">
						<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
						</c:forEach>
					</hk:select><span id="review_score_2_error" class="error"></span><br/>
				</div>
				</td>
			</tr>
			<tr>
				<td>
					<hk:textarea name="content" style="width:500px;height:100px"/><br/>
						<div id="review_content_2_error" class="error"></div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<hk:submit value="写新评论" clazz="btn"/>
				</td>
			</tr>
		</table>
</hk:form>