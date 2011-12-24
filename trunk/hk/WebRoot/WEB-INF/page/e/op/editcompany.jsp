<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.CompanyKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.op.editcompany.title"/></c:set>
<%List<CompanyKind> list = CompanyKindUtil.getCompanKindList();
request.setAttribute("kindlist", list);%>
<hk:wap title="${view_title} - ${o.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/op/op_tosetmap.do?companyId=${companyId}"><hk:data key="view.company.setmap"/></hk:a>|
			<hk:a href="/e/op/photo/photo_toadd.do?companyId=${companyId}"><hk:data key="view.company.continueuploadimage"/></hk:a>|
			<hk:a href="/e/op/photo/photo_smallphoto.do?companyId=${companyId}"><hk:data key="view.company.photo.manage"/></hk:a>
			<c:if test="${o.userId>0}">
				|<hk:a href="/e/op/product/op_sortlist.do?companyId=${companyId}"><hk:data key="view.cmpproductsort.manage"/></hk:a>|
				<hk:a href="/e/op/product/op_productlist.do?companyId=${companyId}"><hk:data key="view.cmpproduct.manage"/></hk:a>
			</c:if>
			<c:if test="${cmpSmsPort==null && o.userId>0}">
			|<hk:a href="/e/op/op_createCompanySmsPort.do?companyId=${companyId}"><hk:data key="view.company.applysmsport"/></hk:a>
			</c:if>
			<c:if test="${o.userId>0}">
			|<hk:a href="/e/op/auth/op_review.do?companyId=${companyId}"><hk:data key="view.company.mgrreview"/></hk:a>
			|<hk:a href="/e/op/authcmp/cmpinfo.do?companyId=${companyId}">企业域名</hk:a>
			</c:if>
		</hk:rmBlankLines>
	</div>
	<div class="hang even">
		<hk:form action="/e/op/op_edit.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<c:if test="${companyport!=null}">
				<hk:data key="view.companyport"/>:<br/>
				${companyport }<br/><br/>
			</c:if>
			<hk:data key="company.name"/>:<br/>
			<hk:text name="name" value="${o.name}"/><br/><span class="ruo s"><hk:data key="company.name.tip"/></span><br/><br/>
			<hk:data key="company.tel"/>:<br/>
			<hk:text name="tel" value="${o.tel}" maxlength="30"/><br/>
			<span class="ruo s"><hk:data key="company.tel.tip"/></span><br/><br/>
			所在城市:<br/>
			<hk:text name="zoneName" value="${zoneName}" maxlength="10"/><br/><br/>
			<hk:data key="company.kindId"/>:<br/>
			<hk:select name="kindId" checkedvalue="${o.kindId}">
				<hk:option value="0" data=""/>
			<c:forEach var="k" items="${kindlist}">
				<hk:option value="${k.kindId}" data="${k.name}"/>
			</c:forEach>
			</hk:select><br/><br/>
			<hk:data key="company.addr"/>(<span class="ruo s"><hk:data key="company.addr.tip"/></span>):<br/>
			<hk:textarea name="addr" value="${o.addr}"/><br/><br/>
			<hk:data key="company.traffic"/>(<span class="ruo s"><hk:data key="company.traffic.tip"/></span>):<br/>
			<hk:textarea name="traffic" value="${o.traffic}"/><br/><br/>
			<hk:data key="company.intro"/>(<span class="ruo s"><hk:data key="company.intro.tip"/></span>):<br/>
			<hk:textarea name="intro" value="${o.intro}" /><br/><br/>
			<c:if test="${companyKind.priceTip>0}">
				<hk:data key="companykind.prizetip${o.kindId}"/>:<br/>
				<hk:text name="price" value="${o.price}"/>元<br/><br/>
			</c:if>
			<hk:data key="company.rebate"/>(<span class="ruo s"><hk:data key="company.rebate.tip"/></span>):<br/>
			<c:if test="${o.rebate==0}">
				<hk:text name="rebate"/>折<br/>
			</c:if>
			<c:if test="${o.rebate!=0}">
				<hk:text name="rebate" value="${o.rebate}"/>折<br/>
			</c:if>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>