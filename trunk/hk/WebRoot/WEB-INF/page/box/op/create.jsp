<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Box"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.box.createbox"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">发布火酷宝箱</div>
	<div>
		<hk:form action="/box/op/op_create.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<c:if test="${company!=null}">
			<div class="hang">${company.name }</div>
		</c:if>
			<div class="hang">
				宝箱名称<span class="ruo s">(最多15个字符)</span><br/>
				<hk:text name="name" maxlength="15" value="${o.name}"/>
			</div>
			<c:if test="${admin}">
				<div class="hang">
					是否是虚拟宝箱:<br/>
					<hk:radioarea name="virtualflg" checkedvalue="<%=Box.VIRTUALFLG_N %>">
						<hk:radio value="<%=Box.VIRTUALFLG_N %>" data="否"/> 
						<hk:radio value="<%=Box.VIRTUALFLG_Y %>" data="是"/> 
					</hk:radioarea>
				</div>
			</c:if>
			<div class="hang">
				地区<span class="ruo s">(为空时默认为全球)</span><br/>
				<hk:text name="zoneName" value="${zoneName}"/>
			</div>
			<div class="hang">
				宝箱总数量<br/>
				<hk:text name="totalCount" clazz="number" value="${o.totalCount}"/>
			</div>
			<div class="hang">
				开箱起始时间<br/>
				<fmt:formatDate var="begint" value="${o.beginTime}" pattern="yyyyMMddHHmm"/>
				<hk:text name="begint" maxlength="12" value="${begint}"/><br/>
				<span class="ruo s">(格式示例：200907010808，表示2009年7月1号8点8分开始)</span>
			</div>
			<div class="hang">
				开箱结束时间<br/>
				<fmt:formatDate var="endt" value="${o.endTime}" pattern="yyyyMMddHHmm"/>
				<hk:text name="endt" maxlength="12" value="${endt}"/>
			</div>
			<div class="hang">
				短信开箱口令<span class="ruo s">(以bx开头的10个字符)</span><br/>
				<hk:text name="boxKey" maxlength="10" value="${o.boxKey}"/><br/>
				<span class="ruo s"><hk:data key="box.boxKey.tip"/></span>
			</div>
			<div class="hang">
				每用户周期内开箱限制<br/>
				<hk:text name="precount" size="5" clazz="numbermin" value="${o.precount}"/>个/
				<hk:select name="pretype" checkedvalue="${o.pretype}">
					<hk:option value="0" data="不限"/>
					<c:forEach var="pre" items="${prelist}">
					<hk:option value="${pre.typeId}" data="${pre.name}"/>
					</c:forEach>					
				</hk:select>
			</div>
			<div class="hang">
				参与方式<br/>
				<hk:select name="opentype" checkedvalue="${o.opentype}">
					<hk:option value="0" data="短信和网站"/>
					<hk:option value="1" data="短信"/>
					<hk:option value="2" data="网站"/>
				</hk:select>
			</div>
			<div class="hang">
				箱子介绍<br/>
				<hk:textarea name="intro" cols="20" rows="5" value="${o.intro}"/>
			</div>
			<div>
				<hk:submit name="create" value="下一步" clazz="sub"/>
				<hk:submit name="cancel" value="取消并返回"/>
			</div>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>