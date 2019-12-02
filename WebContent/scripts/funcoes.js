
 function soNumero(e) {
   var keynum;
   var keychar;
   var numcheck;
			
   if(window.event){
       keynum = e.keyCode;
   }else if(e.which){
      keynum = e.which;
   }
   keychar = String.fromCharCode(keynum);
   numcheck = /[^A-Za-z.Á«=']/;
   return numcheck.test(keychar);
 }			

 function JumpField(pCampo,pDestino) {
   if (pCampo.value.length == pCampo.maxLength) {
      document.getElementById(pDestino).focus();
   }
 }
 
 navHover = function() {
    var lis = document.getElementById("navmenu").getElementsByTagName("li");
	for (var i=0; i<lis.length; i++) {
	   lis[i].onmouseover=function() {
	      this.className+=" iehover";
	   }
	   lis[i].onmouseout=function() {
	      this.className=this.className.replace(new RegExp(" iehover\\b"),"");
	   }
	}
 }
