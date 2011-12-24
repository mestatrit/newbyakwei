<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><hk:css>
<style type="text/css">
select{padding: 2px;margin: 0px;width:150px}
a{color:#${cssColor.anchorColor};text-decoration:none;}
form{margin:0px;padding: 0px}
table{border-collapse:collapse;margin: 0px;}
table.list{width: 100%}
td{vertical-align:top;padding:0.3em;padding-right: 0px}
img{border:0;margin: 0px;padding: 0px;}
body{font-size:medium; background:#${cssColor.backGroundColor};
color:#${cssColor.fontColor};margin:0px;}
.odd,.odd td{background:#${cssColor.oddBackGroundColor}}
.even,.even td{background:#${cssColor.evenBackGroundColor}}
.reply,.reply td{background:#${cssColor.replyBackGroundColor}}
.reply,.reply.even td{background: #${cssColor.replyEvenBackGroundColor}}
td.h0{width:32px;}
td.h0 img{width:32px;height:32px}
td.h1{width:48px;}
td.h1 img{width:48px;height:48px}
<c:if test="${showMode.modeId==1}">td a.sp{text-decoration: underline;}</c:if>
.menu{color:#${cssColor.menuFontColor};background:#${cssColor.menuBackGroundColor};padding: 2px}
.menu2{background:#${cssColor.evenBackGroundColor};}
.menu a{color:#${cssColor.menuAnchorColor};text-decoration: none}
.warn{padding:4px 3px;padding-left:4px;background-color:#FFFFAD;}
.warn a{text-decoration:none;color:#357BDD;}
.page{padding: 3px;}
.blk{height:25px;padding-right:2px;background: #${cssColor.oddBackGroundColor}}
.ipt{width:99%;margin-left: 0px}
.ipt2{width:99%;height:2.5em;margin:0px;}
.hang{padding: 0.3em;}
.ha{padding:0.3em 0 0 0.3em;}
.ha2{padding:0.3em 0;}
.tml{text-decoration: underline;font-size:small;color:#${cssColor.smallFontColor};}
.nn{color:#${cssColor.fontColor}}
.big{font-size:xx-large;color:#${cssColor.menuBackGroundColor}}
.orange{color:#F76D21;}
.number { -wap-input-format: '*N'; }
.numbermin{-wap-input-format: '*N'; width: 50px}
.letter { -wap-input-format: '*a'; }
.line{text-decoration: underline;}
.bd{border: 1px #000 solid;}
div.clr{clear:both}
div.ff{float: left}
div.fr{float: right;}
.refcon{font-size:small;margin-left: 15px;margin-top: 5px;}
.split-r{margin-right: 5px;}
.split{margin: 5px;float: left;}
a.rowline{
display: block;
height: 100%;
}
small,small a{color:#${cssColor.smallFontColor}}
.ruo{color:#${cssColor.smallFontColor};}
.s{font-size:small;}
</style>
</hk:css>