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
				setHtml('tipaction'+tipId,'<div class="tip_checked"><input id="tip_done_'+tipId+'" type="checkbox" onclick="changdonestatus(this,'+tipId+')" checked="checked"/><label for="tip_done_'+tipId+'">'+view2_i_done_this+'</label></div>');
				if(getObj('cmpuserstatus_done')!=null){
					getObj('cmpuserstatus_done').checked=true;
					changestatusdonehtml(getObj('cmpuserstatus_done'));
				}				
			}
			else{
				setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'">'+view2_i_done_this+'</label></div><div class="tip_todo_unchecked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')"/><label for="tip_todo'+tipId+'">'+view2_add_as_todo+'</label></div>');
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
					setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'">'+view2_i_done_this2+'</label></div><div class="tip_checked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')" checked="checked"/><label for="tip_todo'+tipId+'">'+view2_add_as_todo+'</label></div>');
				}
				else{
					setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'">'+view2_i_done_this+'</label></div><div class="tip_checked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')" checked="checked"/><label for="tip_todo'+tipId+'">'+view2_add_as_todo+'</label></div>');
				}
				if(getObj('cmpuserstatus_want')!=null){
					getObj('cmpuserstatus_want').checked=true;
					changestatuswanthtml(getObj('cmpuserstatus_want'));
				}
			}
			else{
				setHtml('tipaction'+tipId,'<div class="tip_done_unchecked"><input id="tip_done_'+tipId+'" type="checkbox"  onclick="changdonestatus(this,'+tipId+')"/><label for="tip_done_'+tipId+'">'+view2_i_done_this+'</label></div><div class="tip_todo_unchecked"><input id="tip_todo'+tipId+'" type="checkbox" onclick="changtodostatus(this,'+tipId+')"/><label for="tip_todo'+tipId+'">'+view2_add_as_todo+'</label></div>');
			}
		}
	});
}
function deleteusercmptip(tipId){
	if(confirm('确实要删除？')){
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
	if(confirm('确实要删除？')){
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
	tourl(path+'/h4/op/user/venue_edittip.do?tipId='+tipId);
}