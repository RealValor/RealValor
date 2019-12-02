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
			  <li><a href="stu?p=IncluiRecebimento&ctrl=limpar" title="Recebimento de Mensalidades e Doações"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Recebimento<img src="imagens/spacer_1x1px.png" width="6" border="0" /></a></li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Inclusao<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <!-- <li><a href="" title="Repasse de Valores - Aguardando Notas">Agendamento</a></li> -->
			      <li><a>Banco<img src="imagens/spacer_1x1px.png" width="91" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiBanco" title="Cadastro de Bancos">Banco</a></li>
			         <li><a href="stu?p=IncluiSaldoBanco" title="Inclusão mensal de Saldo em Bancos">Saldo em Banco</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiBairro" title="Cadastro de Bairros">Bairro</a></li>
			      <li><a>Cadastro<img src="imagens/spacer_1x1px.png" width="72" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiSocio" title="Cadastro de Associados">Associado</a></li>
			         <li><a href="stu?p=IncluiNaoSocio&ctrl=limpar" title="Cadastro de Não Associados">Nao Associado</a></li>
			         <li><a href="stu?p=IncluiNucleo" title="Cadastro de Não Associados">Núcleo</a></li>
			         <li><a href="stu?p=IncluiOperador" title="Cadastro de Operadores do Sistema">Operador</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiCargo" title="Cadastro de Cargos">Cargo</a></li>
			      <li><a>Divida<img src="imagens/spacer_1x1px.png" width="89" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiDivida" title="Lançamento de Dívidas">Lançamento</a></li>
			         <li><a href="stu?p=QuitaDivida" title="Pagamento e exclusão de Dívidas">Pagamento</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiGrauParentesco" title="Cadastro de Grau Parentesco">Grau Parentesco</a></li>
			      <li><a href="stu?p=IncluiLista" title="Inclusão de Lista de Arrecadação">Lista de Arrecadação</a></li>
			      <li><a href="stu?p=IncluiTipoEntrada" title="Cadastro de Grupos de Receitas">Tipo Entrada</a></li>
			      <li><a href="stu?p=IncluiTipoSaida" title="Cadastro de Grupos de Despesas">Tipo Saida</a></li>
			   </ul>
			   </li>
			  <li><a href="stu?p=IncluiSaida" title="Lançamento das Despesas do Núcleo"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Pagamento<img src="imagens/spacer_1x1px.png" width="7" border="0" /></a></li>
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
			         <li><a href="stu?p=ConsultaReciboNumero" title="Consulta recibos por número">Por Numero</a></li>
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
			             <li><a href="stu?p=AtualizaDadosBalanceteAnual" title="Impressão do Balancete Anual">Balancete Anual</a></li>
			             <!-- ConsultaBalanceteAnual -->
			             <li><a href="stu?p=Acesso" title="Impressão de Estatística de Inadimplência">Estatistica de Inadimplencia</a></li>
			             <!-- stu?p=EstatisticaInadimplencia -->
			          </ul>
			       </li>
			       <li><a>Associados<img src="imagens/spacer_1x1px.png" width="59" border="0" />+</a>
			        <ul>          
			             <li><a href="stu?p=ConsultaAniversariante" title="Impressão da Lista de Aniversariantes">Aniversariantes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=ativo" title="Impressão da Lista de Associados Ativos">Associados Ativos</a></li>
			             <li><a href="stu?p=ConsultaHistoricoSocio" title="Impressão de Historico dos Associados">Historico</a></li>
			             <li><a href="stu?p=ConsultaInadimplente" title="Impressão da Lista de Inadimplentes">Inadimplentes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=todos" title="Relação de Todos os Associados do Núcleo">Todos</a></li>
			          </ul>
			       </li>
			       <li><a>Balancete<img src="imagens/spacer_1x1px.png" width="67" border="0" />+</a>
			           <ul>
			              <li><a href="stu?p=ConsultaBalancete&tipo=finalizado" title="Balancete Mensal Finalizado">Finalizado</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=periodo" title="Balancete Mensal Finalizado Por Período">Por Periodo</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=previo" title="Balancete Mensal Para Conferência">Prévio</a></li>
			           </ul>
			       </li>       
			       <li><a>Movimento Mensal<img src="imagens/spacer_1x1px.png" width="6" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=ConsultaMovimentoEntrada" title="Relatório Mensal de Entradas">Entrada</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaSocio" title="Relatório Mensal de Entradas por Sócio e Período">Entrada por Sócio</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaTipo" title="Relatório Mensal de Entradas por Tipo e Período">Entrada por Tipo</a></li>
			             <li><a href="stu?p=ConsultaMovimentoSaida" title="Relatório Mensal de Saidas">Saida</a></li>
			          </ul>
			       </li>
			       <li><a href="stu?p=ConsultaMovimentoPeriodo" title="Relatório de Entradas e Saidas por Período">Fluxo de caixa</a></li>
			    </ul>
			  </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="25" border="0" />Outros<img src="imagens/spacer_1x1px.png" width="25" border="0" /></a>
			    <ul>
			       <li><a href="stu?p=ConsultaRecibo&controle=cancelar" title="Cancelamento de recibos">Cancela Recibo</a></li>
			       <li><a href="stu?p=FinalizaMes" title="Finaliza movimentação do mês">Finaliza Mes</a></li>
			  		<c:if test="${objUsu.nivel>1}">
			       		<li><a href="javascript:escolheNivel();" title="Muda o nível de acesso ao sistema">Muda nível de acesso</a></li>
			       		<input name="nivel_socio" id="nivel_socio" type="hidden" value="${objUsu.nivel}"/>
			       	</c:if>

			  		<c:if test="${objUsu.nucleo.offline=='S' && objUsu.nivel>2}">
			       		<li><a href="stu?p=BuscaXML" title="Atualiza no sistema os dados gerados em modo off-line">Sincroniza Off-line</a></li>
			       	</c:if>

			       	<c:if test="${objUsu.cpf==22235744320}">
			       		<li><a href="stu?p=EnviaEmail.enviar()" title="Testa envio de email">Testa email</a></li>
			       	</c:if>

					<c:if test="${objUsu.nivel>1}">
			       		<li><a href="javascript:mostraVideo();" title="Vídeo Inicial">Vídeo Inicial</a></li>
					</c:if>
			       	
			       <!-- 
			       <li><a href="stu?p=CopiaDeSeguranca" title="Cópia de segurança - Backup">Copia de Seguranca</a></li>
			       <li><a href="stu?p=RestauraDados" title="Restaura dados do backup">Restaura dados</a></li>
			       <li><a  href="stu?p=ConsultaNucleoOperador&ctrl=1" title="Consulta Núcleos que Utilizam o Sistema">Núcleos Cadastrados<img src="imagens/spacer_1x1px.png" width="5" border="0" /></a></li>
			       <li><a href="stu?p=AtualizaTransacao" title="Atualiza Transações Pendentes">Atualiza Transação</a></li>
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
 	   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
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
			   var nivel = prompt("Digite o nível menor ou igual a: "+valor);

			   if(nivel>valor){
			    	alert("Nível máximo permitido para este acesso: "+valor);
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