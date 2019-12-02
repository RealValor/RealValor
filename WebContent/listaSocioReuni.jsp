
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	<jsp:useBean id="objSoc" class="beans.Socio" scope="session" />
	<div class="hero-unit" align="center">
	
		<div id="estrutura">
			<table width="764" frame="void">
				<c:if test="${totalSocios <= 1}">
			   		<tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">${totalSocios} Nome de sócio do Reuni não encontrado no Real Valor. Para sincronizar clique em incluir</div></td></tr>
				</c:if>
				<c:if test="${totalSocios > 1}">
			   		<tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">${totalSocios} Nomes de sócios do Reuni não encontrados no Real Valor. Para sincronizar clique em incluir</div></td></tr>
				</c:if>
	           <tr><td>&nbsp;</td></tr>
			</table>

		<form name="form" id="form" method="post">

			<table width="762" border="1">
				<c:if test="${gravaTodos == 0}">
					<tr>
						<td colspan="5"><input type="button" onclick="enviar('IncluiSocioReuniTodos')" name="bt1" value="Incluir Todos"></input></td>
					</tr>
				</c:if>

				<tr bordercolor="#cccccc" bgcolor="#cccccc">
					<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Nome</strong></div></td>
					<td><div align="center" style="font-size: 12px; color: #000000;"><strong>CPF</strong></div></td>
					<td><div align="center" style="font-size: 12px; color: #000000;"><strong></strong></div></td>
				</tr>

				<c:if test="${gravaTodos > 0}">
						<div id="nomesSemelhantes" style="display: block"><img src="imagens/spacer_1x1px.png" width="104" border="0" /> 
				          <c:forEach var="socioAuxiliarReuni" items="${listaSocioAuxiliar}">
								<tr bordercolor="#cc6633" bgcolor="#cc6633">
									<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${socioAuxiliarReuni.nome}</div></td>
									<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${socioAuxiliarReuni.cpf}</div>
									<td colspan="5"><input type="button" onclick="incluiSocioCpf('IncluiSocioReuniSemelhante&ctrl=1&cd_socio=${socioAuxiliarReuni.codigo}','${socioAuxiliarReuni.nome}','${ socioAuxiliarReuni.cpf==0?objSocio.cpf:socioAuxiliarReuni.cpf }')" name="bt1" value="Mesmo sócio"></input></td>
								</tr>
				          </c:forEach>
						</div>
			          	<tr bordercolor="#669933" bgcolor="#669933">
			          		<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${objSocio.nome}</div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${objSocio.cpf}</div>
			          		<td colspan="5"><input type="button" onclick="incluiSocioCpf('IncluiSocioReuniSemelhante&ctrl=2&cd_socio=0','${objSocio.nome}','${objSocio.cpf}')" name="bt1" value="Novo Nome    "></input></td>
			          	</tr> 																										<!-- socioReuni -->
					  	<tr><td colspan="3"><div align="center" style="font-size: 12px; color: #000000;"><strong>Nome(s) semelhante(s) encontrado(s) no Real Valor!<br />Click em "Mesmo sócio" para atualizar o cadastro já existente, ou em "Novo nome" pra incluir novo sócio</strong></div></td></tr>
					  	<tr><td colspan="3"><div><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td></tr>
				</c:if>

				<c:forEach var="socioReuni" items="${listaSocioReuni}">
					<c:if test="${socioReuni.name!=''}">
						<tr bgcolor="#ffffff">
							<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${socioReuni.name}</div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="3" border="0" />${socioReuni.cpf}</div>

							<input type="hidden" name="cpf_socio" id="cpf_socio" size="18" maxlength="14" style="font-size: 17px; color: #006633; font-weight: bold;" value=""></input></td>
							<td colspan="5"><input type="button" onclick="incluiSocioCpf('IncluiSocioReuni&id_socio=${socioReuni.id}','${socioReuni.name}','${socioReuni.cpf}')" name="bt1" value="Incluir"></input></td>

						</tr>
					</c:if>
				</c:forEach>

				<tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
					<td bordercolor="#ffffff">&nbsp;</td>
					<td bordercolor="#ffffff" colspan="5"><div align="right" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				</tr>
			</table>
		</form>
	</div> 
	</div>
	<c:import url="rodape.jsp"/>

	<script>
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

    function preencheCampo(pCampo,pDesc){
		   if(pCampo.value == ''){
			  alert('Necessário Campo '+pDesc);
			  pCampo.focus();
		      return false;
		   }
		   return true;   
	    }

    function limpaValor(campo){
		 var val=campo; 

		 var res="",i;
		 for(i=1;i<=(val.length);i++){
			if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
				res = val.substr((val.length-i),1)+""+res;
			}
		 }
		 return res;
   }

  	function verificaCPF(src){
  		
        var i;
        var s = limpaValor(src);

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
           alert("CPF "+src+" inválido!");
           src.value = '';
           return false;
        }else{
	        return true;
        }
	}

	  function buscaDados(pMod){
		 document.form.action='stu?p='+pMod;   
		 document.form.submit();
	  }
	  
	  function incluiSocioCpf(pModu,pNome,pCpf){

			//alerta('pCpf '+pCpf);

			var cpf=pCpf;
			var digitacpf = true;
			
			do{
				
				if (pCpf*1==0) {
					do {
						cpf = prompt("Cpf de "+pNome);
					} while (cpf == "");
				};
				
				pCpf=0;
				if (verificaCPF(cpf)) {
					digitacpf = false;
					document.getElementById("cpf_socio").value = cpf;
					buscaDados(pModu+'&cpf_socio='+cpf);
				};
				
			}while(digitacpf);
	  
	  }

	  function enviar(pModu) {
		 buscaDados(pModu);
	  }
	</script>
