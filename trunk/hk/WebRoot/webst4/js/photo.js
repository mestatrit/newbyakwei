var pic=new Array();
var page=1;
var size=20;
var size_distance=10;
var photo_loading=false;
var load_init=true;

var j=0;
var end=0;
var timehandle=null;
var remain=0;
var begin_photo=0;
var end_photo=2;
var mouse_down_flg=0;
var timehandle_mousedown=null;
var current_timeevent=null;
var current_pic_id=-1;
var current_pic_idx=0;
var last_visit_idx=0;
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

function mouseUp(){
	mouse_down_flg=0;
}
function mouseDownNext(){
	mouse_down_flg=1;
	listenMouseDownNext();
}
function listenMouseDownNext(){
	if(mouse_down_flg==0){
		clearInterval(timehandle_mousedown);
		timehandle_mousedown=null;
		return;
	}
	tonext();
	runMouseDownNext();
}
function runMouseDownNext(){
	if (timehandle_mousedown == null) {
		timehandle_mousedown=setInterval("listenMouseDownNext()",100);
		current_timeevent=1;
	}
}
function mouseDownPre(){
	mouse_down_flg=1;
	listenMouseDownPre();
}
function listenMouseDownPre(){
	if(mouse_down_flg==0){
		clearInterval(timehandle_mousedown);
		timehandle_mousedown=null;
		return;
	}
	topre();
	runMouseDownPre();
}
function runMouseDownPre(){
	if(timehandle_mousedown==null){
		timehandle_mousedown=setInterval("listenMouseDownPre()",100);
		current_timeevent=-1;
	}
}
function debuginfo(v){
	document.getElementById('debugdiv').innerHTML=document.getElementById('debugdiv').innerHTML+v+"<br/>";
}
function topre(){
	if(timehandle!=null){
		return;
	}
	if(document.getElementById('smp'+(begin_photo-1))==null){
		end_photo=2;
		return;
	}
	processlocation(begin_photo,-1);
}
function tonext(){
	if(timehandle!=null){
		return;
	}
	processlocation(end_photo,1);
}
function loadbigphoto(i){
	if(i>pic.length-1){
		return;
	}
	if(pic.length>=size && pic.length-1-i<=size_distance){
		if(!photo_loading){
			loadpic(false);
		}
	}
	setHtml('bigphoto','<img src="'+pic[i][3]+'"/>');
	last_visit_idx=current_pic_idx;
	current_pic_idx=i;
	setSelectedPic();
	current_pic_id=pic[i][0];
	$('#bigphoto').unbind('click');
	$('#bigphoto').click(function(){
		loadbigphoto(i+1);
	});
	processlocation(i,0);
}
function setSelectedPic(){
	if(getObj('img'+last_visit_idx)!=null){
		getObj('img'+last_visit_idx).className='pbox';
	}
	getObj('img'+current_pic_idx).className='pbox imgseled';
}
function processlocation(i,direction){
	if(i!=1){
		if(i!=0){
			if(document.getElementById('smp'+(i+2))!=null){
				end_photo=i+1;
				begin_photo=end_photo-2;
			}
			if (document.getElementById('smp' + begin_photo) == null) {
				begin_photo=0;
			}
		}
	}
	var n=$('#smp'+i).offset().top;
	var n1=$('#smphoto').offset().top;
	var res=n-n1;
	if(res>=100){
		end=parseInt((res-112)/14);
		remain=res%14;
		photomovedown();
	}
	else if(document.getElementById('smphoto').scrollTop>0){
		end=8;
		remain=res%14;
		if(remain<0){
			remain=-remain;
		}
		photomoveup();
	}
}
function photomovedown(){
	j=0;
	timehandle=setInterval("wheelphotodown()",5);
}
function photomoveup(){
	j=0;
	timehandle=setInterval("wheelphotoup()",5);
}
function wheelphotodown(){
	if(j>=end){
		clearInterval(timehandle);
		timehandle=null;
		if(remain>0){
			document.getElementById('smphoto').scrollTop=document.getElementById('smphoto').scrollTop+remain;
		}
		return;
	}
	document.getElementById('smphoto').scrollTop=document.getElementById('smphoto').scrollTop+14;
	j++;
}
function wheelphotoup(){
	if(j>=end){
		clearInterval(timehandle);
		timehandle=null;
		if(remain>0){
			document.getElementById('smphoto').scrollTop=document.getElementById('smphoto').scrollTop+remain;
		}
		return;
	}
	document.getElementById('smphoto').scrollTop=document.getElementById('smphoto').scrollTop-14;
	j++;
}
function loadpic(loadfirst){
	photo_loading=true;
	$.ajax({
		type:"POST",
		url:path+"/h4/venue_loadpic.do?companyId="+companyId+"&page="+page,
		cache:false,
    	dataType:"html",
		success:function(data){
			eval(data);
			if(photos.length==0){
				return ;
			}
			appendpic(photos,loadfirst);
			page++;
			load_init=false;
			photo_loading=false;
		}
	});
}
function appendpic(photos,loadfirst){
	var begin_idx=pic.length;
	for(var i=0;i<photos.length;i++){
		pic[pic.length]=new Array(photos[i][0],photos[i][1],photos[i][2],photos[i][3],photos[i][4]);
	}
	appendpictohtml(begin_idx,loadfirst);
}
function appendpictohtml(begin,loadfirst){
	var s="";
	for(var i=begin;i<pic.length;i++){
		s+='<div id="smp'+i+'" class="pboxouter">';
		s+='<div id="img'+i+'" class="pbox" onclick="loadbigphoto('+i+')">';
		s+='<img src="'+pic[i][2]+'"/>';
		s+='</div></div>';
	}
	insertObjBefore(s,'begin');
	if(loadfirst){
		if(pic.length>0){
			loadbigphoto(0);
		}
	}
}
function votecurrentpic(){
	votepic(current_pic_id);
}
function votepic(id){
	showObjGlass('votecon');
	$.ajax({
		type:"POST",
		url:path+"/h4/op/user/venue_votepic.do?photoId="+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('vote_pic_num',data);
		}
	});
}
