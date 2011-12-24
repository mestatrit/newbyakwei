<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function updateCount2() {
		document.getElementById("remaining2").innerHTML = (140 - document.getElementById("status2").value.length)+"字";
		setTimeout(updateCount2, 500);
	}
	function confirmCreate2() {
		var len = document.getElementById("status2").value.length;
		if (len >140 ){
			alert("字数超过140个字符");
			return false;
		}
		return true;
	}
	updateCount2();
	function submitLaba2(event){
		if((event.ctrlKey)&&(event.keyCode==13)){
			if(confirmCreate2()){
				document.labafrm2.submit();
			}
		}
	}
</script>