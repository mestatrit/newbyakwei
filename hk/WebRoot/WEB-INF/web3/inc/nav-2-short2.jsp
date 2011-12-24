<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="nav-2">
	<br class="linefix" />
	<div class="subnav short">
		<div class="l"></div>
		<div class="mid">
			<ul>
				<li class="path">
					<ul>
						<li>
							<a class="home" href="#"></a>
						</li>
						<c:if test="${nav_2_short_content!=null}">${nav_2_short_content }</c:if>
					</ul>
				</li>
			</ul>
			<div class="clr">
			</div>
		</div>
		<div class="r"></div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</div>