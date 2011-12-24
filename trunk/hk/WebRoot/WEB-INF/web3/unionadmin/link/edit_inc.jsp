<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:form oid="editfrm" onsubmit="return sublinkfrm(this.id)" action="/cmpunion/op/link_update.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<hk:hide name="linkId" value="${linkId}"/>
	<table>
		<tr>
			<td width="90px">名称</td>
			<td>
				<div class="f_l">
					<hk:text name="title" clazz="text" value="${o.title}"/>
					<div id="title_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>链接地址</td>
			<td>
				<div class="f_l">
					<hk:text name="url" clazz="text" value="${o.url}"/>
					<div id="url_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
				<hk:submit value="提交" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>