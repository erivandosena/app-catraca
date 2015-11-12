<?php


class VinculoDAO extends DAO{
	
	/**
	 * 
	 * Pega-se o Id da base do SIG. Antes vamos verificar se existe na base local. 
	 * Se existe pega seu id: $idUsuarioBaseLocal
	 * 
	 * Se n�o existir, pegamos da base do SIG e adicionamos na base local. Capturando id inserido em 
	 * $idUsuarioBaseLocal; 
	 *  
	 * 
	 * Verifica��o de numero de cart�o. 
	 * 
	 * 
	 * Adicionar Vinculo
	 * Vai ser o seguinte, acabamos de adicionar um usuario na base local. Utilizaremos o id inserido. 
	 * Iremos adicionar o vinculo para esse usuario com dados de cartao e vinculo. 
	 * 
	 * 
	 * Mas antes n�o sabemos se o cart�o j� � cadastrado. 
	 * Caso o cart�o seja cadastrado, fazemos um Update para que o tipo seja adequado a este. 
	 * Caso contrario, cadastramos o cart�o. 
	 * Ao cadastrar o cart�o ainda precisamos do ID do cart�o.  
	 * Temos o ID do usuario adicionado e o ID do cart�o adicionado. Fa�amos adi��o de vinculo. 
	 * 
	 * Opa, mas tem algo que deve ser visto. Se o cart�o ja estava cadastrado e tinha vinculo? 
	 * Temos que alterar esse vinculo para que deixe de valer a partir de agora, e ent�o continuar o processo. ]
	 * 
	 * Mais uma coisa, se o usuario j� existia cadastrado e tamb�m tinha cart�o vinculado a ele. 
	 * Algum n�o era avulso? Fa�a o processo de substitui��o de vinculo. Vinculo de cart�o anterior 
	 * � atualizado para que tenha vinculo vencido e o novo vinculo ser� finalmente adicionado. 
	 * 
	 *  Caso vinculo seja avulso n�o se leve em considera��o vinculos do usuario. 
	 *  

	 * @param int $idUsuarioBaseExterna
	 * @param Vinculo $vinculo
	 */
	public function adicionaVinculo($idUsuarioBaseExterna, Vinculo $vinculo){
		
		$idUsuarioBaseExterna = intval($idUsuarioBaseExterna);
		
		
		
	}
	
	
}

?>