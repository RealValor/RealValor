<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
	   <title>Real Valor - Sistema Financeiro UDV</title>
	   <!-- Bootstrap -->
       <link href="css/bootstrap.min.css" rel="stylesheet"/>
	   <link href="css/principal.css" rel="stylesheet" />
	   <script src="scripts/funcoes.js" type="text/javascript" language="javascript" > </script>   
	</head>
	
	<body>
		<img src="imagens/LogoImagem.png" width=100% height="119" />
		<div>&nbsp;</div>
		<input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
		<div id="estrutura">
			<ul id="navmenu">
			  <li><a href="stu?p=IncluiRecebimento&ctrl=limpar" title="Recebimento de Mensalidades e Doa��es"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Recebimento<img src="imagens/spacer_1x1px.png" width="6" border="0" /></a></li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Inclusao<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <!-- <li><a href="" title="Repasse de Valores - Aguardando Notas">Agendamento</a></li> -->
			      <li><a>Banco<img src="imagens/spacer_1x1px.png" width="91" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiBanco" title="Cadastro de Bancos">Banco</a></li>
			         <li><a href="stu?p=IncluiSaldoBanco" title="Inclus�o mensal de Saldo em Bancos">Saldo em Banco</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiBairro" title="Cadastro de Bairros">Bairro</a></li>
			      <li><a>Cadastro<img src="imagens/spacer_1x1px.png" width="72" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiSocio" title="Cadastro de Associados">Associado</a></li>
			         <li><a href="stu?p=IncluiNaoSocio&ctrl=limpar" title="Cadastro de N�o Associados">Nao Associado</a></li>
			         <li><a href="stu?p=IncluiNucleo" title="Cadastro de N�o Associados">N�cleo</a></li>
			         <li><a href="stu?p=IncluiOperador" title="Cadastro de Operadores do Sistema">Operador</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiCargo" title="Cadastro de Cargos">Cargo</a></li>
			      <li><a>Divida<img src="imagens/spacer_1x1px.png" width="89" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiDivida" title="Lan�amento de D�vidas">Lan�amento</a></li>
			         <li><a href="stu?p=QuitaDivida" title="Pagamento e exclus�o de D�vidas">Pagamento</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiGrauParentesco" title="Cadastro de Grau Parentesco">Grau Parentesco</a></li>
			      <li><a href="stu?p=IncluiLista" title="Inclus�o de Lista de Arrecada��o">Lista de Arrecada��o</a></li>
			      <li><a href="stu?p=IncluiTipoEntrada" title="Cadastro de Grupos de Receitas">Tipo Entrada</a></li>
			      <li><a href="stu?p=IncluiTipoSaida" title="Cadastro de Grupos de Despesas">Tipo Saida</a></li>
			   </ul>
			   </li>
			  <li><a href="stu?p=IncluiSaida" title="Lan�amento das Despesas do N�cleo"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Pagamento<img src="imagens/spacer_1x1px.png" width="7" border="0" /></a></li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Consulta<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <li><a>Entrada<img src="imagens/spacer_1x1px.png" width="78" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=ListaTipoEntrada">Tipo Entrada</a></li>
			        </ul>
			      </li>
			      <li><a>Recibo<img src="imagens/spacer_1x1px.png" width="86" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=ConsultaRecibo" title="Consulta recibos por nome">Por Nome</a></li>
			         <li><a href="stu?p=ConsultaReciboNumero" title="Consulta recibos por n�mero">Por Numero</a></li>
			        </ul>
			      </li>
			      <li><a>Saida<img src="imagens/spacer_1x1px.png" width="93" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=ListaTipoSaida">Tipo Saida</a></li>
			        </ul>
			      </li>
			   </ul>
			  </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="10" border="0" />Impressao<img src="imagens/spacer_1x1px.png" width="10" border="0" /></a>
			    <ul>
			       <li><a>Anual<img src="imagens/spacer_1x1px.png" width="96" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=AtualizaDadosBalanceteAnual" title="Impress�o do Balancete Anual">Balancete Anual</a></li>
			             <!-- ConsultaBalanceteAnual -->
			             <li><a href="stu?p=Acesso" title="Impress�o de Estat�stica de Inadimpl�ncia">Estatistica de Inadimplencia</a></li>
			             <!-- stu?p=EstatisticaInadimplencia -->
			          </ul>
			       </li>
			       <li><a>Associados<img src="imagens/spacer_1x1px.png" width="59" border="0" />+</a>
			        <ul>          
			             <li><a href="stu?p=ConsultaAniversariante" title="Impress�o da Lista de Aniversariantes">Aniversariantes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=ativo" title="Impress�o da Lista de Associados Ativos">Associados Ativos</a></li>
			             <li><a href="stu?p=ConsultaHistoricoSocio" title="Impress�o de Historico dos Associados">Historico</a></li>
			             <li><a href="stu?p=ConsultaInadimplente" title="Impress�o da Lista de Inadimplentes">Inadimplentes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=todos" title="Rela��o de Todos os Associados do N�cleo">Todos</a></li>
			          </ul>
			       </li>
			       <li><a>Balancete<img src="imagens/spacer_1x1px.png" width="67" border="0" />+</a>
			           <ul>
			              <li><a href="stu?p=ConsultaBalancete&tipo=finalizado" title="Balancete Mensal Finalizado">Finalizado</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=periodo" title="Balancete Mensal Finalizado Por Per�odo">Por Periodo</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=previo" title="Balancete Mensal Para Confer�ncia">Pr�vio</a></li>
			           </ul>
			       </li>       
			       <li><a>Movimento Mensal<img src="imagens/spacer_1x1px.png" width="6" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=ConsultaMovimentoEntrada" title="Relat�rio Mensal de Entradas">Entrada</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaSocio" title="Relat�rio Mensal de Entradas por S�cio e Per�odo">Entrada por S�cio</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaTipo" title="Relat�rio Mensal de Entradas por Tipo e Per�odo">Entrada por Tipo</a></li>
			             <li><a href="stu?p=ConsultaMovimentoSaida" title="Relat�rio Mensal de Saidas">Saida</a></li>
			          </ul>
			       </li>
			       <li><a href="stu?p=ConsultaMovimentoPeriodo" title="Relat�rio de Entradas e Saidas por Per�odo">Fluxo de caixa</a></li>
			    </ul>
			  </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="25" border="0" />Outros<img src="imagens/spacer_1x1px.png" width="25" border="0" /></a>
			    <ul>
			       <li><a href="stu?p=ConsultaRecibo&controle=cancelar" title="Cancelamento de recibos">Cancela Recibo</a></li>
			       <li><a href="stu?p=FinalizaMes" title="Finaliza movimenta��o do m�s">Finaliza Mes</a></li>
			  		<c:if test="${objUsu.nivel>1}">
			       		<li><a href="javascript:escolheNivel();" title="Muda o n�vel de acesso ao sistema">Muda n�vel de acesso</a></li>
			       		<input name="nivel_socio" id="nivel_socio" type="hidden" value="${objUsu.nivel}"/>
			       	</c:if>

			  		<c:if test="${objUsu.nucleo.offline=='S' && objUsu.nivel>2}">
			       		<li><a href="stu?p=BuscaXML" title="Atualiza no sistema os dados gerados em modo off-line">Sincroniza Off-line</a></li>
			       	</c:if>

			       	<c:if test="${objUsu.cpf==22235744320}">
			       		<li><a href="stu?p=EnviaEmail.enviar()" title="Testa envio de email">Testa email</a></li>
			       	</c:if>

					<c:if test="${objUsu.nivel>1}">
			       		<li><a href="javascript:mostraVideo();" title="V�deo Inicial">V�deo Inicial</a></li>
					</c:if>
			       	
			       <!-- 
			       <li><a href="stu?p=CopiaDeSeguranca" title="C�pia de seguran�a - Backup">Copia de Seguranca</a></li>
			       <li><a href="stu?p=RestauraDados" title="Restaura dados do backup">Restaura dados</a></li>
			       <li><a  href="stu?p=ConsultaNucleoOperador&ctrl=1" title="Consulta N�cleos que Utilizam o Sistema">N�cleos Cadastrados<img src="imagens/spacer_1x1px.png" width="5" border="0" /></a></li>
			       <li><a href="stu?p=AtualizaTransacao" title="Atualiza Transa��es Pendentes">Atualiza Transa��o</a></li>
			        -->
			    </ul>
			  </li>
			  <c:if test="${objUsu.nucleo.offline!='S'}">
				  <li><a href="stu?p=Acesso&flg=1" title="Fechar Sistema para o Operador Atual"><img src="imagens/spacer_1x1px.png" width="33" border="0" />Sair<img src="imagens/spacer_1x1px.png" width="34" border="0" /></a></li>
			  </c:if> 
			  <c:if test="${objUsu.nucleo.offline=='S'}">
				  <li><a href="stu?p=GeraXML" title="Ao sair aguarde o sistema gerar o movimento off-line"><img src="imagens/spacer_1x1px.png" width="33" border="0" />Sair<img src="imagens/spacer_1x1px.png" width="34" border="0" /></a></li>
			  </c:if> 
			</ul>
		</div>
		<script>
		
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
 	   	              alert("Seu navegador n�o tem recursos para uso de Ajax");  	   	            
 	   	              xmlhttp_new = null;
 	   	           }
 	   	        }
 	   	     }
 	   	     return xmlhttp_new;
 	    }

	     function XMLHttpRequestChange() {    		    
			    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
					window.location.href = "stu?p=IncluiRecebimento&ctrl=limpar";
		     }
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

	     function escolheNivel() {

			   var valor = document.getElementById('nivel_socio').value;
			   var nivel = prompt("Digite o n�vel menor ou igual a: "+valor);

			   if(nivel>valor){
			    	alert("N�vel m�ximo permitido para este acesso: "+valor);
			    	return false;
			   }else{
					if(nivel<1){
						nivel=1; 
					}
				   
			   		document.getElementById('nivel_socio').value=nivel;
				   
					url = "stu?p=AlteraNivelAcesso&nivel_socio="+nivel; 
					if (document.getElementById) {
						xmlhttp = AbrirAjax();
						if (xmlhttp) {
							xmlhttp.onreadystatechange = XMLHttpRequestChange;
							xmlhttp.open("GET", url, true); //Abre a url.
							xmlhttp.setRequestHeader('Content-Type', 'text/xml');
							xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
							xmlhttp.send(null);
						};
					};
			   };
			};
		</script>
	</body>
</html>