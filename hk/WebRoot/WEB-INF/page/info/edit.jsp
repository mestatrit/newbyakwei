<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title=" 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/info/op/info_edit.do">
			<hk:hide name="infoId" value="${infoId}"/>
			<hk:data key="information.name"/>(<span class="ruo s"><hk:data key="information.name.tip"/></span>):<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			<hk:data key="information.tag"/>(<span class="ruo s"><hk:data key="information.tag.tip"/></span>):<br/>
			<hk:text name="tag" value="${o.tag}"/><br/>
			<span class="ruo s"><hk:data key="information.tag.tip2"/></span><br/><br/>
			<hk:data key="information.intro"/>(<span class="ruo s"><hk:data key="information.intro.tip"/></span>):<br/>
			<hk:textarea name="intro" value="${o.intro}"/><br/><br/>
			<hk:submit value="info.edit.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>