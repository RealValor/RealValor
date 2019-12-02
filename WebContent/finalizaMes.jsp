
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
	  <div class="hero-unit" align="center">
		   <div id="estrutura">
	        <table width="764" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Finalização de Movimento Mensal</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
		    </table>
	           
	        <form id="formNome" name="formNome" method="post" >
	        	<table width="763" frame="void">
	            <tr>
					<td><div align="left" class="style6">
					   <c:if test="${not empty recibosReserva}">
						 <c:forEach var="recibos" items="${recibosReserva}">
						    <input name="mes_reserva" id="mes_reserva" value="${recibos.mes}" type="hidden">
						    <input name="ano_reserva" id="ano_reserva" value="${recibos.ano}" type="hidden">
						 </c:forEach>
					   </c:if>
	            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Finaliza movimento do mês: <img src="imagens/spacer_1x1px.png" width="5" border="0" />
	                   <input class="input-small" name="mes" id="mes" type="text" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${objMovAtu.mes}" type="number" pattern="00"/>' title="Mes do movmento a ser finalizado" size="1" maxlength="2" readonly />
	                   <input class="input-small" name="ano" id="ano" type="text" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${objMovAtu.ano}" type="number" pattern="0000"/>' title="Ano do movmento a ser finalizado" size="3" maxlength="4" readonly />
	                   <input name="cancelarecibos" id="cancelarecibos" value="0" type="hidden">
	                   <c:if test="${ objUsu.nivel > 2 }">
		            	   <input type="button" onclick="enviar('FinalizaMes')" name="bt5" value="Confirma">
					   </c:if>
	            	</div></td>
	            </tr>
	            <tr>
					<td><div align="left" class="style6">
					   <input name="anoSaldoCaixa" id="anoSaldoCaixa" value="${saldoCaixa.ano}" type="hidden">
		               <c:if test="${empty saldoCaixa}">
		            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo anterior inicial de caixa: <img src="imagens/spacer_1x1px.png" width="5" border="0" />
		                   <input class="input-small" name="saldo_caixa" id="saldo_caixa" type="text" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="0" minFractionDigits="2" type="currency" />' title="Saldo inicial de caixa" size="1" onkeypress="return soNumero(event)" onblur="formataValor(this)"/>
		            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo anterior inicial banco: <img src="imagens/spacer_1x1px.png" width="5" border="0" />
		                   <input class="input-small" name="saldo_banco" id="saldo_banco" type="text" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="0" minFractionDigits="2" type="currency" />' title="Saldo inicial de bancos" size="3" onkeypress="return soNumero(event)" onblur="formataValor(this)"/>
	                   </c:if>
					</div></td>
	            </tr>
	            </table>
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	        </form>
		</div>   
	  </div>
		<script>
		
	  	 function limpaValor(campo){
	  	  		var val=campo.value; 
	  	  		var res="",i;
	  	  		for(i=1;i<=(val.length);i++){
	  	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
	  	  					res = val.substr((val.length-i),1)+""+res;
	  	  				}
	  	  		}
	  	  		return res;
	  	 }
	  	 
	     function formataValor(campo){
 	  		var valor=limpaValor(campo);
 	  		var result="",ponto=0,i;

 	  		for(i=1;i<=(valor.length);i++){
 	  			result = valor.substr((valor.length)-i,1)+""+result;
 	  			if (i>1){
 	  				if (i==2){
 	  					result=","+result;
 	  				}else{
 	  					ponto=ponto+1;
 	  					if(ponto==3&&i<valor.length){
 	  						result="."+result;
 	  						ponto=0;
 	  					}
 	  				}
 	  			}
 	  		}
 	  		campo.value="R$ "+result;
	 	 }

 	  	 function trocaVirgula(campo){
  	  		var val=campo; 
  	  		var res="",i;
  	  		for(i=1;i<=(val.length);i++){
  	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
  	  					res = val.substr((val.length-i),1)+""+res;
  	  				}else{
  	  					if( (val.charCodeAt(val.length-i)==44) ){
  	  						res = "."+res;
  	  					}
  	  				}				
  	  		}
  	  		return res;
  	  	 }

	  	 function enviar(pModu){

			   var reciboano = document.getElementById("ano_reserva").value;
			   var recibomes = document.getElementById("mes_reserva").value;

		  	   if(confirm('ATENÇÃO! Antes de finalizar o movimento é necessário atualizar dívidas e saldo em bancos, caso existam.\n\nConfirma finalizar mês?' )) {

			  	  var anoSaldoCaixa = limpaValor(document.getElementById("anoSaldoCaixa"));

		  		  if(anoSaldoCaixa*1==0){
			  		  var saldoCaixa = limpaValor(document.getElementById("saldo_caixa"));
			  		  var saldoBanco = limpaValor(document.getElementById("saldo_banco"));
			  		  
					  if(saldoCaixa*1==0 || saldoBanco*1==0){
						  if(saldoCaixa*1==0){
								if(!confirm('Tem certeza que o valor inicial do caixa é ZERO?')){
						  			return false;
								}
						  };
						  if(saldoBanco*1==0){
								if(!confirm('Tem certeza que o valor inicial de banco é ZERO?')){
						  			return false;
								}
						  };
					  }
					  document.getElementById("saldo_caixa").value=trocaVirgula(document.getElementById("saldo_caixa").value);
					  document.getElementById("saldo_banco").value=trocaVirgula(document.getElementById("saldo_banco").value);
		  		  }
		  		   
				  if(reciboano*1==document.getElementById("ano").value*1 && recibomes*1==document.getElementById("mes").value*1){
					  if(confirm('Cancela recibo(s) reserva existente(s)?')){
					  		if(!confirm('Confirma cancelameto dos recibos reserva?')){
					  			return false;
							}else{
			 				    document.getElementById("cancelarecibos").value = 1;
							}
			  		  }else{
				  		 	alert('Mês não será finalizado. Antes utilize os recibos reserva');
				  			return false;
			  		  }
				  }

				  document.formNome.action='stu?p='+pModu;   
	  			  document.formNome.submit();
			  }
			  return false;
		  }
	  </script>

	<c:import url="rodape.jsp"/>
