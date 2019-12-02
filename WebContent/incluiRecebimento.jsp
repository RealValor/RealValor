<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	
 <style type="text/css">
 
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
	
	#cor_fonte { color: #666666; }
 
</style>

		<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	    <jsp:useBean id="objPaga" class="beans.Login" scope="session" />
	    <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	    <jsp:useBean id="objMovFin" class="beans.MovAtual" scope="session" />
	    <jsp:useBean id="objTipEnt" class="beans.TipoEntrada" scope="request" />
	<div class="hero-unit" align="center">
	<div id="estrutura">
        <table width="760" frame="void">
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Recebimento<img src="imagens/spacer_1x1px.png" width="300" border="0" /><img src="imagens/spacer_1x1px.png" width="300" border="0" /><a href="javascript:mostraVideo();" id="cor_fonte" title="Tutorial Recebimentos">Vídeo</a></div></td>
		   <tr><td colspan="17">&nbsp;</td></tr>	
	    </table>
   		<input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade"> 
        	<table width="760" frame="void">
        	<tr>
         	  <td colspan="4">
	         	  <div align="left" class="style6">Nome<img src="imagens/spacer_1x1px.png" width="1" border="0" />         	 
				  <input class="input-xxlarge" type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" title="Para inclusão em mês pago, selecione o Tipo antes de digitar o Nome" value="${objPaga.nome}" onkeyup="pesquisar(this.value,event);" onblur="clearTime();"/>
	
				  <img src="imagens/spacer_1x1px.png" width="1" border="0" />					
	                 <select class="input-medium" name="situacao" id="situacao" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("situ_paga").value=situacao.value ' >
						<option value="${situPaga[0]}">${situPaga[1]}</option>
	                    <option value="${situPaga[0]=='N'?'A':'N'}">${situPaga[0]=='N'?'Associado':'Não Associado'}</option>
	                 </select>
	              <input name="situ_paga" id="situ_paga" type="hidden" value="${situPaga[0]}" />

	              <input name="isento" id="isento" type="hidden" value="${isento}" />

	              <input name="cod_paga" id="cod_paga" type="hidden" value="${objPaga.usuario}"/>
	              <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/></div>
				  <div id="list" style="display:none"><img src="imagens/spacer_1x1px.png" width="56" border="0" />
				  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold;width:485px"  
	    			   onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_click(this,event);">
	                 </select>
	              </div>
              </td>
            </tr>
            <tr>
            	<td ><div align="left" class="style6">Tipo
            	<c:set var="posi" value="5"/>
		          <select class="input-medium" name="entrada" id="entrada" style="font-size: 16px; color: #006633; font-weight: bold;" title="Para inclusão em mês pago, selecione o Tipo: antes de digitar o Nome:" onchange='document.getElementById("tipo_entrada").value=entrada.value; document.getElementById("valor").value=entrada.value; pegaValor()' >
		          	 <c:if test="${tipoEntrada.codigo!=0}" >
	                    <option value="${tipoEntrada.codigo}">${tipoEntrada.descricao}</option>
                   	 </c:if> 
				     <c:forEach var="entradas" items="${listTEnt}">
		                <option value="${entradas.codigo}">${entradas.descricao}</option>
        			 </c:forEach> 
                  </select>
 	 			  <input name="tipo_entrada" id="tipo_entrada" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${tipoEntrada.codigo}" title="Escolha o tipo de recebimento na caixa de seleção" size="3" maxlength="6" readonly />
            		Ano<img src="imagens/spacer_1x1px.png" width="1" border="0" />                
                   <select class="input-small" name="ano" id="ano" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value; chamarmodulo("ConsultaMensalidade","Consulta histórico",ano.value);'>
                      <option value="${objMovFin.ano}">${objMovFin.ano}</option>
                      <option value="${objMovFin.ano + 1}">${objMovFin.ano + 1}</option>
                      <option value="${objMovFin.ano - 1}">${objMovFin.ano - 1}</option>
                   </select>
                   <input name="ano_paga" id="ano_paga" type="hidden" value="${objMovFin.ano}" title="Escolha o ano na caixa de seleção" size="1" maxlength="1" readonly/>
           		   <input name="numero_recibo" id="numero_recibo" type="hidden" value="" readonly/>
           		   <input name="mes_recibo" id="mes_recibo" type="hidden" value="" readonly/>
           		   
           		   <input name="consulta_recibo" id="consulta_recibo" type="hidden" value="${consultaRecibo}" readonly/>
           		   
           		   <c:if test="${not empty listDependente}">
           		   	  <input name="dependentes" id="dependentes" type="hidden" value="1" />
				   </c:if>
           		   <c:if test="${empty listDependente}">
           		   	  <input name="dependentes" id="dependentes" type="hidden" value="0" />
				   </c:if>
           		   
                   <c:set var="contamesini" value="${objMovIni.mes}"/>
                   <c:set var="contamesfin" value="${objMovIni.mes-1}"/> <!--CORRETO! devido necessidade de pgto em qualquer mes do intervalo -->
				   
                   <select class="input-mini" name="mesini" id="mesini" style="font-size: 16px; color: #006633;" onchange='document.getElementById("mesini_paga").value=mesini.value; ' >
                      <c:if test="${objMovIni.mes!=0}" >
	                      <option value="${objMovIni.mes}">${objMovIni.mesExtenso}</option> <!-- objMovIni.mes (o mes de fato) -->
                   	  </c:if>
                      <c:forEach var="escolhemesini" items="${escolheMes}">
                      	<c:if test="${contamesini < 12 }"> <!-- < 12 (devido vetor 0 a 11) -->
	                      	<option value="${contamesini + 1}">${escolheMes[contamesini]}</option> <!-- contamesini + 1 (o próximo mes) -->
	                      	<c:set var="contamesini" value="${contamesini + 1}"/>
                      	</c:if>
					  </c:forEach>
                   </select>
                   <input name="mesini_paga" id="mesini_paga" type="hidden" value="${objMovIni.mes}" size="1" maxlength="2" readonly/>a<img src="imagens/spacer_1x1px.png" width="2" border="0" />
                   <select class="input-mini" name="mesfin" id="mesfin" style="font-size: 16px; color: #006633;" onchange='document.getElementById("mesfin_paga").value=mesfin.value'>
                      <c:if test="${objMovFin.mes!=0}" >
	                      <option value="${objMovFin.mes}">${objMovFin.mesExtenso}</option>
                   	  </c:if>
                      <c:forEach var="escolhemesfin" items="${escolheMes}">
                      	<c:if test="${contamesfin < 12 }">
	                      	<option value="${contamesfin + 1}">${escolheMes[contamesfin]}</option>
	                       	<c:set var="contamesfin" value="${contamesfin + 1}"/>
                      	</c:if>
					  </c:forEach>
                   </select>
                   <input name="mesfin_paga" id="mesfin_paga" type="hidden" value="${objMovFin.mes}" size="1" maxlength="2" readonly/>
                   <img src="imagens/spacer_1x1px.png" width="1" border="0" />Vlr. Unit.<img src="imagens/spacer_1x1px.png" width="1" border="0" />
		           <select name="valor" id="valor" style="display: none; font-size: 16px; color: #006633; font-weight: bold;" >
		           	  <c:if test="${tipoEntrada.codigo!=0}" >
	                     <option value="${tipoEntrada.codigo}">${tipoEntrada.valorStr}</option>
                   	  </c:if> 
				      <c:forEach var="valores" items="${listTEnt}">
		                 <option value="${valores.codigo}">${valores.valorStr}</option>
        			  </c:forEach> 
                   </select>
			  	   <input class="input-small" name="vlr_unit" id="vlr_unit" type="text" size="7" maxlength="9" title="Valor unitário" style="font-size:16px; color:#006633; font-weight:bold" value='<f:formatNumber value="${valores.valorStr}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)" onblur="formataValor(this)" />			  	   
                   <c:if test="${empty listaRecibo}">
				  	   <input name="controle" id="controle" type="hidden" value="0" />			  	   
                   </c:if>
                   <c:if test="${not empty listaRecibo}">
				  	   <input name="controle" id="controle" type="hidden" value="1" />			  	   
                   </c:if>
			  	   <input name="procurar" id="procurar" type="hidden" type="submit" value="procurar"/>
                   <input name="mudoumes" id="mudoumes" type="hidden" value="${mudouMes}" />
                   <input type="button" onclick="enviar('IncluiRecebimento')" name="bt1" value="Confirma">

            	</div></td>
            </tr>
            </table>
            <table width="760" frame="void" >
			   <tr><td colspan="3">&nbsp;</td></tr>	  
            </table>
            <table width="760" border="1">
	           <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		          <c:if test="${dividaAnoAnterior > 0 && dividaAnoAnterior < 12}">
	           	  	 <td><div align="center" style="font-size: 12px; color: #cccccc;"><img src="imagens/spacer_1x1px.png" width="1" border="0" /></div></td>
				  </c:if>
			      <c:forEach var="mensalidades" items="${listRecF}">
	              	 <td><div align="center" style="font-size: 12px; color: #000000;"><strong>${mensalidades.mesExtenso}</strong></div></td>
	       		  </c:forEach>
	           </tr>
	           <tr bgcolor="#ffffff">
	           		<c:if test="${dividaAnoAnterior > 0 && dividaAnoAnterior < 12}">
			     	   <td><div align="center" class="style5 style9"><a href='javascript: chamarmodulo("ConsultaMensalidade","Consulta histórico",${objMovFin.ano-1})' onclick='document.getElementById("ano_paga").value="${objMovFin.ano-1}"' title="Consulta histórico do ano anterior" class="style5 style9 style13 style14" ><img src="imagens/retornar.png" width="16" height="16" border="0" align="middle" /></a></div></td>	
				  	</c:if>
			     	<c:forEach var="mensalidades" items="${listRecF}">
			     	   <c:if test="${mensalidades.data != null}">
							<td bgcolor="#669933" bordercolor="#000000" onclick='document.getElementById("ano_paga").value="<f:formatDate value="${mensalidades.data.time}" type="date" pattern="yyyy" />"; document.getElementById("numero_recibo").value="${mensalidades.recibo}"; document.getElementById("mes_recibo").value="${mensalidades.mes}"; enviar("ConsultaReciboNumero&ctrl=consulta")'><div align="center" class="style5 style9 style13 style14" ><a href="#" title="Consultar recibo" class="style5 style9 style13 style14" ><f:formatDate value="${mensalidades.data.time}" type="date" pattern="dd/MM/yy" /></a></div></td>
		               </c:if>
			     	   <c:if test="${mensalidades.data == null}">
			     	    	<c:if test="${mensalidades.ano > 0}">
				     	    	<c:if test="${mensalidades.ano>anoAtual || ( mensalidades.ano==anoAtual && mensalidades.mes>mesAtual )}">
			                		<td><div align="center" class="style5 style9">a vencer</div></td>
				     	    	</c:if>
				     	    	<c:if test="${(mensalidades.ano < anoAtual && mensalidades.ano != 0) || ( mensalidades.ano==anoAtual && mensalidades.mes<=mesAtual )}">
		                			<td bgcolor="#cc6633" onclick='document.getElementById("mesfin_paga").value="${mensalidades.mes}"; enviar("IncluiRecebimento")'><div align="center" class="style5 style9"><a href="#" title="Receber até este mês" class="style5 style9 style13 style14" >em aberto</a></div></td>
				     	    	</c:if>
			     	    	</c:if>
			     	    	<c:if test="${mensalidades.ano <= 0}">
			     	    		<c:if test="${mensalidades.ano == 0}">
									<td bgcolor="#ffffff" ><div align="center" class="style5 style9"><img src="imagens/spacer_1x1px.png" width="20" border="0" />-<img src="imagens/spacer_1x1px.png" width="20" border="0" /></div></td>
			     	    		</c:if>
				     	    	<c:if test="${mensalidades.ano == -1}">
		                			<td bgcolor="#FFBF00" ><div align="center" class="style5 style9">em espera</div></td>
				     	    	</c:if>
			     	    	</c:if>
		     	      </c:if>
	    			</c:forEach> 
	           </tr>
			   
			   <!-- 
	           <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		          <c:if test="${dividaAnoAnterior > 0 && dividaAnoAnterior < 12}">
	           	  	 <td><div align="center" style="font-size: 12px; color: #cccccc;"><img src="imagens/spacer_1x1px.png" width="1" border="0" /></div></td>
				  </c:if>
			      <c:forEach var="mensalidades" items="${listRecF}">
	              	 <td><div align="center" style="font-size: 12px; color: #000000;"><strong>${mensalidades.mesExtenso}</strong></div></td>
	       		  </c:forEach>
	           </tr>
	           -->
	           
            </table>
            
            <table width="760" frame="void" >
			   <tr><td>&nbsp;</td></tr>	  
            </table>
            <c:set var="vlr" value="${recibo.data.time}"/>
            <table width="760" class="table table-condensed">
			   <c:if test="${not empty listaRecibo}">			   	  
			   	  <div align="center" style="font-size: 16px; color: #000000;"><strong>RECIBO</strong></div> 
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
		      	      
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>V.Unit.</strong></div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Total</strong></div></td>
		      	      <td width="25">&nbsp;</td> 
		      	      <td width="25">&nbsp;</td> 
                  </tr>          
		       </c:if>
		       <c:set var="cont" value="0"/>
		       <c:set var="vlrtotalgeral" value="0"/>
		       <c:forEach var="linhasrecibo" items="${listaRecibo}">
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
               		      <td bgcolor="#669933" ><div align="center" class="style5 style9 style13 style14">${linhasrecibo.meses[i]}</div></td>
               		      <c:set var="vlrtotal" value="${vlrtotal + 1}"/>
		     	       </c:if>
					</c:forEach>					
               	    <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.valor}" minFractionDigits="2" /></div></td> 
               		<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotal*linhasrecibo.valor}" minFractionDigits="2" /></div></td>

           	        <td><div align="center"><a href='javascript: excluirUmItem(${cont},${tamanhoLista})' title="Excluir este item"><img src="imagens/excluir.png" width="16" height="16" border="0" align="middle" /></a></div>
					           	        
           	        <input name="tamlista" id="tamlista" type="hidden" value="0" readonly /></td>

           	        <td width="25"><div align="center"><input type="checkbox" onclick="alimentaContador(${cont})" name="${cont}" id="${cont}" value="${cont}" title="Selecionar item para exclusão"></div></td>
           	        <c:set var="cont" value="${cont + 1}"/>
           	        
               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral + vlrtotal*linhasrecibo.valor}"/> 
                  </tr>
       		   </c:forEach>
       		   <c:set var="socio" value="0"/> 
       		   <c:if test="${vlrtotalgeral != 0}">
                  <tr bgcolor="#cccccc"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total do Recibo:</strong></div></td>
                  
						<td><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td><td>&nbsp;</td>
						
						<td><div id="desativaexclusao" class="style6" style="display:block">
		                <img src="imagens/spacer_1x1px.png" width="3" border="0" /><img src="imagens/excluir_cinza.png" width="16" height="16" border="0" align="middle" /></div>
		                
		                <div id="ativaexclusao" class="style6" style="display:none">
						<img src="imagens/spacer_1x1px.png" width="3" border="0" /><a href='javascript: excluirItens(${cont})' title="Excluir itens selecionados"><img src="imagens/excluir.png" width="16" height="16" border="0" align="middle" /></a></div></td>
                  
                  
					</tr>

                  <c:forEach var="linhassocio" items="${listaSocio}">
                     <tr bordercolor="#ffffff">
                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
                        <td colspan="17"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td>
                     </tr>
                     <c:set var="socio" value="${linhassocio.codigo}"/> 
                  </c:forEach>
                  <tr>
	               <td colspan="17">
		               <div align="left" class="style6">Observação<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
						  <input class="input-xxlarge" type="text" name="observacao" id="observacao" size="70" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" title="" value="" /></div>
	               </td>
               	  </tr>
                  
               </c:if>
            </table>

			<table width="760" frame="void">
               <tr>
		          <td colspan="18" bordercolor="#ffffff">            
			  		<input name="totrecibo" id="totrecibo" type="hidden" value="${vlrtotalgeral}" readonly="readonly" />
	              </td>
               </tr>

        		<tr>               
		          <td>            
		           	<div id="recibos" class="style6" style="display:none"><img src="imagens/spacer_1x1px.png" width="5" border="0" />
		           		<img src="imagens/spacer_1x1px.png" width="5" border="0" />Digite o total de recibos reserva:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
				  		<input name="totalrecibos" id="totalrecibos" type="text" value="0" size="1" maxlength="2" style="font-size:16px; color:#006633; font-weight:bold"/>
				  		<input type="button" onclick="enviar('FechaRecibo')" name="bt2" value="Confirmar">
		           	</div>
	              </td>
               	</tr>
               	<tr>               
		          <td>            
		           	<div id="mostrarecibos" class="style6" style="display:none"><img src="imagens/spacer_1x1px.png" width="5" border="0" >
		           		<img src="imagens/spacer_1x1px.png" width="5" border="0" />Selecione o recibo reserva:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
		           		<c:set var="separador" value="-1"/>
			            <input name="recibo_reserva" id="recibo_reserva" type="hidden" value="0"/>
			            <input name="rec_res" id="rec_res" type="hidden" value="0"/>
		           		<c:if test="${not empty recibosReserva}">
			                <select class="input-large" name="recibo" id="recibo" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("rec_res").value=recibo.value;' >
							    <c:forEach var="recibos" items="${recibosReserva}">
							    	<c:if test="${recibos.mes != separador }" >
								    	<option value="-1">-------------------------------------</option>
							    	</c:if>
							    	<c:if test="${recibos.recibo != 0}">
								    	<c:if test="${recibos.mes < 10 }" >
								    		<option value="${recibos.recibo}${recibos.ano}0${recibos.mes}">${recibos.recibo}/${recibos.ano} - <f:formatDate type="date" pattern="dd/MM/yyyy" value="${recibos.data.time}" /> (${escolheMes[recibos.mes-1]})</option> <!-- devido vetor 0 a 11 -->
								    	</c:if>
								    	<c:if test="${recibos.mes >= 10 }" >
								    		<option value="${recibos.recibo}${recibos.ano}${recibos.mes}">${recibos.recibo}/${recibos.ano} - <f:formatDate type="date" pattern="dd/MM/yyyy" value="${recibos.data.time}" /> (${escolheMes[recibos.mes-1]})</option> <!-- devido vetor 0 a 11 -->
								    	</c:if>
							    	</c:if>
							    	<c:if test="${recibos.recibo == 0}">
							    		<option value="-1">Não existem recibos reserva</option>
							    	</c:if>                
					           		<c:set var="separador" value="${recibos.mes}"/>
			        			</c:forEach> 
			                </select>
		                	<c:if test="${objUsu.nivel != 2 }">
						  		<input type="button" onclick="enviar('FechaRecibo')" name="bt2" value="Confirma" />
					       	</c:if>
		           		</c:if>
		           	</div>
	              </td>
               	</tr>
            </table>
        </form>
		<form name="form" id="form" method="post">
		  <table width="760" frame="void">
		  	<tr>
				<td><input name="cod_pg" id="cod_pg" type="hidden" value="" />
				<input name="tipo_entr" id="tipo_entr" type="hidden" value="1" /></td>
		  	</tr>                        
          </table>
	  	  <table width="760" frame="void">
  				<tr>
                	<td>
                    	<div align="left">
                    		<c:if test="${not empty listaRecibo}">
						       	<input type="button" onclick="enviar('ConsultaMensalidade')" name="bt4" value="Consultar Mensalidades">
						       	<c:if test="${objUsu.nivel != 2 }">
						       		<input type="button" onclick="fecharRecibo()" name="bt5" value="Fechar Recibo">
						       	</c:if>
							    <input type="button" onclick="limpar()" name="bt3" value="limpar">
                    		</c:if>
                    		<c:if test="${not empty listRecF && empty listaRecibo}">
							    <input type="button" onclick="limpar()" name="bt3" value="limpar">
                    		</c:if>
				        	<c:set var="reservarecibo" value="0"/>
				        	<c:forEach var="recibos" items="${recibosReserva}">
				        		<c:if test="${recibos.recibo != 0}">
							    	<c:set var="reservarecibo" value="1"/>
							    </c:if> 
				        	</c:forEach>
				        	<c:if test="${(reservarecibo!=0) and not empty listaRecibo}">
		               			<img src="imagens/spacer_1x1px.png" width="10" border="0" /><input type="checkbox" name="usarecibo" id="usarecibo" value="0" title="Seleciona os recibos reserva do mes em aberto" onclick="if(this.checked){escolheRecibo();document.getElementById('rec_res').value='-1';}else{limpaEscolheRecibo();document.getElementById('rec_res').value='0';}" /><img src="imagens/spacer_1x1px.png" width="2" border="0" />Utilizar recibo reservado<img src="imagens/spacer_1x1px.png" width="2" border="0" />
				        	</c:if>   
						</div>
                    </td>
                    <td>
                    	<div align="right">
				  		    <c:if test="${not empty listDependente}">
						        <input type="checkbox" checked="checked" name="incluidependentes" id="incluidependentes" value="0" onclick="if(this.checked){mudaStatusDependente(1);}else{mudaStatusDependente(0);}" /><img src="imagens/spacer_1x1px.png" width="2" border="0" />Inclui dependentes
							</c:if>
                    	</div>
				    </td>
				</tr>
		  </table>
   		</form>
	</div> 	     
	<form name="form2" id="form2" method="post" action="stu?p=Acesso">
  		<c:if test="${dividaAnoAnterior > 0}">
			<c:if test="${dividaAnoAnterior < 12}">
				<script>
					alert("Existe(m) mensalidade(s) em aberto no ano anterior!");
				</script>
			</c:if>			
		</c:if>
	</form>
	</div>    
	<c:import url="rodape.jsp"/>
 		
  	<script>
  	    window.onload = function(){
  	  	    document.formNome.consulta.focus();
  	  	    pegaValor();
  	  	    if(document.getElementById("consulta_recibo").value > 0){
  	  	    	document.getElementById("consulta_recibo").value = 0;
  	  	    	imprimeRecibo();
  	  	    }
		};
		
		var totatual = -2;  	//Tratamento de mudança de mês
		var tot_recibos = 1;	//Tratamento de quantidade de recibos
		
  	 	if (window.attachEvent) window.attachEvent("onload", navHover);

  	    function AbrirAjax() {
  	   	     var xmlhttp_new;
  	   	     try {
  	   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
  	   	     } 
  	   	     catch(e) {
  	   	        try {  	   	      
  	   	           xmlhttp_new = new getXMLHTTP(); //IE8	
  	   	        } 
  	   	        catch(ex) {
  	   	           try {  	   	         
  	   	              xmlhttp_new = new XMLHttpRequest(); //Para todos os outros Browsers (FireFox,Opera, etc ...)
  	   	           } 
  	   	           catch(exc) {
  	   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
  	   	              xmlhttp_new = null;
  	   	           }
  	   	        }
  	   	     }
  	   	     return xmlhttp_new;
  	    }

	     function mostraVideo(){
	    	 
				var width = 1310;
				var height = 716;
				
				var left = 99;
				var top = 99;
				var now=new Date();
				var segundos = now.getSeconds();
				
				window.open(document.getElementById("urlSet").value+'videoTutorial.jsp?cod='+segundos,'janela, top='+top+', left='+left+'resizable=no, fullscreen=no');
		 }

		var result;
  	    var res_cod;
  	    var res_nome;
  	    var res_situ;
  	  
  	    function pesquisar(buscar,e){
  	    	 var codigoTecla;    
  	  	     if (!e) 
  	  	        var e = window.event; 
	  	  	     
	  	     if (e.keyCode) 
		  	    codigoTecla = e.keyCode;
		  	    
	  	     else if (e.which) 
		  	    codigoTecla = e.which;    

	  	     var key = '';
		     key = String.fromCharCode(codigoTecla); // Memoriza o valor da tecla a partir do seu código.
		         
		     if (codigoTecla == 40) { //Se a tecla pressionada foi seta para baixo.
				   document.getElementById('list').display = "block";
				   document.getElementById("listboxnome").focus(); //Passa o foco para a listbox.
				   document.getElementById("listboxnome").selectedIndex = 0; 
				   document.getElementById("consulta").value = res_nome[0].childNodes[0].data; 
				   document.getElementById("cod_paga").value = res_cod[0].childNodes[0].data; 
				   document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
		     } else {
			    if (buscar.length > 0) { //Se tem alguma string para ser procurada.
			       if((codigoTecla > 64 && codigoTecla < 123)||codigoTecla == 8){            
			          document.getElementById("cod_paga").value=0;
			          document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
			          settimeId = window.setTimeout("startHttpReq('"+buscar+"')",100);
			       }else if (codigoTecla == 27 || codigoTecla == 13){
			    	   pesquisa_test_key(buscar,e);
			       };        
			    }else{
				   if(codigoTecla==27){
				      ShowList(false);
				   };
				};			    
	         };
	     }

  	     function startHttpReq(buscar){
  		    var url = "stu?p=BuscaPessoa&nome_paga="+escape(buscar)+"&situ_paga="+document.getElementById("situacao").value+"&ano="+document.getElementById("ano").value+"&tipo_entrada="+document.getElementById("tipo_entrada").value; //Monta a url de pesquisa. 

  		    if (document.getElementById) { //Verifica se o Browser suporta DHTML.        
  			   xmlhttp = AbrirAjax();        
  			   if (xmlhttp) {
  				   xmlhttp.onreadystatechange = XMLHttpRequestChange;             
  				   xmlhttp.open("GET", url, true); //Abre a url.
  				   xmlhttp.setRequestHeader('Content-Type','text/xml');             
  				   xmlhttp.setRequestHeader('encoding','ISO-8859-1');
  	 			   xmlhttp.send(null); 
  			   };    
  		    };
  	     }
  	     
	     function ShowList(exibir){
			 div = document.getElementById("list");
			 
			 if (exibir==true){
	  			div.style.display = 'block';
			 }else{
	  			div.style.display = 'none';
			 }
		 }
		 
	     function XMLHttpRequestChange() { //Controla as mudanças do objeto XMLHttpRequest.    		    
		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
			   result = xmlhttp.responseXML; //Armazena a resposta XML.	
		       res_cod  = result.getElementsByTagName("codigo"); //Captura todas as respostas nas Tags <codigo>         
		       res_nome = result.getElementsByTagName("nome"); //Captura todas as respostas nas Tags <nome>         
		       res_situ = result.getElementsByTagName("situacao"); //Captura todas as respostas nas Tags <situacao> 
	           document.getElementById("listboxnome").innerHTML = ""; //Apaga o conteúdo da listbox.

	           if( res_nome.length>0){
		           for (var i = 0; i < res_nome.length; i++) { //Popula o listbox
			          res_nome[i].childNodes[0].data = (res_nome[i].childNodes[0].data).replace(/\Æ/, "&");
		        	  //A linha acima soluciona a transferencia de & (e comercial) nessa busca Ajax.
			          new_pgcao = create_pgcao(res_nome[i]);
					  document.getElementById("listboxnome").appendChild(new_pgcao);        
		           };          
		       }

	           if (i>0) {
	        	  var j = i;
		          if(j>3)j=3;
		          document.getElementById("listboxnome").size = j;
		          ShowList(true);
	           }else{
	        	   ShowList(false);
	        	   document.getElementById("consulta").focus();
		       };
	        };
	     }

	     function create_pgcao(nome) { //Cria um novo elemento OPTION.    
		    var new_pgcao = document.createElement("option"); //Cria um OPTION.    
		    var texto = document.createTextNode(nome.childNodes[0].data); //Cria um texto.	          
	        new_pgcao.setAttribute("value",nome.getAttribute("id")); //Adiciona o atributo de valor a nova opção.     
	        new_pgcao.appendChild(texto); //Adiciona o texto a OPTION.    
	        return new_pgcao; // Retorna a nova OPTION.
	     }
	     //Atualiza o campo de pesquisa com o valor da listbox.
	     function pesquisa_selecionada(listbox,e) { 
			document.getElementById("consulta").value = listbox.options[listbox.selectedIndex].text;
			document.getElementById("cod_paga").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
		 }
		     
	     
	     function pesquisa_test_key(listbox,e) {  //Trata o pressionamento das teclas enter e escape    
		    var code;    
	  	    if (!e) 
		  	   var e = window.event;    

	  	    if (e.keyCode) 
		  	   code = e.keyCode;
	  	    else if (e.which) 
		  	   code = e.which;    

	  	    if ((code == 13)||(code == 27)) { //Caso enter passa o foco para o campo de pesquisa, e desativa a listbox. 
	  	       ShowList(false);
			   if (code == 27) { //Caso escape (ESC), apaga o valor do campo de pesquisa, 
			      limpar();
			      document.getElementById("tipo_entrada").value=0;
			   }else{

				   if(document.getElementById("tipo_entrada").value=='1'){
					   
					   if(document.getElementById("situ_paga").value=="N"){
						   document.getElementById("tipo_entrada").value="3";
					   }
				   }
				   enviar('ConsultaMensalidade');
			   }
			   document.getElementById("consulta").focus();  		
		    }
	     }

 	  	 function limpaValor(campo){
  	  		var val=campo.value; 
  	  		var res="",i;
  	  		for(i=1;i<=(val.length);i++){
  	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
  	  					res = val.substr((val.length-i),1)+""+res;
  	  			};
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
 	  					};
 	  				};
 	  			};
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
 	  					};
 	  				};		
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
     		document.getElementById("vlr_unit").value=trocaVirgula(document.getElementById("vlr_unit").value);
		    document.formNome.action='stu?p='+pMod;
  			document.formNome.submit();
		 }

     	 function imprimeRecibo(){
				var width = 840;
				var height = 620;
				
				var left = 99;
				var top = 99;
				var now=new Date();
				var segundos = now.getSeconds();

				window.open(document.getElementById("urlSet").value+'imprimeRecibo.jsp?cod='+segundos,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		 }

  		 function XMLHttpQuantidade(){ //Controla as mudanças do objeto XMLHttpRequest.
	   			var qtderecibos;
	 		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.

	 		       var quantidade = xmlhttp.responseXML; //Armazena a resposta XML.		                
	 			   qtderecibos = quantidade.getElementsByTagName("total"); //Captura as respostas na Tag <total>
	 			   tot_recibos = qtderecibos[0].childNodes[0].data;

	 			   if (tot_recibos > 1) {
	 				  alert('Encontrados '+tot_recibos+' recibos!\nPara visualizar todos consulte recibo por nome para o mês '+document.getElementById("mes_recibo").value);
	 			   };
	 			   
	 	        };
	 	     }

		function enviar(pModu) {
			
			var recebeCampo = document.getElementById("situ_paga").value;
			
			if ((pModu!='FechaRecibo')&&(pModu!='ConsultaReciboNumero&ctrl=consulta')){
				
				if (!(preencheCampo(document.formNome.consulta, 'Nome'))) {
					return false;
				} else {
					if (recebeCampo != "A" && recebeCampo != "") { //"A"(ativo)
						if (!confirm(recebeCampo == 'F' ? "Associado Afastado!":recebeCampo == 'L' ? "Associado em Licença!":recebeCampo == 'T' ? "Associado Transferido!":"Nao Associado!"))
							return false;
					};
					
					var isento = document.getElementById("isento").value;
					
					if (isento == "S") {
						if (!confirm("Associado Isento de Pagamentos!"))
						return false;
					};
				};
			}
			document.form.cod_pg.value = document.formNome.cod_paga.value;
	
			if (pModu=='FechaRecibo'){
	
				if (document.getElementById("controle").value*1==0) {
					alert("Não há recibo para fechamento!");
					return false;
				} else if (document.getElementById("rec_res").value * 1 < 0) {
					alert("Não foi selecionado o recibo reserva solicitado!");
					return false;
				} else if (document.getElementById("totrecibo").value*1<0.01) {	
					alert("Não há valores para fechamento do recibo! Verifique o tipo de entrada");
					return false;
				} else {
					
					if (!confirm("Confirma a finalização do recibo?")) { //imprescindível o posicionamento deste confirm, 
						return false; 									 //devido assincronismo do Ajax, na busca ACIMA!
					} else {
						
						totatual = document.getElementById("mudoumes").value;
						
						var reciboanomes = document.getElementById("rec_res").value;
	
						if (reciboanomes * 1 > 0) {
	
							var reciboano = reciboanomes.substring(0,reciboanomes.length - 2);
	
							document.getElementById("recibo_reserva").value = reciboano;
	
							var recibo = reciboano.substring(0,reciboano.length - 4);
	
							var ano = reciboano.substring(reciboano.length - 4,
									reciboano.length);
							var mes = reciboanomes.substring(
									reciboanomes.length - 2, reciboanomes.length);
	
							if (document.getElementById("ano_paga").value * 1 != ano) {
								if (!confirm('O ano ' + document.getElementById("ano_paga").value * 1 + ' difere da data do recibo reserva! Fecha assim mesmo?')) {
									return false;
								};
							}
	
							if (document.getElementById("mesini_paga").value * 1 != mes
									|| document.getElementById("mesfin_paga").value * 1 != mes) {
								if (!confirm('Mês difere da data do recibo reserva! Fecha assim mesmo?')) {
									return false;
								};
							};
						}
						buscaDados(pModu);
							
						if (totatual == 0 || totatual == 1) {
							imprimeRecibo();
						} else {
							alert(' Erro ao consultar mudança de mês.\n Detectado recebimento com data posterior à data atual!');
							return false;
						};
					};
				};
			} else {

				if (pModu=='ConsultaReciboNumero&ctrl=consulta') {
					
					url = "stu?p=BuscaTotaldeRecibos&cod_paga="+document.getElementById("cod_paga").value+"&situ_paga="+document.getElementById("situacao").value+"&ano="+document.getElementById("ano_paga").value+"&mes="+document.getElementById("mes_recibo").value; 
					if (document.getElementById) {
						xmlhttp = AbrirAjax();
						if (xmlhttp) {
							xmlhttp.onreadystatechange = XMLHttpQuantidade;
							xmlhttp.open("GET", url, true); //Abre a url.
							xmlhttp.setRequestHeader('Content-Type', 'text/xml');
							xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
							xmlhttp.send(null);
						};
					};
				}
	 			buscaDados(pModu);
			};
		}

	    function pesquisa_test_click(listbox,e) { //Testa se a listbox foi clicada.
			ShowList(false);
		    pesquisa_selecionada(listbox,e);
			document.getElementById("consulta").focus(); //Passa o foco para o campo de pesquisa.
        	enviar('ConsultaMensalidade');
		}
	    
		function clearTime() {    
			window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
		}
		function confirmaExclusao(pModu){
			buscaDados(pModu);
		}
			     
		function pegaValor() {
			document.getElementById("vlr_unit").value = document.formNome.valor.options[document.formNome.entrada.selectedIndex].text;
		}
		
		function limpar() {
			document.formNome.consulta.value = '';
			document.formNome.situ_paga.value = 'A';
			buscaDados('IncluiRecebimento&ctrl=limpar');
		}

		function mudaStatusDependente(pStatus) {
			document.getElementById('dependentes').value=pStatus;
		}
		
		function reservarRecibo() {
			alert('Detectada mudança de mês, importante reservar recibos.\nNecessário confirmar!');
			div_reserva = document.getElementById("recibos");
			document.getElementById("totalrecibos").value=3; 
			div_reserva.style.display = 'block';
		}

		function fecharRecibo(){
			
			var mudoumes = document.getElementById("mudoumes").value*1;
			var totalreservados = document.getElementById("totalrecibos").value*1;
			
			if(mudoumes==1 && totalreservados<1){
				reservarRecibo();
			}else{
				enviar('FechaRecibo');
			};
		}
		
		function escolheRecibo() {
			div_recibos = document.getElementById("mostrarecibos");
			div_recibos.style.display = 'block';
		}

		function limpaEscolheRecibo() {
			div_recibos = document.getElementById("mostrarecibos");
			div_recibos.style.display = 'none';
		}

		function chamarmodulo(modulo,mensagem,pValor){
			enviar(modulo);
			alert(mensagem+' '+pValor);
		};
		
		function alimentaContador(campo){
			
			if(document.getElementById(campo).checked == true){
				document.getElementById("tamlista").value = document.getElementById("tamlista").value*1+1;

				div_ativaexclusao = document.getElementById("ativaexclusao");
				div_ativaexclusao.style.display = 'block';
				
				div_desativaexclusao = document.getElementById("desativaexclusao");
				div_desativaexclusao.style.display = 'none';
				
			}else{
				document.getElementById("tamlista").value = document.getElementById("tamlista").value*1-1; 
			}
			
			if(document.getElementById("tamlista").value<1){
				
				div_ativaexclusao = document.getElementById("ativaexclusao");
				div_ativaexclusao.style.display = 'none';
				
				div_desativaexclusao = document.getElementById("desativaexclusao");
				div_desativaexclusao.style.display = 'block';
			}
		};
		
		 function excluirUmItem(posicao,totalItens){

		 	 document.getElementById(posicao).checked = true;
		 	 excluirItens(totalItens);
		 	 
		 };	
		 
		 function excluirItens(tamanholista){
			 var tot=0;
			 var posicoes='';
			 var itemchecado=0;
			 for(i=0;i<tamanholista;i++){
				 var campo=i;
				 if(document.getElementById(campo).checked == true){
					posicoes=posicoes+campo+';';
					itemchecado=campo;
				    tot++;
				 };
			 }
			 
			 var mensagem = tot<2?' Item ':' Itens ';
 		 	 if(!confirm(tot+mensagem+'para exclusão. Confirma?')){
				if(tot<2){
 			 	   document.getElementById(itemchecado).checked = false;
				}
			  	 return false;
		 	 };
			 buscaDados('ExcluiItensRecebimento&pos='+posicoes);
		 };	

	</script>
