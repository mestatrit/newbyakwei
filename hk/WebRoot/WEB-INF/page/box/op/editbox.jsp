<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Box"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${editbox!=null}">
<%request.setAttribute("box",request.getAttribute("editbox")); %>
</c:if>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/box/op/op_editbox.do">
			<hk:hide name="boxId" value="${o.boxId}"/>
			<hk:hide name="fromadmin" value="${fromadmin}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="t" value="${t}"/>
			宝箱名称:<br/>
			<hk:text name="name" value="${o.name}" maxlength="15"/><br/><br/>
			地区<span class="ruo s">(为空时默认为全球)</span><br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
			<c:if test="${admin}">
				是否可以获得副产品:<br/>
				<hk:radioarea name="otherPrizeflg" checkedvalue="${o.otherPrizeflg}">
					<hk:radio value="<%=Box.OTHERPRIZEFLG_N %>" data="否"/> 
					<hk:radio value="<%=Box.OTHERPRIZEFLG_Y %>" data="是"/> 
				</hk:radioarea>
				<br/><br/>
			</c:if>
			宝箱数量:<br/>
			<hk:text name="totalCount" value="${o.totalCount}" maxlength="8"/><br/><br/>
			开始时间:<br/>
			<fmt:formatDate var="begint" value="${o.beginTime}" pattern="yyyyMMddHHmm"/>
			<hk:text name="begint" value="${begint}" maxlength="12"/> <br/><br/>
			结束时间:<br/>
			<fmt:formatDate var="endt" value="${o.endTime}" pattern="yyyyMMddHHmm"/>
			<hk:text name="endt" value="${endt}" maxlength="12"/><br/><br/>
			短信开箱指令:<br/>
			<hk:text name="boxKey" maxlength="10" value="${o.boxKey}"/><br/>
			<span class="ruo s"><hk:data key="box.boxKey.tip"/></span><br/><br/>
			宝箱介绍:<br/>
			<hk:textarea name="intro" value="${o.intro}"/><br/><br/>
			宝箱类别:<br/>
			<hk:select name="boxType" checkedvalue="${o.boxType}">
				<hk:option value="0" data=""/>
				<c:forEach var="t" items="${typeList}">
					<hk:option value="${t.typeId}" data="${t.name}"/>
				</c:forEach>
			</hk:select>
			<br/><br/>
			每用户周期内开箱限制:<br/>
			<hk:text name="precount" size="5" clazz="numbermin" value="${o.precount}"/>个/
			<hk:select name="pretype" checkedvalue="${o.pretype}">
				<hk:option value="0" data="不限"/>
				<c:forEach var="pre" items="${prelist}">
				<hk:option value="${pre.typeId}" data="${pre.name}"/>
				</c:forEach>					
			</hk:select>
			<br/>
			参与方式:<br/>
			<hk:select name="opentype" checkedvalue="${o.opentype}">
				<c:forEach var="t" items="${opentypelist}">
					<hk:option value="${t.typeId}" data="${t.name}"/>
				</c:forEach>
			</hk:select>
			<br/><br/>			
			<div class="hang">
				<hk:submit value="保存" clazz="sub"/>
			</div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/op/op_getbox.do?boxId=${boxId }&t=${t }&page=${repage}&fromadmin=${fromadmin }">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>