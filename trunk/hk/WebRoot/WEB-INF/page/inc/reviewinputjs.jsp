<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = (140 - document.getElementById("status").value.length)+"字";
		setTimeout(updateCount, 500);
	}
	function confirmCreate() {
		var content=document.getElementById("status").value;
		var len = content.length;
		if(content.indexOf('http://')!=-1 || content.indexOf('https://')!=-1){
			return true;
		}
		if (len >140 ){
			alert("字数超过140个字符");
			return false;
		}
		return true;
	}
	updateCount();
	function submitLaba(event){
		if((event.ctrlKey)&&(event.keyCode==13)){
			if(confirmCreate()){
				document.labafrm.submit();
			}
		}
	}
</script>