function reserve_svr(companyId, svrId) {
	tourl(path + "/h4/venue/actor_listforsvr.do?companyId=" + companyId + "&svrId=" + svrId);
}
function reserve_actor(companyId, actorId) {
	tourl(path + "/h4/op/reserve.do?companyId=" + companyId + "&actorId=" + actorId);
}
function reserve_svrfromactor(companyId, svrId,actorId) {
	tourl(path + "/h4/venue/actor_selsvrforactor.do?companyId=" + companyId + "&svrId=" + svrId+"&actorId="+actorId);
}