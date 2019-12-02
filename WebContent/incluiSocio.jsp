<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objSoc" class="beans.Socio" scope="request" />
	  <jsp:useBean id="data" class="java.util.Date"/>
	  
	  <div class="hero-unit" align="center">
		    <div id="estrutura">
	        <table width="764" frame="void">

		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de associado</div></td>
			       <!--
			       <td colspan="3"><div align="right" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Vídeo</div></td>
			       <embed src="imagens/cadastro_socios.avi" width="640" height="480" title="video"> 
			        -->
		       </tr>
	           <tr><td>&nbsp;</td></tr>
		    </table>
			<form name="form" id="form" method="post">
	          <c:if test="${btnRetorna != null}">
	             <a><input type="button" onclick="enviar('${btnRetorna}')" name="bt7" value="Retornar" title="Retornar para módulo de lançamento de despesas"></a>
			  </c:if>

			<table width="764" frame="void">

	            <tr>
	              <c:if test="${not empty imagemAvatar}">
					 <img align="right" src="${imagemAvatar}" width="116" height="134" />
				  </c:if>
	         	  <td colspan="3"><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
				  <input class="input-xxlarge" type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objSoc.nome}" onkeyup="pesquisar(this.value,event,1)" onblur="clearTime()"/>

	              <input name="cod_socio" id="cod_socio" type="hidden" value="${objSoc.codigo}"/>
	              
	              </div>

				  <div id="list" style="display:none"><img src="imagens/spacer_1x1px.png" width="56" border="0" />
				  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold;width:437px"  
	    		        onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_click(this,event);">
	               	</select>
	              </div></td> 
	              
	            </tr>
	            <tr><td>&nbsp;</td></tr>
	            <tr>
	              <td colspan="4"><div align="left" class="style6">Telefone(s):<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                    <input name="telefone_socio" type="text" id="telefone_socio" size="20" maxlength="50" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.telefone}"/>
	             		<img src="imagens/spacer_1x1px.png" width="3" border="0" />E-mail(s):<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                    <input name="email_socio" type="text" id="email_socio" size="45" maxlength="100" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.email}"/>
		          </div></td>
	  		    </tr>
	            <tr>
					<td colspan="4"><div align="left" class="style6">Data Nascimento:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	                    <input class="input-small" name="data_nasc" type="text" id="data_nasc" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objSoc.dataNasc}"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'data_assoc');"/>                     
	     	            <img src="imagens/spacer_1x1px.png" width="10" border="0" />Associa&ccedil;&atilde;o:<img src="imagens/spacer_1x1px.png" alt="" width="5" border="0" />
	                <input class="input-small" name="data_assoc" type="text" id="data_assoc" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objSoc.dataAsso}"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'cpf_socio');"/>
					<img src="imagens/spacer_1x1px.png" width="5" border="0" />CPF:<img src="imagens/spacer_1x1px.png" width="2" border="0" />
	                    <input name="cpf_socio" type="text" id="cpf_socio" size="18" maxlength="14" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.cpfStr}" onkeypress="return mascara(event,this,'###.###.###-##');" onblur="verificaCPF(this);" onkeyup="JumpField(this,'sexo');"/>
					</div></td>
				</tr>
	            <tr>
					<td colspan="4"><div align="left" class="style6">Sexo:<img src="imagens/spacer_1x1px.png" width="5" border="0" />					
	                      <select class="input-large" name="sexo" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("sexo_socio").value=sexo.value'>
	                        <c:if test="${empty objSoc.sexo}">
					             <option value="${objSoc.sexo}"></option>
							 </c:if>
	                        <option value="${objSoc.sexo=='F'?'F':'M'}">${objSoc.sexo=='F'?'Feminino':'Masculino'}</option>
	                        <option value="${objSoc.sexo=='F'?'M':'F'}">${objSoc.sexo=='F'?'Masculino':'Feminino'}</option>
	                        <!-- option mostra sexo atual e, ^ permite alteração -->
	                      </select>
	                      <input name="sexo_socio" type="hidden" id="sexo_socio" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.sexo}" title="Escolha o sexo na caixa de seleção" size="1" maxlength="1" onfocus='javascript:document.form.data_nasc.focus();' readonly/>
	                	  <img src="imagens/spacer_1x1px.png" width="5" border="0" />Grau:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	                      <select name="grau" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("grau_socio").value=grau.value'>
	                        <c:if test="${empty objSoc.grau}" >
								<option value="${objSoc.grau}" > ${objSoc.grau}</option>
	                        </c:if>
	                        <c:if test="${not empty objSoc.grau}" >
		                        <option value="">${objSoc.grau=='M'?'Mestre':(objSoc.grau=='C'?'Conselheiro(a)':(objSoc.grau=='I'?'Discípulo CI':(objSoc.grau=='S'?'Discípulo QS':'')))} </option>
	                        </c:if>
	                        <option value="S">Discípulo QS</option>
	                        <option value="I">Discípulo CI</option>
	                        <option value="C">Conselheiro(a)</option>
	                        <option value="M">Mestre</option>
	                      </select>
	                      <input name="grau_socio" type="hidden" id="grau_socio" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.grau}" title="Escolha o grau na caixa de seleção" size="1" maxlength="1" onfocus='javascript:document.form.cargo.focus();' readonly/>
	                      </div>
	                      <div align="left" class="style6">Cargo:<img src="imagens/spacer_1x1px.png" width="3" border="0" />	
				          <select name="cargo" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("cargo_socio").value=cargo.value'>
							 <c:if test="${not empty objSoc.cargo.codigo}">
					             <option value="${objSoc.cargo.codigo}">${objSoc.cargo.descricao}</option>
							 </c:if>			          
							 <c:forEach var="cargos" items="${listCar}">
								 <c:if test="${(objSoc.cargo.codigo!=0&&objSoc.cargo.codigo!=cargos.codigo)||objSoc.cargo.codigo==0}">
					                <option value="${cargos.codigo}">${cargos.descricao}</option>
								 </c:if>
		        			 </c:forEach> 
		                  </select>
			                <input name="cargo_socio" id="cargo_socio" type="hidden" value="${objSoc.cargo.codigo}" readonly />
			                <img src="imagens/spacer_1x1px.png" width="1" border="0" />Senha:<img src="imagens/spacer_1x1px.png" width="1" border="0" />	                
							<input class="input-small" name="senha_socio" type="password" id="senha_socio" size="10" maxlength="50" onblur="verificaSenha(this);" style="font-size: 17px; color: #006633; font-weight: bold;" value=""/>
				            <img src="imagens/spacer_1x1px.png" width="1" border="0" />A contar de:<img src="imagens/spacer_1x1px.png" width="1" border="0" />
				            <input class="input-small" name="data_historico" type="text" id="data_historico" size="10" maxlength="10" style="font-size: 17px; color: #006633; font-weight: bold;" value="" onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'situacao');"/>
		      			</div>
	      			
	      			</td>
				</tr>
				<tr>    
		        <td colspan="4"><div align="left" class="style6">    
	              <c:if test="${(objSoc.situacao == 'A')||(objSoc.situacao == null)}">
	                <input type="radio" name="situacao" id="situacao" value="ativo" checked="checked" />
	              	Ativo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="licenca" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Em licença<img src="imagens/spacer_1x1px.png" width="3" border="0" />
                    <input type="radio" name="situacao" id="situacao" value="afastado" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Afastado<img src="imagens/spacer_1x1px.png" width="3" border="0" />
                    <input type="radio" name="situacao" id="situacao" value="transferido" />                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Transferido<img src="imagens/spacer_1x1px.png" width="3" border="0" />
                    <input type="radio" name="situacao" id="situacao" value="outro"/>                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ausente por Outro Motivo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	              </c:if>
	              <c:if test="${objSoc.situacao == 'L'}">
	                <input type="radio" name="situacao" id="situacao" value="ativo" />
	              	Ativo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="licenca" checked="checked" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Em licença<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="afastado" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Afastado<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="transferido" />                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Transferido<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="outro"/>                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ausente por Outro Motivo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	              </c:if>
	              <c:if test="${objSoc.situacao == 'F'}">
	                <input type="radio" name="situacao" id="situacao" value="ativo" />
	                Ativo<img src="imagens/spacer_1x1px.png" width="3" border="0" />	                
	                <input type="radio" name="situacao" id="situacao" value="licenca" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Em licença<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="afastado" checked="checked" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Afastado<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="transferido" />                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Transferido<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="outro"/>                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ausente por Outro Motivo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	              </c:if>
	              <c:if test="${objSoc.situacao == 'T'}">
	                <input type="radio" name="situacao" id="situacao" value="ativo" />
	                Ativo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="licenca" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Em licença<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="afastado"/>
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Afastado<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="transferido" checked="checked" />                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Transferido<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="outro"/>                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ausente por Outro Motivo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	              </c:if>
	              <c:if test="${objSoc.situacao == 'O'}">
	                <input type="radio" name="situacao" id="situacao" value="ativo" />
	                Ativo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="licenca" />
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Em licença<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="afastado"/>
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Afastado<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="transferido"/>                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Transferido<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input type="radio" name="situacao" id="situacao" value="outro" checked="checked" />                      
	                <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ausente por Outro Motivo<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	              </c:if>
	              </div></td>
	              </tr>
	              <tr><td>&nbsp;</td></tr>
		          <tr>
		           	<td colspan="4"> <div align="left" class="style6">Observação:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
		               <input class="input-xxlarge" name="observacao" type="text" id="observacao" size="65" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.observacao}"/>
		           	</div></td>        
				  </tr>
		          <tr><td>&nbsp;</td></tr>
	          </table>
	          
	          <c:if test="${not empty listDependente}">
		          <table width="762" border="1">
			        <tr><td colspan="5"><div align="center" class="style6">Dependentes para composição do recibo</div></td></tr>
			    	<tr bordercolor="#cccccc" bgcolor="#cccccc">
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Nome</strong></div></td>
			    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>CPF</strong></div></td>
		      	      	<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Grau de Parentesco</strong></div></td> 
	               		<td>&nbsp;</td>					
			   	    </tr>
			        
			        <c:forEach var="linhasdependente" items="${listDependente}">
				     	<tr bgcolor="#ffffff">
					        <td><div align="center"><a href="stu?p=ConsultaSocio&cod_socio=${linhasdependente.dependente.codigo}"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;">${linhasdependente.dependente.nome}</div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;">${linhasdependente.dependente.cpf==0?'':linhasdependente.dependente.cpf }</div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;">${linhasdependente.grauparentesco.descricao}</div></td>
							
		               		<c:if test="${objUsu.nivel > 2 }">
			           	        <td><div align="center" style="font-size: 12px; color: #000000;"><a href='javascript: confirmaExclusao("ExcluiDependente&cod_socio="+${linhasdependente.socio.codigo}+"&cod_dependente="+${linhasdependente.dependente.codigo})' title="Excluir"><img src="imagens/excluir.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							</c:if>
		               		<c:if test="${objUsu.nivel < 3 }">
			           	        <td width="25"><div align="center"><a><img src="imagens/excluir_cinza.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							</c:if>
			            </tr>
			        </c:forEach>
			        <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
					   <td bordercolor="#ffffff" colspan="5"><div align="right" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
				    </tr>	       		   
		          	<tr><td>&nbsp;</td></tr>
			   	  </table>
	          </c:if>

	          <table>
	              <tr>               
			         <td>
			         	<div id="incluidependentes" class="style6" style="display:none">Dependente:<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
						    <input type="text" name="consultadependente" id="consultadependente" size="40" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objDependente.dependente.nome}" onkeyup="pesquisar(this.value,event,2)" onclick="pesquisa_test_click(this,event);" onblur="clearTime()"/>
						    <img src="imagens/spacer_1x1px.png" width="3" border="0" />Grau:<img src="imagens/spacer_1x1px.png" width="3" border="0" />	
					        <select name="graudependencia" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("grauparentesco").value=graudependencia.value'>
					        	<option value="0"></option>
								<c:forEach var="parentesco" items="${listGrauParentesco}">
						           <option value="${parentesco.codigo}">${parentesco.descricao}</option>
			        			</c:forEach> 
			                </select>
				            <input name="grauparentesco" id="grauparentesco" type="hidden" value="${objDependente.grauParentesco.codigo}" readonly />
			                <input name="cod_dependente" id="cod_dependente" type="hidden" value="${objDependente.dependente.codigo}"/>
	
			                <div id="listdependente" style="display:none"><img src="imagens/spacer_1x1px.png" width="104" border="0" />
						  	 <select name="listboxdependente" id="listboxdependente" style="font-size:18px; color:#006633; font-weight:bold;width:356px"  
		    		            onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_key(this,event);">
				             </select>
		             		<input name="cod_dep" id="cod_dep" type="hidden" value="${objDependente.codigo}" />
		              		</div>
			                <input type="button" onclick="enviar('IncluiDependente')" name="bt2" value="Confirmar inclusão de dependente" />
			                <input type="button" onclick="limpar()" name="bt6" value="limpar">
							
			                <c:if test="${not empty objSoc.nome}">
				            	<img src="imagens/spacer_1x1px.png" width="250" border="0" /><input type="checkbox" checked="checked" name="incluidependente" id="incluidependente" value="0" title="Incluir dependentes para recebimento de mensalidades" onclick="if(this.checked){incluirDependente();}else{limpaIncluirDependente();}" /><img src="imagens/spacer_1x1px.png" width="2" border="0" />Incluir dependentes
					        </c:if>

			            </div>
	              	</td>
	              </tr>
	          </table>
			  <table width="764">
	  				<tr>
	                	<td colspan="3">
		                	<div id="botoespagina" class="style6" style="display:block">
		                		<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('IncluiSocio')" name="bt1" value="Incluir">
						        	<input type="button" onclick="enviar('AlteraSocio')" name="bt3" value="alterar">   
								</c:if>
					        	<input type="button" onclick="enviar('ListaSocio')" name="bt5" value="listar todos">
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
					        	
					        	<c:if test="${objUsu.nivel > 2 }">
						        	<c:if test="${not empty objSoc.nome}">
						        	
							        	<c:if test="${ objUsu.cpf == 22235744320 }">
							        		<input type="button" onclick="Caso seja necessário implementar posteriormente" name="bt7" value="enviar e-mail">
						        		</c:if>
						        		
							            <img src="imagens/spacer_1x1px.png" width="37" border="0" />Receber notificações por e-mail?:
						                <select class="input-small" name="notifica" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("notificacao").value=notifica.value' title="Habilita o recebimento de notificações por e-mail">
						                   <option value="${objSoc.notifica=='S'?'S':'N'}">${objSoc.notifica=='S'?'Sim':'Não'}</option>
						                   <option value="${objSoc.notifica=='S'?'N':'S'}">${objSoc.notifica=='S'?'Não':'Sim'}</option>
						                </select>
									    <input name="notificacao" type="hidden" id="notificacao" size="1" maxlength="1" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.notifica}"/>

							            <img src="imagens/spacer_1x1px.png" width="72" border="0" />Sócio Isento?:
						                <select class="input-small" name="isenta" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("isencao").value=isenta.value' title="Isenção total de pagamentos">
						                   <option value="${objSoc.isencao=='S'?'S':'N'}">${objSoc.isencao=='S'?'Sim':'Não'}</option>
						                   <option value="${objSoc.isencao=='S'?'N':'S'}">${objSoc.isencao=='S'?'Não':'Sim'}</option>
						                </select>
									    <input name="isencao" type="hidden" id="isencao" size="1" maxlength="1" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.isencao}"/>

					               		<img src="imagens/spacer_1x1px.png" width="40" border="0" /><input type="checkbox" name="incluidependente" id="incluidependente" value="0" title="Incluir dependentes para recebimento de mensalidades" onclick="if(this.checked){incluirDependente();}else{limpaIncluirDependente();}" /><img src="imagens/spacer_1x1px.png" width="2" border="0" />Incluir dependentes
						        	</c:if>
								</c:if>
								
				            </div>              
	                    </td>   
					</tr>
			  </table>
	   		</form>
	</div> 
	  </div>
	<c:import url="rodape.jsp"/>
	
	<script>

      window.onload = function(){document.form.consulta.focus();}
      if (window.attachEvent) window.attachEvent("onload", navHover);
      //-----------------------------------------------------------
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

      	var lista;
      	var box;
      	var nome;
      	var codigo;

      	var result;
        var res_cod;
      	var res_nome;
      	var res_situ;
      
			function pesquisar(buscar,e,tipo){
	    	 var codigoTecla;    

 	      	 lista="list";
	      	 box="listboxnome";
	      	 nome="consulta";
	      	 codigo="cod_socio";
   	    	 
	    	 if(tipo*1==2){
     	      	 lista="listdependente";
    	      	 box="listboxdependente";
    	      	 nome="consultadependente";
    	      	 codigo="cod_dependente";
	    	 }
	    	 
	  	     if (!e) 
	  	        var e = window.event; 
	  	  	     
	  	     if (e.keyCode) 
		  	    codigoTecla = e.keyCode;
		  	    
	  	     else if (e.which) 
		  	    codigoTecla = e.which;    

			if (codigoTecla == 40) { //Se a tecla pressionada foi seta para baixo.        
  				   document.getElementById(lista).display = "block";
				   document.getElementById(box).focus(); //Passa o foco para a listbox.
				   document.getElementById(box).selectedIndex = 0; 
				   
				   document.getElementById(nome).value = res_nome[0].childNodes[0].data;
   				   document.getElementById(codigo).value = res_cod[0].childNodes[0].data; 
			}else {
 				if (buscar.length > 0) { //Se tem alguma string para ser procurada.
     				if((codigoTecla > 64 && codigoTecla < 123)||codigoTecla == 8){
				          document.getElementById(codigo).value=0;
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
			    var url = "stu?p=BuscaPessoa&nome_paga="+escape(buscar)+"&situ_paga="+document.getElementById("situacao").value; //Monta a url de pesquisa. 
		
			    if (document.getElementById) { //Verifica se o Browser suporta DHTML.        
				   xmlhttp = AbrirAjax();        
				   if (xmlhttp) {
					   xmlhttp.onreadystatechange = XMLHttpRequestChange;             
					   xmlhttp.open("GET", url, true); //Abre a url.
					   xmlhttp.setRequestHeader('Content-Type','text/xml');             
					   xmlhttp.setRequestHeader('encoding','ISO-8859-1');
					   xmlhttp.send(null); 
				   }    
			    }
		}
		function ShowList(exibir){
			 div = document.getElementById(lista);
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
		      res_situ  = result.getElementsByTagName("situacao"); //Captura todas as respostas nas Tags <situacao> 
		      document.getElementById(box).innerHTML = ""; //Apaga o conteúdo da listbox.
		      if( res_nome.length>0){
		          for (var i = 0; i < res_nome.length; i++) { //Popula o listbox
			          new_pgcao = create_pgcao(res_nome[i]);
					  document.getElementById(box).appendChild(new_pgcao);        
		          }          
		      }

		      if (i>0) {
		    	 var j = i;
		         if(j>3)j=3;
		         document.getElementById(box).size = j;
		         ShowList(true);
		      }else{
		   	     ShowList(false);
		   	     document.getElementById(nome).focus();
		      }
		      
		   }else{
			  ShowList(false);
			  document.getElementById(nome).focus();
		   }
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
		   document.getElementById(nome).value = listbox.options[listbox.selectedIndex].text;
		   document.getElementById(codigo).value = res_cod[document.getElementById(box).selectedIndex].childNodes[0].data; 
		}
		function pesquisa_test_key(listbox,e) {  //Trata o pressionamento das teclas enter e escape    
		  	var code;    
			if (!e) 
			   	var e = window.event;
		
			if (e.keyCode)
				code = e.keyCode;
			else if (e.which) 
		 		code = e.which;    
		
			if ((code == 13)||(code == 27)){ //Caso enter passa o foco para o campo de pesquisa,  e desativa a listbox. 
				ShowList(false);
				if (code == 27) { //Caso escape (ESC), apaga o valor do campo de pesquisa, 
					limpar();
				}else{
					if(nome=="consulta"){
						enviar('ConsultaSocio&ctrl=0');
					};
				};        
		   	};
		}
		
		function pesquisa_test_click(listbox,e) { //Testa se a listbox foi clicada.
		   ShowList(false);    
		   pesquisa_selecionada(listbox,e);
		   document.getElementById(nome).focus(); //Passa o foco para o campo de pesquisa.
		   enviar('ConsultaSocio&ctrl=0');
		}
		
		function clearTime() {    
		   window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
		}
       //-----------------------------------------------------------

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

	    function preencheCampo(pCampo,pDesc){
		   if(pCampo.value == ''){
			  alert('Necessário Campo '+pDesc);
			  pCampo.focus();
		      return false;
		   }
		   return true;   
	    }

  	function verificaCPF(src){
   		 
	         var i;
	         var s = limpaValor(src); //document.form.cpf_socio

	         if(s.length < 1) return true;
	         
	         c = s.substr(0,9);
	         var dv = s.substr(9,2);
	         var d1 = 0;
	         var result = 1;	
					
	         for (i = 0; i < 9; i++){
	            d1 += c.charAt(i)*(10-i);
	         }
	
	         if (d1 == 0){
	            result = 0;
	         }
	
	         d1 = 11 - (d1 % 11);
	         if (d1 > 9) d1 = 0;
	
	         if (dv.charAt(0) != d1){
	            result = 0;
	         }
	
	         d1 *= 2;
	         for (i = 0; i < 9; i++){
	            d1 += c.charAt(i)*(11-i);
	         }
	
	         d1 = 11 - (d1 % 11);
	         if (d1 > 9) d1 = 0;
	
	         if (dv.charAt(1) != d1){
	            result = 0;
	         }
	
	         if (result==0) {
	            alert("CPF "+src.value+" inválido!");
	            src.value = '';
	            src.focus();
	            return false;
	         }else{
		        return true;
	         }
 		}

		function verificaSenha(senha){
			var valorSenha = senha.value;
	      	if(valorSenha.length < 6 && valorSenha.length != 0){
					alert("Comprimento mínimo da senha 6 caracteres");
					document.getElementById("senha_socio").value = "";
					document.getElementById("senha_socio").focus();
					return false;
	      	}
		}
	    
	    function buscaDados(pMod){
	       document.form.action='stu?p='+pMod;   
		   document.form.submit();
	    }
	    
	    function enviar(pModu){
	    	if(pModu!='IncluiDependente'&&pModu!='ListaSocio'&&pModu!='ConsultaSocio&ctrl=0'){

	    		if(!preencheCampo(document.form.consulta,'Nome')){
		   		     return false;
				}
				  
				if(!preencheCampo(document.form.cpf_socio,'Cpf')){
					 return false;
				}
		   }
		   
		   if(document.form.consultadependente.value!=""&&!preencheCampo(document.form.grauparentesco,"Grau Parentesco")){
			   document.form.grauparentesco.focus();
				return false;
		   }
		   document.form.cpf_socio.value=limpaValor(document.form.cpf_socio);
 		   buscaDados(pModu);
	    }
	    	
	     function confirmaExclusao(pModu){
			 if(confirm("Confirma a exclusao do dependente?")){
			    buscaDados(pModu);
			 }
		 }

		function incluirDependente() {
			div_dependentes = document.getElementById("incluidependentes");
			div_dependentes.style.display = 'block';

			div_botoespagina = document.getElementById("botoespagina");
			div_botoespagina.style.display = 'none';

		}
		function limpaIncluirDependente() {
			div_dependentes = document.getElementById("incluidependentes");
			div_dependentes.style.display = 'none';

			div_botoespagina = document.getElementById("botoespagina");
			div_botoespagina.style.display = 'block';				

		}

		function enviarEmail(){
			buscaDados('EnviaEmailSocio');
		}

		function limpar(){
		   document.form.consulta.value=''; 
		   buscaDados('IncluiSocio');
 	       document.form.CampoStatus.value='';
 	       document.form.sexo.value='';	   	     
 	       document.form.consulta.focus();
	    }
	</script>   
	