<?php

/**
 * Classe controller do MVC que serve para controlar Unidades AcadÍmicas.  
 * @author jefponte
 *
 */
class UnidadeController{
	
	
	public static function main(){
		
		$controller = new UnidadeController();
		$controller->listagem();
		$controller->cadastro();
		$controller->delete();
		
		
		
	}
	public static function mainDetalhe(){
		$controller = new UnidadeController();
		
		$controller->detalhe();
		
		
	
	
	
	}
	public function detalhe(Unidade $unidade = null){

		
	}
	/**
	 * @var PDO
	 */
	private $dao;

	/**
	 * @var UnidadeView
	 */
	private $view;
	public function UnidadeController(){
		$this->dao = new UnidadeDAO();
		$this->view = new UnidadeView();
	}
	
	
	
	public function listagem(){
		
		$lista = $this->dao->retornaLista();
		$this->view->mostraLista($lista);
		
		
	}
	public function cadastro(){
		$this->view->mostraFormulario();
		
		if(isset($_POST['unid_nome']))
			if($_POST['unid_nome'] != null && $_POST['unid_nome'] != "")
			{
				$nome = $_POST['unid_nome'];
				$unidade = new Unidade();
				$unidade->setNome($nome);
				if($this->dao->inserirUnidade($unidade))
					$this->view->cadastroSucesso();
				else
					$this->view->cadastroFracasso();
				echo '<meta http-equiv="refresh" content="2; url=/interface/index.php">';
					
			}
	}
	
	public function delete(){

		if(isset($_GET['delete_unidade'])){
			if(isset($_GET['unid_id'])){
				if(is_int(intval($_GET['unid_id']))){
					$id = intval($_GET['unid_id']);
					$unidade = new Unidade();
					$unidade->setId($id);
		
					if($this->dao->deletarUnidade($unidade))
						$this->view->deleteSucesso();
					else
						$this->view->deleteFracasso();
		
				}
			}
			echo '<meta http-equiv="refresh" content="2; url=/interface/index.php">';
		
		}
	}

	
	
	
}



?>