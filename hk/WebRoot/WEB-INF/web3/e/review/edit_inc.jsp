<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%><%@page import="com.hk.svr.impl.CompanyScoreConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div onkeydown="keydown(event)">
<hk:form oid="review_frm_edit_cmpreview" onsubmit="return subcmpreviewfrm(this.id)" action="/review/op/op_editweb.do" target="hideframe">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="labaId" value="${labaId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<div class="f_l">
					<%List<CompanyScoreConfig> list=CompanyScoreConfig.getList();
						request.setAttribute("companyScoreConfigList",list);%>
					打分
					<hk:select oid="id_score_edit_cmpreview" name="score" checkedvalue="${companyreviewvo.companyReview.score}">
						<hk:option value="0" data="view.notsetscore" res="true"/>
						<c:forEach var="conf" items="${companyScoreConfigList}">
						<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
						</c:forEach>
					</hk:select><span id="review_score_error_edit_cmpreview" class="error"></span><br/>
					
				</div>
				</td>
			</tr>
			<tr>
				<td>
					<hk:textarea oid="id_content_edit_cmpreview" name="content" value="${companyreviewvo.content}" style="width:500px;height:100px"/><br/>
						<div id="review_content_error_edit_cmpreview" class="error"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="right">
						<hk:submit value="提交" clazz="btn"/>
					</div>
				</td>
			</tr>
		</table>
</hk:form>
</div>