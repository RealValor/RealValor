<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	
 <style type="text/css">
 <!--    body,td,th {
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
	    <jsp:useBean id="objPaga" class="beans.Login" scope="session" />
	    <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	    <jsp:useBean id="objMovFin" class="beans.MovAtual" scope="session" />
	    <jsp:useBean id="objTipEnt" class="beans.TipoEntrada" scope="request" />
	<div class="hero-unit" align="center">
		<div id="estrutura">
	        <table width="760" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Mensalidade</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">${objPaga.nome}</div></td></tr>     
		    </table>
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
						<td><div align="center" class="style5 style9"></div></td>
					  </c:if>
					  <c:forEach var="mensalidades" items="${listRecF}">
		              	 <td><div align="center" style="font-size: 12px; color: #000000;"><strong>${mensalidades.mesExtenso}</strong></div></td>
		       		  </c:forEach>
		           </tr>
		           <tr bordercolor="#000000">
		           		<c:if test="${dividaAnoAnterior > 0 && dividaAnoAnterior < 12}">
				     	   <td><div align="center" class="style5 style9"><a href='javascript: chamarmodulo("ConsultaMensalidade","Consulta histórico",${objMovFin.ano-1})' onclick='document.getElementById("ano_paga").value="${objMovFin.ano-1}"' title="Consulta histórico do ano anterior" class="style5 style9 style13 style14" ><img src="imagens/retornar.png" width="16" height="16" border="0" align="middle" /></a></div></td>	
					  	</c:if>
				     	<c:forEach var="mensalidades" items="${listRecF}">
				     	   <c:if test="${mensalidades.data != null}">
								<td bgcolor="#669933" bordercolor="#000000" onclick='document.getElementById("ano_paga").value="<f:formatDate value="${mensalidades.data.time}" type="date" pattern="yyyy" />"; document.getElementById("numero_recibo").value="${mensalidades.recibo}"; document.getElementById("mes_recibo").value="${mensalidades.mes}"; enviar("ConsultaReciboNumero&ctrl=consulta")'><div align="center" class="style5 style9 style13 style14" ><a href="#" title="Consultar recibo" class="style5 style9 style13 style14" ><f:formatDate value="${mensalidades.data.time}" type="date" pattern="dd/MM/yy" /></a></div></td>
								<!-- 
								#009900 --verde anterior
								#669933 --verde atual
								 -->
			               </c:if>
				     	   <c:if test="${mensalidades.data == null}">
				     	    	<c:if test="${mensalidades.ano > 0}">
					     	    	<c:if test="${mensalidades.ano>anoAtual || ( mensalidades.ano==anoAtual && mensalidades.mes>mesAtual )}">
				                		<td bgcolor="#ffffff"><div align="center" class="style5 style9">a vencer</div></td>
					     	    	</c:if>
					     	    	<c:if test="${(mensalidades.ano < anoAtual && mensalidades.ano != 0) || ( mensalidades.ano==anoAtual && mensalidades.mes<=mesAtual )}">
			                			<td bgcolor="#cc6633"><div align="center" class="style5 style9">em aberto</div></td>
			                			<!-- 
										#CC3300 --vermelho anterior
										#cc6633 --vermelho atual
			                			 -->
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
	            </table>
	            
	            <table width="760" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>
				   <tr><td><input name="totalrecibos" id="totalrecibos" type="hidden" value="0" /></td></tr>
	            </table>
	            <c:set var="vlr" value="${recibo.data.time}"/>
	            <table width="760" class="table table-condensed">
				   <c:if test="${not empty listaRecibo}">			   	  
				   	  <div align="center" style="font-size: 16px; color: #000000;"><strong>RECIBO</strong></div> 
	           	      <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
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
	                  </tr>          
			       </c:if>
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
	                  <td bgcolor="#cccccc"><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" maxFractionDigits="2" /></strong></div></td></tr>
						
						<!-- 
	                  <tr bgcolor="#cccccc"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;">Valor cobrado pelo serviço de recebimento on-line:</div></td>
	                  <td bgcolor="#cccccc"><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${vlrtotalgeral*0.0527+0.4}" minFractionDigits="2" maxFractionDigits="2" /></strong></div></td></tr>

	                  <tr bgcolor="#cccccc"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total a Pagar:</strong></div></td>
	                  <td bgcolor="#cccccc"><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${(vlrtotalgeral*0.0527+0.4)+vlrtotalgeral}" minFractionDigits="2" maxFractionDigits="2"/></strong></div></td></tr>
						 -->
	                  
	                  <c:forEach var="linhassocio" items="${listaSocio}">
	                     <tr bordercolor="#ffffff">
	                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
	                        <td colspan="16"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td>
	                     </tr>
	                     <c:set var="socio" value="${linhassocio.codigo}"/> 
	                  </c:forEach>
	               </c:if>
	               	<!-- 
	               <tr bordercolor="#ffffff">
			          <td bordercolor="#ffffff">            
				  		<c:set var="vlrtotalapagar" value="${(vlrtotalgeral*0.0527+0.4)+vlrtotalgeral}" /> 
				  		<input name="totrecibo" id="totrecibo" type="hidden" value="${vlrtotalgeral}" readonly="readonly" />
				  		<input name="totapagar" id="totapagar" type="hidden" value='<f:formatNumber value="${vlrtotalapagar}" minFractionDigits="2" maxFractionDigits="2" />' readonly="readonly" />
	
		              </td>
	               	</tr>
	               	 -->
					<!-- 4.99% +0.40 centavos. porém o desconto é aplicado no total resultante -->
						               	
	            </table>
	
	            <f:setLocale value="en_US" />
				<f:formatNumber var="total" value="${vlrtotalapagar}" type="number" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
				<f:setLocale value="${user.locale}" />
	
	        </form>
	        
		<!-- aqui botão -->
		<!-- 
		<!-- INICIO FORMULARIO BOTAO PAGSEGURO -->
		<!-- 
		<form name="wps-bn" id="wps-bn" action="https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html" method="post"> 
		------------------
		        
		        <input name="receiverEmail" type="hidden" value="${objNucleo.email}"> 
		        
		        <input name="currency" type="hidden" value="BRL">
		  
		        <input name="itemId1" type="hidden" value="0001">  
		        <input name="itemDescription1" type="hidden" value="Pagamento de Mensalidade">
		        <input name="itemAmount1" type="hidden" value="${total}" >
		        <input name="itemQuantity1" type="hidden" value="1">  
		        <input name="itemWeight1" type="hidden" value="1">
		  
		        <input name="reference" type="hidden" value="">  
		          
		        <input onclick="PagSeguroLightbox('1F69A3CF7878ED9994B3DF9DDC706796');" alt="Pague com PagSeguro" name="submit"  type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/120x53-pagar.gif"/>
		        <input alt="Pague com PagSeguro" name="submit"  type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/120x53-pagar.gif"/>
		</form>  		
		 -->	

		<!-- INICIO FORMULARIO BOTAO PAGSEGURO -->
		<form name="pagseguro" id="pagseguro" action="https://ws.sandbox.pagseguro.uol.com.br/v2/checkout" method="post"> 
			<!-- 
			https://stc.sandbox.pagseguro.uol.com.br/v2/checkout 
			-->
		        <!-- Campos obrigatórios -->
		        
		        <input type="hidden" name="token" value="1A1C6552D077407F817B6BFD699BF120"> <!-- aqui buscar o token do núcleo -->
		        <input type="hidden" name="email" value="${objNucleo.email}"> <!-- aqui buscar o e-mail do núcleo udvnmgstu@gmail.com -->
		        <input type="hidden" name="currency" value="BRL">
		  
		        <!-- Itens do pagamento (ao menos um item é obrigatório) -->  
		        <input type="hidden" name="itemId1" value="0001">  
		        <input type="hidden" name="itemQuantity1" value="1">  
		        <input type="hidden" name="itemDescription1" value="Pagamento de Mensalidade">
		        <input type="hidden" name="itemAmount1" value="${total}" >
		        <input type="hidden" name="itemWeight1" value="1">
		  
		        <!-- Código de referência do pagamento no seu sistema (opcional) -->  
		        <input name="reference" type="hidden" value="">  
		          
		        <!-- submit do form (obrigatório) -->  
		        
		        <!-- 
		        <input type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/120x53-pagar.gif" name="submit" alt="Pague com PagSeguro"/>
		         -->
		        

				<!-- Fazer uma chamada ajax para uma ao endereço acima com os parametros correspondentes-->
		</form>  		
		<!-- 
		<button onclick="PagSeguroLightbox('E22D9F9885854EA334201FACDCFCDC58')">Pagar com lightbox</button>

		<button onclick="BuscaCodigoPagSeguro()">Pagar com lightbox</button>
		<button onclick="enviar('GeraBoleto&total='+${total})">Boleto <img src="imagens/cod_barras.png" width="69" height="23" border="0" align="middle" /></button>
		 -->
		        <!-- 
		        
		        enviar("ConsultaReciboNumero&ctrl=consulta")
		        
		        <!DOCTYPE html>
				<html>
				    <head>
				        <title>Minha loja</title>
				    </head>
				    <body>
				        <button onclick="PagSeguroLightbox('38DAA0390C0CECBAA44EDFA10A26C9CA')">Pagar com lightbox</button>
				        <script type="text/javascript"
				            src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>
				    </body>
				</html>
		         -->
			
			<form name="form" id="form" method="post">
		  	  <table width="760" frame="void">
					<tr>
						<td>
					  		<input type="button" onclick="limpar()" name="bt3" value="limpar" />
							<input name="cod_pg" id="cod_pg" type="hidden" value="" />
	
							<input name="div_ant" id="div_ant" type="hidden" value="0" />
	
							<input name="tipo_entr" id="tipo_entr" type="hidden" value="1" />
							<input name="consultaMensalidade" id="consultaMensalidade" type="hidden" value="${consultaMensalidade}" />
				  		     <c:if test="${not empty listDependente}">
						        <img src="imagens/spacer_1x1px.png" width="490" border="0" /><input type="checkbox" checked="checked" name="incluidependentes" id="incluidependentes" value="0" onclick="if(this.checked){mudaStatusDependente(1);}else{mudaStatusDependente(0);}" /><img src="imagens/spacer_1x1px.png" width="2" border="0" />Inclui dependentes
							 </c:if>
						</td>
					</tr>
			  </table>
	   		</form>
	  		<c:if test="${dividaAnoAnterior > 0}">
				<c:if test="${dividaAnoAnterior < 12}">
					<script>
						document.getElementById("div_ant").value = 1;
					</script>
				</c:if>			
			</c:if>
		</div> 	     
	</div>
	<c:import url="rodape.jsp"/>
 		
 	<!-- 
    <script type="text/javascript" src="https://stc.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>
    Acimoa para ativar em procução, abaixo sandbox - Lightbox 
	<script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>
 	 -->

	<script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript">
    
    	/*
	    jQuery(document).ready(function(){
	        //Pegamos o formulário do botão
	        var wpsBn = jQuery('#wps-bn');
	
	        //Interceptamos o clique no botão
	        wpsBn.click(function(e){
	            //Evitamos o comportamento padrão, de submeter o formulário
	            e.preventDefault();
	
				if(enviar("FechaRecebimentoOnline")){ //reativar esta linha
				//if(enviar("ConsultaMensalidade")){
					wpsBn.submit();
				}
	        });
	    });	
	    */
	    
	    function BuscaCodigoPagSeguro(){
	    	enviar('BuscaCodigoPagamento');    	
	    	//return;
	    }
	    
		function pegaValor() {
			document.getElementById("vlr_unit").value = document.formNome.valor.options[document.formNome.entrada.selectedIndex].text;

 			if(document.getElementById("tipo_entrada").value==1||document.getElementById("tipo_entrada").value==0){
 				document.getElementById("vlr_mensalidade").value = document.formNome.valor.options[document.formNome.entrada.selectedIndex].text;
			}
		}

  		window.onload = function(){
  	  	    if(document.form.consultaMensalidade.value*1==0){
  	  	    	//alert('Consulta mensalidades');
  	  	    	enviar('ConsultaMensalidade');
  	  	    }
  	  	    pegaValor();
		};

		if (window.attachEvent) window.attachEvent("onload", navHover);

  	    function AbrirAjax() {
  	   	     var xmlhttp_new;
  	   	     try {
  	   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
  	   	     }catch(e) {
  	   	        try {  	   	      
  	   	           xmlhttp_new = new getXMLHTTP(); //IE8	
  	   	        }catch(ex) {
  	   	           try {  	   	         
  	   	              xmlhttp_new = new XMLHttpRequest(); //Para todos os outros Browsers (FireFox,Opera, etc ...)
  	   	           }catch(exc) {
  	   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
  	   	              xmlhttp_new = null;
  	   	           };
  	   	        };
  	   	     }
  	   	     return xmlhttp_new;
  	    }
  	    
		var fechou=-2;//Tratamento de gravação de recebimento temporário
		var gravourec;
		var tot_recibos=1;	//Tratamento de quantidade de recibos
		var retorno;

  		 function XMLHttpQuantidade(){ //Controla as mudanças do objeto XMLHttpRequest.
  			var qtderecibos;
		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
			   var quantidade = xmlhttp.responseXML; //Armazena a resposta XML.		                
			   qtderecibos = quantidade.getElementsByTagName("total"); //Captura as respostas na Tag <total>
			   //tot_recibos = qtderecibos[0].text;
			   tot_recibos = qtderecibos[0].childNodes[0].data;
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

 			if(document.getElementById("tipo_entrada").value==1||document.getElementById("tipo_entrada").value==0){
 				campo.value = document.getElementById("vlr_mensalidade").value;
 				alert('O valor da mensalidade não pode ser alterado: '+campo.value);
			}

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

  		 function XMLHttpRetornoFecha(){ //Controla as mudanças do objeto XMLHttpRequest.
		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
			   retorno = xmlhttp.responseXML; //Armazena a resposta XML.		                
			   gravourec = retorno.getElementsByTagName("situacao"); //Captura todas as respostas nas Tags <situacao>
			   document.getElementById("reference").value = gravourec[0].childNodes[0].data;
				   
			   //Este código atende gravação de recebimentoOnlineTemp e checagem de recibos reserva. REFATORAR ESTE CÓDIGO!!!
	        }
	     }
	     
  		 function espera(pTempo){
  			 var now=new Date();
			 var segundos = now.getSeconds()+pTempo;
			 var espera = 0;

			 while(espera<segundos){
			     now=new Date();
			     espera = now.getSeconds();
			 };
  		 }

  		 function imprimeRecibo(){
				var width = 840;
				var height = 620;
				
				var left = 99;
				var top = 99;
				var now=new Date();
				var segundos = now.getSeconds();

				window.open(document.getElementById("urlSet").value+'imprimeRecibo.jsp?cod='+segundos,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
				
				//window.open('http://localhost:8080/STU/imprimeRecibo.jsp?cod='+segundos,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
				//window.open('http://www.udvnmg.org/imprimeRecibo.jsp?cod='+segundos,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		 }

		function enviar(pModu) {

			document.form.cod_pg.value = document.formNome.cod_paga.value;
	
			if (pModu=='FechaRecebimentoOnline'){
	
				if (document.getElementById("controle").value*1==0) {
					alert("Antes é necessário confirmar o(s) mes(es) do pagamento!");
					return false;
				} else if (document.getElementById("totrecibo").value*1<1) {	
					alert("Não há valores para fechamento do recibo! Verifique o tipo de entrada");
					return false;
				} else {

					if (!confirm("Confirma a finalização do recibo?")) { //imprescindível o posicionamento deste confirm, 
						return false; 									 //devido assincronismo do Ajax, na busca ACIMA!
					} else {

						url = "stu?p=FechaRecebimentoOnline"; 
						if (document.getElementById) {
							xmlhttp = AbrirAjax();
							if (xmlhttp) {
								xmlhttp.onreadystatechange = XMLHttpRetornoFecha; 
								//Neste ponto o código verifica, também, sucesso na gravação do recebimentoOnlineTemp - REFATORAR ESTE CÓDIGO!
								xmlhttp.open("GET", url, true); //Abre a url.
								xmlhttp.setRequestHeader('Content-Type', 'text/xml');
								xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
								xmlhttp.send(null);
							}
						}
						//aguardar a confirmação do serviço de pagamento, para efetivar gravação do recibo ou descartar transação.
					
						return true;
					}
				}
			} else {

				if (pModu=='IncluiRecebimento'&&document.getElementById("div_ant").value == 1) {
					if(!confirm("Existe(m) mensalidade(s) em aberto no ano anterior! \nConfirma assim mesmo?")){
						return false;
					}
				}
				buscaDados(pModu);
				
				if (pModu=='ConsultaReciboNumero&ctrl=consulta') {

			        url = "stu?p=BuscaQuantidadeRecibos&cod_paga="+document.getElementById("cod_paga").value+"&situ_paga="+document.getElementById("situ_paga").value+"&ano="+document.getElementById("ano_paga").value+"&mes="+document.getElementById("mes_recibo").value; 
					if (document.getElementById) {
						xmlhttp = AbrirAjax();
						if (xmlhttp) {
							xmlhttp.onreadystatechange = XMLHttpQuantidade;
							xmlhttp.open("GET", url, true); //Abre a url.
							xmlhttp.setRequestHeader('Content-Type', 'text/xml');
							xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
							xmlhttp.send(null);
						}
					}

					if (confirm('Visualiza o recibo '+document.getElementById("numero_recibo").value + '/'+document.getElementById("ano_paga").value + '?')) {

						if (tot_recibos > 1) {
							alert(' Encontrados '+tot_recibos+' recibos!\n Para visualizar todos consulte recibo por nome para o mês '+document.getElementById("mes_recibo").value);
						} else {
							espera(2);
						}
						espera(1);
						imprimeRecibo();
					} else {
						return false;
					}
				}
	
			}
		}

		var settimeId;
		function clearTime() {    
			window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
		}
			
		function limpar() {
			document.formNome.consulta.value = '';
			document.formNome.situ_paga.value = 'A';
			buscaDados('IncluiRecebimento&ctrl=limpar');
		}

		function mudaStatusDependente(pStatus) {
			document.getElementById('dependentes').value=pStatus;
		}

		function chamarmodulo(modulo,mensagem,pValor){
			enviar(modulo);
			alert(mensagem+' '+pValor);
		}
	    
</script>
