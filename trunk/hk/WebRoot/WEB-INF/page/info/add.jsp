<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="info.add.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
<c:set var="var_t1"><hk:data key="view.add.t" arg0="1" arg1="${hkb_t1}"/></c:set>
<c:set var="var_t3"><hk:data key="view.add.t" arg0="3" arg1="${hkb_t3}"/></c:set>
<c:set var="var_t6"><hk:data key="view.add.t" arg0="6" arg1="${hkb_t6}"/></c:set>
<c:set var="var_t12"><hk:data key="view.add.t" arg0="12" arg1="${hkb_t12}"/></c:set>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/info/op/info_add.do">
			<hk:data key="info.add.userhkb" arg0="${nowhkb}"/><br/><br/>
			<hk:data key="information.name"/>(<span class="ruo s"><hk:data key="information.name.tip"/></span>):<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			<hk:data key="information.tag"/>(<span class="ruo s"><hk:data key="information.tag.tip"/></span>):<br/>
			<hk:text name="tag" value="${o.tag}"/><br/><br/>
			<hk:data key="information.intro"/>(<span class="ruo s"><hk:data key="information.intro.tip"/></span>):<br/>
			
			<hk:textarea name="intro" value="${o.intro}"/><br/><br/>
			
			<hk:data key="info.add.time"/>:<br/>
			
			<hk:select name="t" checkedvalue="${t}">
				<hk:option value="1" data="${var_t1}"/>
				<hk:option value="3" data="${var_t3}"/>
				<hk:option value="6" data="${var_t6}"/>
				<hk:option value="12" data="${var_t12}"/>
			</hk:select><br/>
			<hk:submit value="info.add.submit" res="true"/>
			
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>