<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div>
	<hk:form name="labafrm_c" action="/laba/op/op_createnewlaba.do" target="hideframe">
		<div class="f_l text_14">
			<strong><hk:data key="view.laba.createlabatip"/></strong>
		</div>
		<div class="f_r iptnum"><span id="iptnum">140</span>字</div>
		<div class="clr"></div>
		<textarea id="status" name="content" class="text_laba" onkeyup="submitLaba(event)"></textarea>
		<div align="right">
			<div class="f_l"><div class="error" id="labae_rror"></div></div>
			<hk:submit value="提交" clazz="btn split-r f_r"/>
			<div class="clr"></div>
		</div>
	</hk:form>
	<script type="text/javascript">
		function updateCount() {
			document.getElementById("iptnum").innerHTML = (140 - document.getElementById("status").value.length);
			setTimeout(updateCount, 500);
		}
		function confirmCreate() {
			var content=document.getElementById("status").value;
			var len = content.length;
			if(content.indexOf('http://')!=-1 || content.indexOf('https://')!=-1){
				return true;
			}
			if (len >500 ){
				alert("字数超过500个字符");
				return false;
			}
			return true;
		}
		updateCount();
		function submitLaba(event){
			if((event.ctrlKey)&&(event.keyCode==13)){
				if(confirmCreate()){
					document.labafrm_c.submit();
				}
			}
		}
		function createlabaok(error,error_msg,respValue){
			refreshurl();
		}
		function createlabaerror(error,error_msg,respValue){
			setHtml("labae_rror",error_msg);
		}
	</script>
</div>