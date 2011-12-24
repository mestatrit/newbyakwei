var photo=null;
var map=null;
var begin_idx=0;
var end_idx=5;
var pos_line=2;
var current_pic_idx=0;
var last_visit_idx=0;
var slider_timeid=null;
var mouse_down_flg=0;
var mousedone_timeid=null;
var slider_time=1;
var smphoto_top=0;//顶端位置
var slider_height=14;
var mous_donw_time=10;
var slider_fast=false;
var disable_style=false;
function wheel(obj, fn ,useCapture){
	var mousewheelevt=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel" //FF doesn't recognize mousewheel as of FF3.x  
	if (obj.attachEvent) //if IE (and Opera depending on user setting)  
    	obj.attachEvent("on"+mousewheelevt, handler, useCapture);  
	else if (obj.addEventListener) //WC3 browsers  
    	obj.addEventListener(mousewheelevt, handler, useCapture);

	function handler(event) {
		var delta = 0;	
		var event = window.event || event ;
		var delta =  event.detail ?  -event.detail/3 : event.wheelDelta/120;
		if (event.preventDefault)
					event.preventDefault();
		event.returnValue = false;
		return fn.apply(obj, [event, delta]);
	}	
}
function initimg(){
	
}
function initimglist(_begin_idx,_end_idx){
	pos_line=2+(_begin_idx-begin_idx);
	var current_pic_idx=_begin_idx;
	var last_visit_idx=_begin_idx;
	begin_idx=_begin_idx;
	end_idx=_end_idx;
	var s='';
	if(end_idx>photo.length-1){
		end_idx=photo.length-1;
	}
	for(var i=begin_idx;i<=end_idx;i++){
		s+=buildImgCon(i,false);
	}
	setHtml('smphoto',s);
}
function buildImgCon(idx,hide){
	var s='';
	if(hide){
		if(idx<0){
			s='<div id="smp'+idx+'" class="pboxouter" style="height:0px;" onclick="loadphoto('+idx+')"><input type="hidden" id="smp_dis'+idx+'" value="'+idx+'"/><div id="img'+idx+'" class="pbox"></div></div>';
		}
		else{
			s='<div id="smp'+idx+'" class="pboxouter" style="height:0px;" onclick="loadphoto('+idx+')"><input type="hidden" id="smp_dis'+idx+'" value="'+idx+'"/><div id="img'+idx+'" class="pbox"><img src="'+photo[idx][2]+'" alt="第'+(idx+1)+'张" title="第'+(idx+1)+'张"/></div></div>';
		}
	}
	else{
		if(idx>=photo.length){
			s='<div id="smp'+idx+'" class="pboxouter" onclick="loadphoto('+idx+')"><input type="hidden" id="smp_dis'+idx+'" value="'+idx+'"/><div id="img'+idx+'" class="pbox"></div></div>';
		}
		else{
			s='<div id="smp'+idx+'" class="pboxouter" onclick="loadphoto('+idx+')"><input type="hidden" id="smp_dis'+idx+'" value="'+idx+'"/><div id="img'+idx+'" class="pbox"><img src="'+photo[idx][2]+'" alt="第'+(idx+1)+'张" title="第'+(idx+1)+'张"/></div></div>';
		}
	}
	return s;
}

/**
 * 图片向下滚动
 */
function sliderDown(){
	if(disable_style){
		return;
	}
	if(slider_timeid!=null){
		return;
	}
	if(map[begin_idx]==null){
		return;
	}
	var id="smp"+(begin_idx-1);
	var s=buildImgCon(begin_idx-1,true);
	insertObjBefore(s,'smp'+begin_idx);
	if(slider_fast){
		$('#'+id).height(112);
		if(end_idx!=photo.length-1){
			delObj('smp'+end_idx);
		}
		begin_idx--;
		end_idx--;
		pos_line--;
		slider_timeid=null;
		checkbtn();
	}
	else{
		slider_timeid=setInterval(function(){
			var objh=$('#'+id).height();
			if(objh>=112){
				clearInterval(slider_timeid);
				if(end_idx!=photo.length-1){
					delObj('smp'+end_idx);
				}
				begin_idx--;
				end_idx--;
				pos_line--;
				slider_timeid=null;
				checkbtn();
				return;
			}
			var h=objh+slider_height;
			if(slider_fast){
				h=112;
			}
			$('#'+id).height(h);
		},slider_time);
	}
}

function checkbtn(){
	getObj('id_pre_btn').className='divbtn';
	getObj('id_next_btn').className='divbtn';
	if(end_idx>=photo.length-1){
		getObj('id_next_btn').className='divbtn disabled';
	}
	if(begin_idx<0){
		getObj('id_pre_btn').className='divbtn disabled';
	}
	if(photo.length<=4){
		getObj('id_pre_btn').className='divbtn disabled';
		getObj('id_next_btn').className='divbtn disabled';
	}
}

/**
 * 图片向上滚动
 */
function sliderUp(){
	checkbtn();
	if(disable_style){
		return;
	}
	if(slider_timeid!=null){
		return;
	}
	if(map[end_idx]==null){
		return;
	}
	var id="smp"+(end_idx+1);
	if(end_idx>=photo.length-1){
		return;
	}
	s=buildImgCon(end_idx+1,false);
	insertObjAfter(s,'smp'+end_idx);
	if(slider_fast){
		$('#smp'+begin_idx).height(112);
		delObj('smp'+begin_idx);
		begin_idx++;
		end_idx++;
		pos_line++;
		slider_timeid=null;
		checkbtn();
	}
	else{
		slider_timeid=setInterval(function(){
			var objh=$('#smp'+begin_idx).height();
			if(objh<=0){
				delObj('smp'+begin_idx);
				clearInterval(slider_timeid);
				begin_idx++;
				end_idx++;
				pos_line++;
				slider_timeid=null;
				checkbtn();
				return;
			}
			var h=objh-slider_height;
			$('#smp'+begin_idx).height(h);
		},slider_time);
	}
}
function mouseUp(){
	mouse_down_flg=0;
}
function mouseDownNext(){
	if(photo.length<=4){
		return;
	}
	mouse_down_flg=1;
	mousedone_timeid=setInterval(function(){
		if(mouse_down_flg==0){
			clearInterval(mousedone_timeid);
			mousedone_timeid=null;
			return;
		}
		sliderUp()
	},25);
}
function mouseDownPre(){
	if(photo.length<=4){
		return;
	}
	mouse_down_flg=1;
	mousedone_timeid=setInterval(function(){
		if(mouse_down_flg==0){
			clearInterval(mousedone_timeid);
			mousedone_timeid=null;
			return;
		}
		sliderDown();
	},25);
}

/**
 * 点击图片
 * @param {Object} id
 */
var loadphoto_timeid=null;
function setSelectedPic(){
	if(getObj('img'+last_visit_idx)!=null){
		getObj('img'+last_visit_idx).className='pbox';
	}
	if(getObj('img'+current_pic_idx)!=null){
		getObj('img'+current_pic_idx).className='pbox imgseled';
	}
}
function loadphoto(idx){
	if(loadphoto_timeid!=null){
		return;
	}
	if(idx>photo.length-1){
		return;
	}
	last_visit_idx=current_pic_idx;
	current_pic_idx=idx;
	setSelectedPic();
	setHtml('bigphoto','<img src="'+photo[idx][3]+'"/>');
	var pname=photo[idx][1];
	if(pname.length==0){
		pname="点击编辑名称";
	}
	setHtml('pic_name',pname);
	document.title=photo[idx][1]+" "+companyname+" - 火酷";
	clearphotoinfo();
	loadphotoinfo(idx);
	$('#bigphoto').unbind('click');
	$('#bigphoto').click(function(){
		loadphoto(idx+1);
	});
	if(photo.length<4){
		//return;
	}
	//显示图片 ... ...
	//移动图片
	var pos=parseInt(getObj('smp_dis'+idx).value);
	var dis=pos-pos_line;
	if(dis>0){//向上移动
		loadphoto_timeid=setInterval(function(){
			if(dis!=0){
				if(slider_timeid==null){
					sliderUp();
					dis--;
				}
			}
			else{
				clearInterval(loadphoto_timeid);
				loadphoto_timeid=null;
			}
		},mous_donw_time);
	}
	else if(dis<0){//向下移动
		dis=-dis;
		loadphoto_timeid=setInterval(function(){
			if(dis!=0){
				if(slider_timeid==null){
					sliderDown();
					dis--;
				}
			}
			else{
				clearInterval(loadphoto_timeid);
				loadphoto_timeid=null;
			}
		},mous_donw_time);
	}
}
function loadpic(photoId){
	$.ajax({
		type:"POST",
		url:path+"/h4/venue_loadpic.do?companyId="+companyId,
		cache:false,
    	dataType:"html",
		success:function(data){
			eval(data);
			if(photo.length==0){
				return ;
			}
			photo_idx=getPhotoIdx(photoId);
			if(photo_idx!=-1){
				var b=photo_idx-1;
				var e=photo_idx+4;
				if(e>photo.length-1){
					e=photo.length-1;
					b=e-5;
				}
				if(b<0){
					b=0;
					e=b+5;
				}
				if(photo.length<6){
					b=0;
					e=photo.length;
				}
				initimglist(b ,e);
			}
			else{
				initimglist(begin_idx ,end_idx);
			}
			if(photo.length<=4){
				disable_style=true;
				$('#smphoto').css("top","0");
				getObj('id_pre_btn').className='divbtn disabled';
				getObj('id_next_btn').className='divbtn disabled';
			}
			loadphoto(begin_idx);
		}
	});
}
function getPhotoIdx(photoId){
	for(var i=0;i<photo.length;i++){
		if(photo[i][0]==photoId){
			return i;
		}
	}
	return -1;
}
function debuginfo(v){
	document.getElementById('debugdiv').innerHTML=document.getElementById('debugdiv').innerHTML+v+"<br/>";
}
function votecurrentpic(){
	if(getObj('vote')!=null){
		if(getObj('vote').checked==true){
			votepic(map[current_pic_idx]);
		}
		else{
			delvotepic(map[current_pic_idx]);
		}
	}
}
function delvotepic(id){
	showObjGlass('votecon');
	$.ajax({
		type:"POST",
		url:path+"/h4/op/user/venue_delphotovote.do?photoId="+id,
		cache:false,
		dataType:"html",
		success:function(data){
		}
	});
}
function votepic(id){
	showObjGlass('votecon');
	$.ajax({
		type:"POST",
		url:path+"/h4/op/user/venue_votepic.do?photoId="+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			
		}
	});
}
function loadphotoinfo(idx){
	var id=map[idx];
	$.ajax({
		type:"POST",
		url:path+"/h4/venue_loadphotocmt.do?photoId="+id,
		cache:false,
		dataType:"html",
		success:function(data){
			setHtml('cmtdata',data);
			if(getObj('show_pci_cmt').value=="1"){
				$('#user_pic_cmt_mod').css('display','');
			}
			var stat=parseInt(getObj('votestat').value);
			if(getObj('vote')!=null){
				if(stat==1){
					getObj('vote').checked=true;
				}
				else{
					getObj('vote').checked=false;
				}
			}
			if(cmpedit){
				if(photo[idx][4]==cmpheadpath){
					$('#id_setheadpath').css('display','none');
				}
				else{
					$('#id_setheadpath').css('display','inline');
				}
			}
			var caneditpic=getObj('caneditpic').value;
			if(caneditpic=='true'){
				getObj('id_delpic').href="javascript:delpic()";
				setHtml('id_delpic','删除图片');
				$('#pic_name').unbind('mouseover');
				$('#pic_name').mouseover(function(){
					this.className='b bg2';
				});
				$('#pic_name').unbind('mouseout');
				$('#pic_name').mouseout(function(){
					this.className='b';
				});
				getObj('pic_name').title="点击编辑名称";
				$('#pic_name').unbind('click');
				$('#pic_name').click(function(){
					var html='<form id="editname_frm" method="post" onsubmit="return subeditname(this.id)" action="'+path+'/h4/op/user/venue_editpicname.do" target="hideframe">';
					html+='<input type="hidden" name="photoId" value="'+photo[idx][0]+'">';
					html+='<input id="id_name" name="name" value="" class="text"/> ';
					html+='<input type="submit" value="提交" class="btn"/> ';
					html+='<a style="font-size:14px;font-weight:normal;" href="javascript:canceleditname()">取消</a>';
					html+='</form>';
					setHtml('pic_name',html);
					getObj('id_name').value=toText(photo[idx][1]);
					getObj('id_name').focus();
					$('#pic_name').unbind('click');
				});
			}
			else{
				getObj('id_delpic').href="javascript:void(0)";
				setHtml('id_delpic','');
				$('#pic_name').unbind('mouseover');
				$('#pic_name').unbind('onmouseout');
				$('#pic_name').unbind('click');
				getObj('pic_name').title="";
			}
		}
	});
}
function canceleditname(){
	var pname=photo[current_pic_idx][1];
	if(pname.length==0){
		pname="点击编辑名称";
	}
	setHtml('pic_name',toHtml(pname));
	$('#pic_name').click(function(){
		var html='<form id="editname_frm" method="post" onsubmit="return subeditname(this.id)" action="'+path+'/h4/op/user/venue_editpicname.do" target="hideframe">';
		html+='<input type="hidden" name="photoId" value="'+photo[current_pic_idx][0]+'">';
		html+='<input id="id_name" name="name" value="" class="text"/> ';
		html+='<input type="submit" value="提交" class="btn"/> ';
		html+='<a style="font-size:14px;font-weight:normal;" href="javascript:canceleditname()">取消</a>';
		html+='</form>';
		setHtml('pic_name',html);
		getObj('id_name').value=toText(photo[current_pic_idx][1]);
		getObj('id_name').focus();
		$('#pic_name').unbind('click');
	});
}
function subeditname(frmid){
	showGlass(frmid);
	return true;
}
function oneditnameok(error,msg,v){
	hideGlass();
	photo[current_pic_idx][1]=v;
	canceleditname();
}
function oneditnameerr(){
	hideGlass();
	alert('不能保存，系统出错，请稍后再试');
}
function clearphotoinfo(){
	if(getObj('cmtdata')!=null){
		setHtml('cmtdata','');
	}
}
function delcmt(id){
	if(window.confirm('确实要删除？')){
		showGlass('cmt'+id);
		$.ajax({
			type:"POST",
			url:path+"/h4/op/user/venue_delphotocmt.do?cmtId="+id,
			cache:false,
			dataType:"html",
			success:function(data){
			hideGlass();
			delObj('cmt'+id);
		}
		});
	}
}
function delpic(){
	if(window.confirm("确实要删除？")){
		showObjGlass('id_delpic');
		$.ajax({
			type:"POST",
			url:path+"/h4/op/user/venue_delpic.do?photoId="+map[current_pic_idx],
			cache:false,
	    	dataType:"html",
			success:function(data){
				delfromphoto();
				hideGlass('id_delpic');
			}
		});
	}
}
function delfromphoto(){
	photo=photo.del(current_pic_idx);
	slider_fast=true;
	map=new Array();
	for(var i=0;i<photo.length;i++){
		map[i]=photo[i][0];
	}
	var tmp_idx=current_pic_idx;
	if(tmp_idx==0){
		tmp_idx++;
	}
	var nbegin=tmp_idx-1;
	var nend=tmp_idx-1+5;
	if(nend>photo.length-1){
		nend=photo.length-1;
		nbegin=nend-5;
	}
	if(nbegin<0){
		nbegin=0;
	}
	initimglist(nbegin,nend);
	loadphoto(tmp_idx-1);
	setTimeout(function(){
		slider_fast=false;
	}, 500);
}
var checktimeid=null;
function checkPhotoLength(){
	if(checktimeid==null){
		checktimeid=setInterval(function(){
			if(photo!=null && photo.length<=4){
				disable_style=true;
				initimglist(0,photo.length-1);
				$('#smphoto').css("top","0");
				clearInterval(checktimeid);
			}
		}, 1000);
	}
}
function setheadpath(){
	showObjGlass('id_setheadpath');
	$.ajax({
		type:"POST",
		url:path+"/h4/op/user/venue_sethead.do?photoId="+map[current_pic_idx]+"&companyId="+companyId,
		cache:false,
    	dataType:"html",
		success:function(data){
			cmpheadpath=photo[current_pic_idx][4];
			$('#id_setheadpath').css('display','none');
			hideGlass();
		}
	});
}