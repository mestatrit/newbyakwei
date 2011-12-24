var tipwrite=false;
var submitfrm=false;
function subvenuefrm(){
	if(submitfrm){
		return false;
	}
	submitfrm=true;
	showGlass("sfrm");
	setHtml('_name','');
	setHtml('_addr','');
	setHtml('_tel','');
	setHtml('_zoneName','');
	if(trim(getObj('_content').value).length>0){
		tipwrite=true;
	}
	getObj('subbtn').disabled=true;
	return true;
}
function createvenueok(error,error_msg,id){
	if(tipwrite){
		tourl('/venue/'+id);
	}
	else{
		tourl('/createtip?companyId='+id+"&doneflg="+doneflg);
	}
}
function createvenueerror(error,error_msg,v){
	submitfrm=false;
	setHtml(getoidparam(error),error_msg);
	hideGlass();
	getObj('subbtn').disabled=false;
}