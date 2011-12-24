<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="添加项目 - ${o.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">添加项目</div>
	<div class="hang even">
		<hk:form action="/e/op/tml/op_addcmpmodule.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="templateId" value="${templateId}"/>
			项目名称:<br/>
			<hk:text name="title" value="${cmpModule.title}"/><br/><br/>
			项目描述:<br/>
			<hk:textarea name="intro" value="${cmpModule.intro}"/><br/>
			挂接的模块:<br/>
			<hk:select name="moduleId" checkedvalue="${cmpModule.moduleId}">
				<c:forEach var="m" items="${modules}">
					<hk:option value="${m.moduleId}" data="${m.name}"/>
				</c:forEach><br/>
			</hk:select><br/>
			<hk:radioarea name="showflg" checkedvalue="${cmpModule.showflg}" forcecheckedvalue="0">
				<hk:radio value="0" data="显示"/> 
				<hk:radio value="1" data="隐藏"/>
			</hk:radioarea><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/op/tml/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>