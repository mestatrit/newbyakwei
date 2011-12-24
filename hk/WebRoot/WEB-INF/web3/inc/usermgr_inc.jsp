<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="body_hk_content" scope="request">
<jsp:include page="userleftnav_inc.jsp"></jsp:include>
<div class="mod_primary">
	<div class="nav-2">
		<div class="subnav">
			<div class="l">
			</div>
			<div class="mid">
				<ul>
					<li class="path">
						<ul>
							<li>
								<a class="home" href="#"></a>
							</li>
							<li>
								<a class="nav-a" href="#">${loginUser.nickName }</a>
							</li>
							<li>
								<a class="nav-a" href="#">${html_title }</a>
							</li>
						</ul>
					</li>
				</ul>
				<div class="clr"></div>
			</div>
			<div class="r"></div>
			<div class="clr"></div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="inner">${mgr_content }</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
<c:if test="${op_func!=null }">
var op_func=${op_func };
</c:if>
<c:if test="${op_func==null }">
var op_func=-1;
</c:if>
var obj=getObj("op_"+op_func);
if(obj!=null){
	obj.className="n1 active";
}
</script>
</c:set>
<jsp:include page="frame.jsp"></jsp:include>