
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
		<script>
  	    window.onload = function(){document.formNome.numero_recibo.focus();}
  	 	if (window.attachEvent) window.attachEvent("onload", navHover);
 
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

			if(pModu!='EmiteRecibo'){
	    		buscaDados(pModu);
			}
				    
			if(pModu=='EmiteRecibo'){
				var width = 840;
				var height = 620;

				var left = 99;
				var top = 99;
				var now=new Date();
				var seconds = now.getSeconds();

				if(document.getElementById("cancelado").value=='N'){
					
				   window.open(document.getElementById('urlSet').value+'imprimeRecibo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
				}else{
					
				   window.open(document.getElementById('urlSet').value+'imprimeRecCancelado.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
				}
			}
		 }
		 
		 function limpar(){
		 	document.formNome.numero_recibo.value = '';
		 	document.formNome.numero_recibo.focus();
		 	document.getElementById("ano_paga").value='0';
		 	enviar('ConsultaReciboNumero');
		 }
				 
	  </script>
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
	  <jsp:useBean id="objPaga" class="beans.Login" scope="session" /> <!-- request -->
  <div class="hero-unit" align="center">
	  <div id="estrutura">
        <table width="764" frame="void">
		   <tr><td colspan="17">&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Recibo por Número</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
		<form id="formNome" name="formNome" method="post">
        	<table width="763" frame="void">
            <tr>
            	<td><div align="left"  class="style6" >Nº. Recibo:<img src="imagens/spacer_1x1px.png" width="2" border="0" />
            	 <input name="cancelado" id="cancelado" type="hidden" value="${excluido}"/>
            	 <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
				 <input class="input-mini" name="numero_recibo" type="text" id="numero_recibo" style="font-size: 16px; color: #006633; font-weight: bold;" size="4" maxlength="6" title="Numero do Recibo" value='<f:formatNumber value="" type="number" pattern="######"/>' onkeypress="return soNumero(event);" />			      
		         <img src="imagens/spacer_1x1px.png" width="1" border="0" />/<img src="imagens/spacer_1x1px.png" width="1" border="0" />            
	              <select class="input-small" name="ano" id="ano" title="Ano do Recibo" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value'>
                      <option value="${objMovIni.ano}">${objMovIni.ano}</option>
                      <option value="${objMovIni.ano - 1}">${objMovIni.ano - 1}</option>
                      <option value="${objMovIni.ano - 2}">${objMovIni.ano - 2}</option>
                      <option value="${objMovIni.ano - 3}">${objMovIni.ano - 3}</option>
    	          </select>
                   <input type="button" onclick="enviar('ConsultaReciboNumero')" name="bt5" value="Consultar"/>
                   <input name="ano_paga" id="ano_paga" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.ano}" maxlength="1" readonly/>
           	  </div></td>
            </tr>
            </table>
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="763" class="table table-condensed">
			   <c:if test="${not empty listaRec}">
			   	  
			   	  <c:if test="${excluido == 'S'}">
					  <div align="left" style="font-size: 16px; color: #333333;">Recibo <f:formatNumber value="${numeroRecibo}" pattern="0000" />/<f:formatNumber pattern="0000" value="${anoRecibo}" /> (Cancelado)</div> 
				  </c:if>
				  <c:if test="${excluido == 'N'}">
					  <div align="left" style="font-size: 16px; color: #333333;">Recibo: <f:formatNumber value="${numeroRecibo}" pattern="0000" />/<f:formatNumber pattern="0000" value="${anoRecibo}" /></div> 
				  </c:if>
			   	   
           	      <tr bordercolor="#cccccc" bgcolor="#cccccc">
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>entrada</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>codigo</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>ano</strong></div></td> 
		      	      
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jan</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>fev</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>mar</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>abr</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>mai</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jun</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jul</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>ago</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>set</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>out</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>nov</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>dez</strong></div></td> 
		      	      
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>v.unit.</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>total</strong></div></td> 
                  </tr>          
		       </c:if>
		       <c:set var="vlrtotalgeral" value="0"/>
		       <c:forEach var="linhasrecibo" items="${listaRec}">
           	      <tr bgcolor="#ffffff">
               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.entrada.descricao}</div></td> 
               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.socioDevedor.codigo}" pattern="0000"/></div></td>
               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.ano}</div></td> 
					<c:set var="vlrtotal" value="0"/>
					<c:forEach var="i" begin="0" end="11">
					   <c:if test="${linhasrecibo.meses[i] == 0}">
               		      <td><div align="center" style="font-size: 12px; color: #999999;">-</div></td>
					   </c:if>
		     	       <c:if test="${linhasrecibo.meses[i] != 0}">
               		      <td bgcolor="#009900" ><div align="center" class="style5 style9 style13 style14">${linhasrecibo.meses[i]}</div></td>
               		      <c:set var="vlrtotal" value="${vlrtotal + 1}"/>
		     	       </c:if>
					</c:forEach>					
               	    <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.valor}" minFractionDigits="2" /></div></td> 
               		<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotal*linhasrecibo.valor}" minFractionDigits="2" /></div></td>

               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral + vlrtotal*linhasrecibo.valor}"/> 
                  </tr>
       		   </c:forEach>
       		   <c:set var="socio" value="0"/> 
       		   <c:if test="${vlrtotalgeral != 0}">
                  <tr bgcolor="#cccccc"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total do Recibo:</strong></div></td>
                  <td><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td></tr>
                  
                  <c:forEach var="linhassocio" items="${listaSoc}">
                     <tr bordercolor="#ffffff">
                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
                        <td colspan="12"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td>
                     </tr>
                     <c:set var="socio" value="${linhassocio.codigo}"/> 
                  </c:forEach>
                  
               </c:if>
            </table>
        </form>
		<form name="form" id="form" method="post">
		  <table width="763" frame="void">
		  	<tr>
		  		<td><input name="cod_pg" id="cod_pg" type="hidden" value="" /></td>
		  		<td><input name="tipo_entr" id="tipo_entr" type="hidden" value="1" /></td>
		  	</tr>                        
          </table>
	  	  <table width="764" frame="void">
  			 <tr>
                	<td colspan="3">
                    	<div align="left">
                    		<c:if test="${not empty listaRec}">
					        	<input type="button" onclick="enviar('EmiteRecibo')" name="bt1" value="Visualizar">   
                    		</c:if>
				        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
						</div>   
                    </td>   
			</tr>
		  </table>
   		</form>
</div> 
  </div>
	<c:import url="rodape.jsp"/>
