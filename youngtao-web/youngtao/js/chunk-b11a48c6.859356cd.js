(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-b11a48c6"],{1679:function(t,c,e){},"283f":function(t,c,e){"use strict";(function(t){var a=e("d4ec"),n=e("bee2"),s=e("262e"),r=e("2caf"),i=e("9ab4"),u=e("60a3"),o=e("637a"),l=e("4b25"),d=function(c){Object(s["a"])(i,c);var e=Object(r["a"])(i);function i(){var t;return Object(a["a"])(this,i),t=e.apply(this,arguments),t.active=0,t.menus=[],t.productList=[],t}return Object(n["a"])(i,[{key:"changeMenu",value:function(c){this.active=c,this.getProduct(c,1,10),t(".hoursMenu li").each((function(t,e){t!==c?e.classList.remove("active"):e.classList.add("active")}))}},{key:"getTimeMenu",value:function(){var t=this;Object(l["b"])().then((function(c){t.menus=c.data,t.getProduct(0,1,10),t.$log.info("获取菜单",c.data)}))}},{key:"getProduct",value:function(t,c,e){var a=this;Object(l["a"])(t,c,e).then((function(t){a.productList=t.data,a.$log.info("秒杀商品",t.data)}))}},{key:"mounted",value:function(){this.getTimeMenu()}}]),i}(u["e"]);d=Object(i["a"])([Object(u["a"])({components:{ProductCard:o["a"]}})],d),c["a"]=d}).call(this,e("1157"))},"497c":function(t,c,e){"use strict";e.r(c);var a=function(){var t=this,c=t.$createElement,e=t._self._c||c;return e("div",{staticClass:"container product-wrap"},[0!=t.menus.length?e("ul",{staticClass:"hoursMenu shadow-sm clearfix"},t._l(5,(function(c,a){return e("li",{key:a,class:{active:0==a},on:{click:function(c){return t.changeMenu(a)}}},[e("div",{staticClass:"clearfix"},[e("el-col",{staticClass:"menu-time",attrs:{span:10}},[t._v(" "+t._s(t.menus[a].substring(8))+" : 00 ")]),t.active==a?e("el-col",{staticClass:"menu-show",attrs:{span:14}},[e("div",0==a?[e("div",[t._v("正在秒杀")]),e("div",[t._v("距离结束：xxx")])]:[e("div",[t._v("即将开始")]),e("div",[t._v("距离开始：xxx")])])]):e("el-col",{attrs:{span:14}},[e("div",0==a?[e("span",{staticClass:"menu-hint"},[t._v("进行中")])]:[e("span",{staticClass:"menu-hint"},[t._v("即将开始")])])])],1)])})),0):t._e(),e("el-row",{attrs:{gutter:20}},t._l(t.productList,(function(c){return e("el-col",{key:c.spuId,attrs:{md:6}},[e("product-card",{attrs:{product:c,time:t.menus[t.active]}})],1)})),1)],1)},n=[],s=e("283f"),r=s["a"],i=(e("af0d"),e("2877")),u=Object(i["a"])(r,a,n,!1,null,"bf5c1336",null);c["default"]=u.exports},"4b25":function(t,c,e){"use strict";e.d(c,"b",(function(){return n})),e.d(c,"a",(function(){return s})),e.d(c,"c",(function(){return r}));e("99af");var a=e("6b02");function n(){return Object(a["a"])({url:"gsc/product/timeMenu",method:"get"})}function s(t,c,e){return Object(a["a"])({url:"gsc/product/listByTime/".concat(t,"/").concat(c,"/").concat(e),method:"get"})}function r(t){return Object(a["a"])({url:"gsc/product/confirmOrder",method:"post",data:JSON.stringify(t)})}},"637a":function(t,c,e){"use strict";var a=function(){var t=this,c=t.$createElement,e=t._self._c||c;return e("div",{staticClass:"product-card shadow-sm",on:{click:function(c){return t.productDetail(t.product.spuId,t.product.skuId)}}},[e("div",{staticClass:"product-img"},[e("img",{attrs:{src:t.product.image,alt:""}})]),e("div",{staticClass:"product-title"},[e("el-tag",{attrs:{type:"danger",size:"mini"}},[t._v("活动中")]),t._v(" "+t._s(t.product.title)+" ")],1),e("el-row",[e("el-col",{attrs:{span:15}},[e("div",{staticClass:"product-price-box"},[e("span",{staticClass:"product-price"},[t._v("￥"+t._s(t.product.price))]),e("span",[e("s",[t._v(t._s(t.product.oldPrice))])])])]),e("el-col",{attrs:{span:9}},[e("el-button",{staticClass:"float-right",attrs:{size:"small",type:"primary"},on:{click:function(c){return t.productDetail(t.product.spuId,t.product.skuId)}}},[t._v(" 立即抢购 ")])],1)],1),e("div",{staticClass:"mt-2"},[e("el-progress",{attrs:{percentage:10,"stroke-width":5,format:function(t){return"售"+t+"%"}}})],1)],1)},n=[],s=(e("99af"),e("d4ec")),r=e("bee2"),i=e("262e"),u=e("2caf"),o=e("9ab4"),l=e("60a3"),d=function(t){Object(i["a"])(e,t);var c=Object(u["a"])(e);function e(){return Object(s["a"])(this,e),c.apply(this,arguments)}return Object(r["a"])(e,[{key:"productDetail",value:function(t,c){this.$router.push("/market/detail/".concat(t,"?skuId=").concat(c))}}]),e}(l["e"]);Object(o["a"])([Object(l["c"])()],d.prototype,"product",void 0),Object(o["a"])([Object(l["c"])()],d.prototype,"time",void 0),d=Object(o["a"])([l["a"]],d);var p=d,f=p,v=(e("b384"),e("2877")),b=Object(v["a"])(f,a,n,!1,null,"fa12f348",null);c["a"]=b.exports},"9f88":function(t,c,e){},af0d:function(t,c,e){"use strict";var a=e("9f88"),n=e.n(a);n.a},b384:function(t,c,e){"use strict";var a=e("1679"),n=e.n(a);n.a}}]);
//# sourceMappingURL=chunk-b11a48c6.859356cd.js.map