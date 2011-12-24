<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<div class="nav-2">
	<br class="linefix" />
	<div class="subnav short">
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
							<c:if test="${user!=null}"><a class="nav-a" href="<%=path %>/home_web.do?userId=${userId }">${user.nickName }</a></c:if>
							<c:if test="${user==null}"><a class="nav-a" href="<%=path %>/home_web.do?userId=${loginUser.userId }">${loginUser.nickName }</a></c:if>
						</li>
						<li>
							${nav_2_short_content }
						</li>
					</ul>
				</li>
			</ul>
			<div class="clr">
			</div>
		</div>
		<div class="r">
		</div>
		<div class="clr">
		</div>
	</div>
	<div class="clr">
	</div>
</div>