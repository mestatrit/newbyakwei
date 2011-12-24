<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path=request.getContextPath();%>
<script type="text/javascript">
function changdonestatus(obj,tipId){
	if(!userLogin){
		alertLoginAndReg();
		return;
	}
	var u=null;
	if(obj.checked==false){
		u='/deleteusercmptip?tipId='+tipId;
	}
	else{
		u='/createusercmptipdone?tipId='+tipId;
	}
	$.ajax({
		type:"POST",
		url:u,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(obj.checked){
				setHtml('tipaction'+tipId,'<div class="tip_checked"><input id="tip_done_'+tipId+'" type="checkbox" onclick="changdonestatus(this,'+tipId+')" checked="checked"/><label for="tip_done_'+tipId+'"><hk:data key="view2.i_done_this"/></label></div>');
				if(getObj('cmpuserstatus_done')!=null){
					getObj('cmpuserstatus_done').checked=true;
					changestatusdonehtml(getObj('cmpuserstatus_done'));
				}				
			}
			else{
				setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'"><hk:data key="view2.i_done_this"/></label></div><div class="tip_todo_unchecked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')"/><label for="tip_todo'+tipId+'"><hk:data key="view2.add_as_todo"/></label></div>');
				if(getObj('cmpuserstatus_done')!=null){
					getObj('cmpuserstatus_done').checked=false;
					changestatusdonehtml(getObj('cmpuserstatus_done'));
				}
			}
		}
	});
}
function changtodostatus(obj,tipId){
	if(!userLogin){
		alertLoginAndReg();
		return;
	}
	var doneforfinish=false;
	var u=null;
	if(obj.checked==false){
		u='/deleteusercmptip?tipId='+tipId;
	}
	else{
		u='/createusercmptiptodo?tipId='+tipId;
		doneforfinish=true;
	}
	$.ajax({
		type:"POST",
		url:u,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(obj.checked){
				if(doneforfinish){
					setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'"><hk:data key="view2.i_done_this2"/></label></div><div class="tip_checked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')" checked="checked"/><label for="tip_todo'+tipId+'"><hk:data key="view2.add_as_todo"/></label></div>');
				}
				else{
					setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'"><hk:data key="view2.i_done_this"/></label></div><div class="tip_checked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')" checked="checked"/><label for="tip_todo'+tipId+'"><hk:data key="view2.add_as_todo"/></label></div>');
				}
				if(getObj('cmpuserstatus_want')!=null){
					getObj('cmpuserstatus_want').checked=true;
					changestatuswanthtml(getObj('cmpuserstatus_want'));
				}
			}
			else{
				setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'"><hk:data key="view2.i_done_this"/></label></div><div class="tip_todo_unchecked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')"/><label for="tip_todo'+tipId+'"><hk:data key="view2.add_as_todo"/></label></div>');
				if(getObj('cmpuserstatus_want')!=null){
					getObj('cmpuserstatus_want').checked=false;
					changestatuswanthtml(getObj('cmpuserstatus_want'));
				}
			}
		}
	});
}
function deleteusercmptip(tipId){
	if(confirm('<hk:data key="view2.confirm.delete"/>')){
		showObjGlass('delcmptip'+tipId);
		$.ajax({
			type:"POST",
			url:"/deleteusercmptip?tipId="+tipId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function deletecmptip(tipId){
	if(confirm('<hk:data key="view2.confirm.delete"/>')){
		showObjGlass('delcmptip'+tipId);
		$.ajax({
			type:"POST",
			url:"/deletecmptip?tipId="+tipId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function toedittip(tipId){
	tourl('<%=path%>/h4/op/user/venue_edittip.do?tipId='+tipId);
}
</script>