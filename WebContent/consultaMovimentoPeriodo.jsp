
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	  
 <style type="text/css">
 <!--
	 body,td,th {
		font-size: 14px;
	 }
	 .style1 {
		color: #FFCC00;
		font-weight: bold;
	 }
	 .style4 {color: #006600; font-weight: bold; }
	 .style9 {color: #000000}
	 .style10 {color: #FFCC00}
	 .style13 {
		font-size: 12px;
		font-weight: bold;
	 }
	 .style14 {font-size: 10px}
 -->
      </style>   
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objPaga" class="beans.Login" scope="request" />
	  <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	  <jsp:useBean id="objMovFin" class="beans.MovAtual" scope="session" />
	  <div class="hero-unit" align="center">
		 <div id="estrutura">
	        <table width="764" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Movimento por Período</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
		    </table>
	           
	        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
	        	<table width="763" frame="void">
	            <tr>
					<td><div align="left" class="style6">Período:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                
	                   
	                   <input class="input-small" name="data_inicial" type="text" id="data_inicial" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${datainicial}" pattern="dd/MM/yyyy"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'data_final');"/> 
					   a<img src="imagens/spacer_1x1px.png" width="2" border="0" />
					   <input class="input-small" name="data_final" type="text" id="data_final" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${datafinal}" pattern="dd/MM/yyyy"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'bt5');"/>
	                   
	                   <input name="controle" id="controle" type="hidden" value="${controle}" />
	                   <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
	                   <input type="button" onclick="enviar('ConsultaMovimentoPeriodo')" name="bt5" value="Confirma">
	            	</div></td>
	            </tr>
	            </table>
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	        </form>
		</div>  
	</div>
	<c:import url="rodape.jsp"/>

			<script>
        window.onload = function(){document.getElementById("data_inicial").focus();visualizar();}

     	 function buscaDados(pMod){
		    document.formNome.action='stu?p='+pMod;   
  			document.formNome.submit();
		 }

		 function enviar(pModu){
			buscaDados(pModu);
		 }

	     function mascara(e,src,mask){
		     
	  	  	 if(window.event){
	  	  		_TXT = e.keyCode;
	  	  	 }else if(e.which){
	  	  		_TXT = e.which;
	  	  	 }

		  	 var i = src.value.length;
		 	 if(i==10){
		 	  	_TXT = 8;
		 	 }
	  	  	 if(_TXT > 47 && _TXT < 58){	
	  	  		var saida = mask.substring(0,1);
	  	  		var texto = mask.substring(i);
	  	  		if(texto.substring(0,1) != saida){
	  	  			src.value += texto.substring(0,1);
	  	  		}
	  	  		return true;
	  	  	 }else{
	  	  		if(_TXT != 8){
	  	  			return false;
	  	  		}else{
	  	  			return true;
	  	  		}
	  	  	 }
	  	 }
			 
		 function visualizar(){
			var width = 900;
			var height = 500;
			var left = 99;
			var top = 99;
			var now=new Date();
			var seconds = now.getSeconds();
		    if(document.getElementById("controle").value>1){
			   window.open(document.getElementById('urlSet').value+'imprimeMovimentoPeriodo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		    }
		 }
	  </script>
	