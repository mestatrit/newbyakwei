<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:form action="${form_action}">
	<hk:hide name="pid" value="${pid}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:data key="product.name"/>(<span class="ruo s"><hk:data key="product.name.tip"/></span>):<br/>
	<hk:text name="name" value="${o.name}" maxlength="30"/><br/><br/>
	<hk:data key="product.sort"/>:<br/>
	<hk:select name="sortId" checkedvalue="${o.sortId}">
		<hk:option value="0" data=""/>
		<c:forEach var="s" items="${sortlist}">
		<hk:option value="${s.sortId}" data="${s.name}"/>
		</c:forEach>
	</hk:select><br/><br/>
	<hk:data key="product.money"/>(<span class="ruo s"><hk:data key="product.money.tip"/></span>):<br/>
	<hk:text name="money" value="${o.money}" maxlength="30"/><hk:data key="product.money.unit"/><br/><br/>
	<hk:data key="product.rebate"/>:<br/>
	<hk:text name="rebate" value="${o.rebate}" maxlength="5"/><br/>
	<span class="ruo s"><hk:data key="product.rebate.tip"/></span><br/><br/>
	<hk:data key="product.intro"/>(<span class="ruo s"><hk:data key="product.intro.tip"/></span>):<br/>
	<hk:textarea name="intro" value="${o.intro}"/><br/>
	<hk:submit value="view.submit" res="true"/>
</hk:form>