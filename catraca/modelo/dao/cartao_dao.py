#!/usr/bin/env python
# -*- coding: latin-1 -*-


from contextlib import closing
from catraca.util import Util
from catraca.modelo.dados.conexao import ConexaoFactory
from catraca.modelo.dados.conexaogenerica import ConexaoGenerica
from catraca.modelo.entidades.cartao import Cartao
from catraca.modelo.dao.tipo_dao import TipoDAO


__author__ = "Erivando Sena"
__copyright__ = "Copyright 2015, Unilab"
__email__ = "erivandoramos@unilab.edu.br"
__status__ = "Prototype" # Prototype | Development | Production


class CartaoDAO(ConexaoGenerica):

    def __init__(self):
        super(CartaoDAO, self).__init__()
        ConexaoGenerica.__init__(self)
        
    def busca(self, *arg):
        obj = Cartao()
        id = None
        for i in arg:
            id = i
        if id:
            sql = "SELECT cart_id, cart_numero, cart_creditos, tipo_id FROM cartao " +\
                "WHERE cart_id = " + str(id)
        elif id is None:
            sql = "SELECT cart_id, cart_numero, cart_creditos, tipo_id FROM cartao ORDER BY cart_id"
        try:
            with closing(self.abre_conexao().cursor()) as cursor:
                cursor.execute(sql)
                if id:
                    dados = cursor.fetchone()
                    if dados is not None:
                        obj.id = dados[0]
                        obj.numero = dados[1]
                        obj.creditos = dados[2]
                        obj.tipo = self.busca_por_tipo(obj)
                        return obj
                    else:
                        return None
                elif id is None:
                    list = cursor.fetchall()
                    if list != []:
                        return list
                    else:
                        return None
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro ao realizar SELECT na tabela cartao.', exc_info=True)
        finally:
            pass

    def busca_por_tipo(self, obj):
        return TipoDAO().busca(obj.id)
        
    def busca_por_numero(self, *arg):
        obj = Cartao()
        id = None
        for i in arg:
            id = i
        if id:
            sql = "SELECT cart_id, cart_numero, cart_creditos, tipo_id FROM cartao " +\
                  "WHERE cart_numero = " + str(id)
        elif id is None:
            sql = "SELECT cart_id, cart_numero, cart_creditos, tipo_id FROM cartao"
        try:
            with closing(self.abre_conexao().cursor()) as cursor:
                cursor.execute(sql)
                if id:
                    dados = cursor.fetchone()
                    if dados is not None:
                        obj.id = dados[0]
                        obj.numero = dados[1]
                        obj.creditos = dados[2]
                        obj.tipo = self.busca_por_tipo(obj)
                        return obj
                    else:
                        return None
                elif id is None:
                    list = cursor.fetchall()
                    if list != []:
                        return list
                    else:
                        return None
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro ao realizar SELECT na tabela cartao.', exc_info=True)
        finally:
            pass
        
        
    def busca_cartao_valido(self, numero, data = None):
        if data is None:
            data = Util().obtem_datahora_postgresql()
        sql = "SELECT cartao.cart_id, cartao.cart_numero, cartao.cart_creditos, "+\
            "tipo.tipo_valor, vinculo.vinc_refeicoes FROM cartao " +\
            "INNER JOIN tipo ON cartao.tipo_id = tipo.tipo_id " +\
            "INNER JOIN vinculo ON vinculo.cart_id = cartao.cart_id " +\
            "WHERE ('"+str(data)+"' BETWEEN vinculo.vinc_inicio AND vinculo.vinc_fim) AND "  +\
            "(cartao.cart_numero = "+str(numero)+")"   
        try:
            with closing(self.abre_conexao().cursor()) as cursor:
                cursor.execute(sql)
                obj = cursor.fetchone()
                if obj:
                    return obj
                else:
                    return None
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro ao realizar SELECT na tabela cartao.', exc_info=True)
        finally:
            pass
        
    def busca_isencao(self, data = None):
        if data is None:
            data = Util().obtem_datahora_postgresql()
        sql = "SELECT isencao.isen_inicio, isencao.isen_fim FROM cartao " +\
        "INNER JOIN isencao ON isencao.cart_id = cartao.cart_id WHERE ('"+str(data)+"' "+\
        "BETWEEN isencao.isen_inicio AND isencao.isen_fim)" 
        try:
            with closing(self.abre_conexao().cursor()) as cursor:
                cursor.execute(sql)
                obj = cursor.fetchone()
                if obj:
                    return obj
                else:
                    return None
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro ao realizar SELECT na tabela cartao.', exc_info=True)
        finally:
            pass
  
    def insere(self, obj):
        try:
            if obj:
                sql = "INSERT INTO cartao("\
                        "cart_id, "\
                        "cart_numero, "\
                        "cart_creditos, "\
                        "tipo_id) VALUES (" +\
                        str(obj.id) + ", " +\
                        str(obj.numero) + ", " +\
                        str(obj.creditos) + ", " +\
                        str(obj.tipo) + ")"
                self.aviso = "Inserido com sucesso!"
                with closing(self.abre_conexao().cursor()) as cursor:
                    cursor.execute(sql)
                    #self.commit()
                    return True
            else:
                self.aviso = "Objeto inexistente!"
                return False
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro realizando INSERT na tabela cartao.', exc_info=True)
#             return False
        finally:
            pass
        
    def atualiza_exclui(self, obj, delete):
        try:
            if obj:
                if delete:
                    sql = "DELETE FROM cartao WHERE cart_id = " + str(obj.id)
                    self.aviso = "Excluido com sucesso!"
                else:
                    sql = "UPDATE cartao SET " +\
                          "cart_numero = " + str(obj.numero) + ", " +\
                          "cart_creditos = " + str(obj.creditos) + ", " +\
                          "tipo_id = " + str(obj.tipo) +\
                          " WHERE "\
                          "cart_id = " + str(obj.id)
                    self.aviso = "Alterado com sucesso!"
                with closing(self.abre_conexao().cursor()) as cursor:
                    cursor.execute(sql)
                    #self.commit()
                    return True
            else:
                self.aviso = "Objeto inexistente!"
                return False
#         except Exception, e:
#             self.aviso = str(e)
#             self.log.logger.error('Erro realizando DELETE/UPDATE na tabela cartao.', exc_info=True)
#             return False
        finally:
            pass
        