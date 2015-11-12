<?php
ini_set ( 'display_errors', 1 );
ini_set ( 'display_startup_erros', 1 );
error_reporting ( E_ALL );
function __autoload($classe) {
	if (file_exists ( 'classes/dao/' . $classe . '.php' ))
		include_once 'classes/dao/' . $classe . '.php';
	if (file_exists ( 'classes/model/' . $classe . '.php' ))
		include_once 'classes/model/' . $classe . '.php';
	if (file_exists ( 'classes/controller/' . $classe . '.php' ))
		include_once 'classes/controller/' . $classe . '.php';
	if (file_exists ( 'classes/util/' . $classe . '.php' ))
		include_once 'classes/util/' . $classe . '.php';
	if (file_exists ( 'classes/view/' . $classe . '.php' ))
		include_once 'classes/view/' . $classe . '.php';
}

$sessao = new Sessao ();

if (isset ( $_GET ["sair"] )) {
	
	$sessao->mataSessao ();
	header ( "Location:./index.php" );
}

?>

<!DOCTYPE html>
<html lang="pt-BR">
	<head>

		<meta charset="UTF-8">
		<meta name="description" content="Curso Bootstrap - PontoCanal"/>
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no" />
		
		<title>Projeto Catraca</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
		<link rel="stylesheet" href="http://spa.dsi.unilab.edu.br/spa/css/spa.css" />		
		<link rel="stylesheet" href="css/estilo.css" type="text/css" media="screen">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>		
		<script>window.jQuery || document.write('<script src="js/jquery-1.7.1.min.js"><\/script>')</script>
		<script src="js/bootstrap.js"></script>		

	</head>

	<body >
		<div class="pagina fundo-cinza1">
			<div id="barra-governo">
			    <div class="resolucao config">
			       <div class="a-esquerda">
			          <a href="http://brasil.gov.br/" target="_blank"><span id="bandeira"></span><span>BRASIL</span></a>
			          <a href="http://acessoainformacao.unilab.edu.br/" target="_blank">Acesso à  informa&ccedil;&atilde;o</a>
			       </div>
			       <div class="a-direita"><a href="#"><i class="icone-menu"></i></a></div>
			       <ul>
			          <li><a href="http://brasil.gov.br/barra#participe" target="_blank">Participe</a></li>
			          <li><a href="http://www.servicos.gov.br/" target="_blank">Servi&ccedil;os</a></li>
			          <li><a href="http://www.planalto.gov.br/legislacao" target="_blank">Legisla&ccedil;&atilde;o</a></li>
			          <li><a href="http://brasil.gov.br/barra#orgaos-atuacao-canais" target="_blank">Canais</a></li>
			       </ul>
			    </div>
			</div>

			<div class="doze colunas banner gradiente">
					
				<div id="acessibilidade" class="">
				    <div class="resolucao config">
				        <a href="#conteudo" tabindex="1" accesskey="1">Ir para o conte&ccedil;o <b>1</b></a>
				        <a href="#menu" tabindex="2" accesskey="2">Ir para o menu <b>2</b></a>
				        <a href="#busca" tabindex="3" accesskey="3">Ir para a busca <b>3</b></a>
				        <a href="#rodape" tabindex="4" accesskey="4"><span>Ir para o rodap&eacute; <b>4</b></a>
				    </div>
				</div>

				<div id="topo" class="resolucao config">
					<div class="tres colunas">
						<a href="http://www.dti.unilab.edu.br"><img class="imagem-responsiva" src="img/logo_h-site.png" alt=""></a>				
					</div>
					<div class="seis colunas centralizado">
						<h1>CATRACA<br><small class="texto-branco">Controle Administrativo de Tr&aacute;go Acad&ecirc;ico Automatizado</small></h1>
					</div>
					<div class="tres colunas alinhado-a-direita">
						<a href="http://www.unilab.edu.br"><img class="imagem-responsiva centralizada" src="img/logo-unilab-branco.png" alt=""></a>
					</div>			
				</div>
			</div>

			<div id="barra" class="doze colunas fundo-azul3 alinhado-a-direita">
				<div class="config">
					<a href="#"><span>Perguntas frequentes |</span></a>
					<a href="#"><span>Contato |</span></a>
					<a href="#"><span>Servi&ccedil;os |</span></a>
					<a href="#"><span>Dados Abertos |</span></a>
					<a href="#"><span>&Aacute;rea de Imprensa|</span></a>
				</div>
			</div>		
						
			<div class="doze colunas">
				<div class="resolucao config">
				
				<?php 
				if($sessao->getNivelAcesso() == SESSAO::NIVEL_SUPER){
					echo '<div class="duas colunas">					
						<div class="padding">
						    <a href="#expandir_menu" title="Clique para expandir o menu" class="menu-resp icone-menu2"> Menu vertical</a>
						    <div id="expandir_menu" class="menu-vertical">
						        <a href="#ocultar_menu" class="fechar-menu icone-cross"></a>
						        <ol>
						            <li><a href="index.php" class="item-vertical-ativo"><span class="icone-home3"></span> <span class="item-vertical-texto">In&iacute;</span></a></li>
						            <li><a href="cadastro.php" class="item-vertical"><span class="icone-drawer"></span> <span class="item-vertical-texto">Cadastro</span></a></li>						            					            
						            <li><a href="cartao.php" class="item-vertical"><span class="icone-profile"></span> <span class="item-vertical-texto">Cart&atilde;o</span></a></li>
						            <li><a href="relatorios.php" class="item-vertical"><span class="icone-file-text2"></span> <span class="item-vertical-texto">Relat&oacute;rio</span></a></li>
						         	<li><a href="?sair=1" class="item-vertical"><span class="icone-exit"></span> <span class="item-vertical-texto">Sair</span></a></li>
						        </ol>
						    </div>
						</div>
					</div>';
				}
				
				?>
					
					<div class="dez colunas">
						
					<?php 


					if ($sessao->getNivelAcesso () == Sessao::NIVEL_SUPER) {
						echo '<section id="navegacao">							
							<!--Tabs-->	
							<ul class="nav nav-tabs">
								<li role="presentation" class="active"><a href="#tab1" data-toggle="tab">Cadastro</a></li>																
							</ul>						

							<div class="tab-content">
								<div class="tab-pane active" id="tab1">';
						UsuarioController::main ( $sessao->getNivelAcesso() );
						VinculoController::main($sessao->getNivelAcesso());
						//Vamos pesq
						echo '	</div>								
							</div>
						</section>';
					} else {
					
						UsuarioController::main($sessao->getNivelAcesso());
					}
					
					?>
					
						

					</div>
				</div>
			</div>
		</div>	
	</body>
</html>