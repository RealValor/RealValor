
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objSal" class="beans.SaldoBanco" scope="request" />
	  <div class="hero-unit" align="center">
		 <div id="estrutura">
	      <table width="763" frame="void">
			 <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Inclusão de Saldo Mensal em Bancos</div></td></tr>     
			 <tr><td colspan="17">&nbsp;</td></tr>	  
		  </table>
	        
	        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
	        	<table width="763" frame="void">
	            <tr>
	         	    <td><div align="left" class="style6">Banco:<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
					<select name="listabanco" id="listabanco" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("banco").value=listabanco.value ' >
			           <c:if test="${objSal.banco.codigo != 0}">
			              <option value="${objSal.banco.codigo}">${objSal.banco.descricao}</option>
			           </c:if>
	                   <c:forEach var="bancos" items="${listBan}">
			              <option value="${bancos.codigo}">${bancos.descricao}</option>
	        		   </c:forEach> 
	                </select>
	                <input name="banco" id="banco" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSal.banco.codigo}" maxlength="1" />                  
	                <img src="imagens/spacer_1x1px.png" width="3" border="0" />Ano:<img src="imagens/spacer_1x1px.png" width="2" border="0" />                
	                <input name="ano" type="text" id="ano" class="input-small" size="4" maxlength="4" title="Ano do movimento" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${ objSal.ano }" type="number" pattern="####"/>' readonly />
	            	<img src="imagens/spacer_1x1px.png" width="2" border="0" />Mes:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	            	<input name="mes" type="text" id="mes" class="input-mini" size="2" maxlength="2" title="Mes do movimento" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${ objSal.mes }" type="number" pattern="00"/>' readonly />
	                <img src="imagens/spacer_1x1px.png" width="2" border="0" />Valor:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input name="saldo" type="text" id="saldo" class="input-small" size="10" maxlength="20" style="font-size:16px; color:#006633; font-weight:bold" value='<f:formatNumber value="${objSal.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event);" onblur="formataValor(this)" title="Saldo em conta no último dia do mes ${objSal.mes}" />			  	   
	            	</div></td>
	            </tr>
	            </table>
	            <table width="762" border="1">
			    	<tr bordercolor="#cccccc" bgcolor="#cccccc">
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Banco</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Ano</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Mes</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Saldo</strong></div></td>
			   	    </tr>
		   	      <c:set var="valortotal" value="0"/>
		          <c:forEach var="saldo" items="${listSal}">
		               <c:if test="${saldo.banco.descricao!=''}"> 
					     	<tr bgcolor="#ffffff">
			                    <td><div align="center"><a href="stu?p=ConsultaSaldoBanco&banco=${saldo.banco.codigo}&ano=${saldo.ano}&mes=${saldo.mes}"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
								<td><div align="left" style="font-size: 12px; color: #000000;">${saldo.banco.descricao}</div><img src="imagens/spacer_1x1px.png" width="300" border="0" /></td>
								<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatNumber value="${saldo.ano}" type="number" pattern="####"/></div></td>
								<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatNumber value="${saldo.mes}" type="number" pattern="00"/></div></td>
								<td><div align="right" style="font-size: 12px; color: #000000;"><f:formatNumber value="${saldo.valor}" type="currency"/><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
								<c:set var="valortotal" value="${valortotal + saldo.valor}"/>
				            </tr>
					   </c:if>
		          </c:forEach>
		          <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
				     <td bordercolor="#ffffff" colspan="3"><div align="right" style="font-size: 12px; color: #000000;">
				     	<img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo total em bancos no mês ${objSal.mes}/${objSal.ano}<img src="imagens/spacer_1x1px.png" width="10" border="0" /></div></td>
						<td><div align="right" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${valortotal}" type="currency"/><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
			      </tr>	       		   
		   	  </table>
	            
	        </form>
			<form name="form" id="form" method="post">
			  <table width="763" frame="void">
			  	<tr><td><input name="cod_pg" id="cod_pg" type="hidden" value="" /><input name="tipo_entr" id="tipo_entr" type="hidden" value="1" /></td></tr>                        
	         
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
	                    		<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('IncluiSaldoBanco')" name="bt1" value="Incluir">
						        	<input type="button" onclick="enviar('AlteraSaldoBanco')" name="bt2" value="alterar">     
						        	<input type="button" onclick="enviar('ExcluiSaldoBanco')" name="bt4" value="excluir">
								</c:if>
					        	<input type="button" onclick="enviar('ListaSaldoBanco')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar"> 
							</div>   
	                    </td>   
					</tr>
			  </table>
	   		</form>
	</div>     
	  </div>
	<c:import url="rodape.jsp"/>
		<script>
  	    window.onload = function(){document.formNome.ano.focus();}
  	      
  	      function mascara(e,src,mask){
   	  	     if(window.event){
   	  		    _TXT = e.keyCode;
   	  	     }else if(e.which){
   	  		    _TXT = e.which;
   	  	     }
   	  	     if(_TXT > 47 && _TXT < 58){	
   	  		    var i = src.value.length;
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
     	 function preencheCampo(pCampo,pDesc){
     	    if(pCampo.value == ""){
     	       alert('Necessário Campo '+pDesc);
     		   pCampo.focus();
     		   return false;
     		}
     		return true;   
     	 }
     	 function buscaDados(pMod){
		    document.formNome.action='stu?p='+pMod;   
  			document.formNome.submit();
		 }
		 function enviar(pModu){
			if(pModu=='IncluiSaldoBanco'){
				if(document.getElementById("banco").value*1==0||document.getElementById("ano").value*1==0||document.getElementById("mes").value*1==0||document.getElementById("saldo").value*1==0){
					alert("Necessário o preenchimento dos campos");
					return false;
				}							
			}
			document.getElementById("saldo").value=trocaVirgula(document.getElementById("saldo").value);
    		buscaDados(pModu);
		 }
		 
		 function limpar(){
		    document.formNome.ano.value='';
		    document.formNome.mes.value='';	
		    document.formNome.saldo.value='';
		    document.formNome.banco.focus();	
		 }				
	  </script>
	