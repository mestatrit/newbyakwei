<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${editbox!=null}">
<%request.setAttribute("box",request.getAttribute("editbox")); %>
</c:if>
<hk:wap title="${box.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/box/admin/adminbox_editbox.do">
			<hk:hide name="boxId" value="${box.boxId}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="t" value="${t}"/>
			<div class="c4">宝箱名称</div>
			<div class="hang"><hk:text name="name" value="${box.name}" maxlength="15"/></div>
			<div class="c4">宝箱数量</div>
			<div class="hang"><hk:text name="totalCount" value="${box.totalCount}" maxlength="8"/></div>
			<div class="c4">开始时间</div>
			<div class="hang"><hk:text name="begint" value="${begint}" maxlength="12"/> </div>
			<div class="c4">结束时间</div>
			<div class="hang"><hk:text name="endt" value="${endt}" maxlength="12"/></div>
			<div class="c4">短信开箱指令</div>
			<div class="hang"><hk:text name="boxKey" maxlength="6" value="${box.boxKey}"/></div>
			<div class="c4">宝箱介绍</div>
			<div class="hang">
				<hk:textarea name="intro" value="${box.intro}"/>
			</div>
			<div class="c4">宝箱类别</div>
			<div class="hang">
				<hk:select name="boxType" checkedvalue="${box.boxType}">
					<hk:option value="0" data=""/>
					<c:forEach var="t" items="${typeList}">
						<hk:option value="${t.typeId}" data="${t.name}"/>
					</c:forEach>
				</hk:select>
			</div>
			<div class="c4">每用户周期内开箱限制</div>
			<div class="hang">
				<hk:text name="precount" size="5" clazz="numbermin" value="${box.precount}"/>个/
				<hk:select name="pretype" checkedvalue="${box.pretype}">
					<hk:option value="0" data="不限"/>
					<c:forEach var="pre" items="${prelist}">
					<hk:option value="${pre.typeId}" data="${pre.name}"/>
					</c:forEach>					
				</hk:select>
			</div>
			<div class="c4">参与方式</div>
			<div class="hang">
				<hk:select name="opentype" checkedvalue="${box.opentype}">
					<c:forEach var="t" items="${opentypelist}">
						<hk:option value="${t.typeId}" data="${t.name}"/>
					</c:forEach>
				</hk:select>
			</div>
			<div class="hang">
				<hk:submit value="保存" clazz="sub"/>
			</div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/admin/adminbox_getbox.do?boxId=${boxId }&t=${t }&page=${repage}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>