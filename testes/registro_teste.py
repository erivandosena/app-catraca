#!/usr/bin/env python
# -*- coding: latin-1 -*-


import os
import socket
import locale
import datetime
from catraca.dao.registro import Registro
from catraca.dao.registrodao import RegistroDAO
from catraca.dao.cartaodao import CartaoDAO
from catraca.dao.catracadao import CatracaDAO
from catraca.dao.turnodao import TurnoDAO
from catraca.dao.finalidadedao import FinalidadeDAO
from time import sleep
from catraca.dispositivos.sensoroptico import SensorOptico


__author__ = "Erivando Sena"
__copyright__ = "Copyright 2015, Unilab"
__email__ = "erivandoramos@unilab.edu.br"
__status__ = "Prototype" # Prototype | Development | Production



socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
socket.connect(('unilab.edu.br', 0))
IP = '%s' % (socket.getsockname()[0])

locale.setlocale(locale.LC_ALL, 'pt_BR.UTF-8')

def main():
    print 'Iniciando os testes tabela registro...'
    
    registro = Registro()
    registro_dao = RegistroDAO()
    cartao_dao = CartaoDAO()
    turno_dao = TurnoDAO()
    catraca_dao = CatracaDAO()

    registro.data = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")
    registro.giro = 1
    registro.valor = 0.01
    registro.cartao = cartao_dao.busca(1)
    registro.turno = turno_dao.busca(2)
    
#     if registro_dao.conexao_status():
#          print "conexao_status: "+ str(registro_dao.conexao_status())

# 
#     if not registro_dao.mantem(registro,False):
#         raise Exception(registro_dao.aviso)
#     else:
#         print registro_dao.aviso


#     print registro.cartao.perfil.tipo.nome
#     print registro.cartao.perfil.nome
#     print registro.cartao.numero
#     print locale.currency(registro.valor).format()

    #registro = registro_dao.busca(6)

    """
    registro_dao.mantem(registro,False)
    print registro_dao.aviso
    
    print registro.cartao


    catraca = CatracaDAO().busca_por_ip(IP)
    turnos = catraca.turno

    p1_hora_inicio = datetime.datetime.strptime('00:00:00','%H:%M:%S').time()
    p1_hora_fim = datetime.datetime.strptime('00:00:00','%H:%M:%S').time()

    conta_turnos = 0
    hora_atual = datetime.datetime.strptime(datetime.datetime.now().strftime('%H:%M:%S'),'%H:%M:%S').time()
    for turno in turnos:
        conta_turnos += 1
        print conta_turnos
        p1_hora_inicio = datetime.datetime.strptime(str(turno[1]),'%H:%M:%S').time()
        p1_hora_fim = datetime.datetime.strptime(str(turno[2]),'%H:%M:%S').time()

        if ((hora_atual >= p1_hora_inicio) and (hora_atual <= p1_hora_fim)):
            finalidade_turno = FinalidadeDAO().busca(turno[3]).nome
            print "Turno encontrado entre:" +str(p1_hora_inicio)+" "+str(p1_hora_fim)+ " -> " + finalidade_turno
            break
    if not (((hora_atual >= p1_hora_inicio) and (hora_atual <= p1_hora_fim)) or ((hora_atual >= p1_hora_inicio) and (hora_atual <= p1_hora_fim))):
        print "Fora do horario!"
        return None
    elif ((hora_atual >= p1_hora_inicio) and (hora_atual <= p1_hora_fim)):
        print "Turno liberado entre:" +str(p1_hora_inicio)+" "+str(p1_hora_fim)
        print "faco coisas apartir daqui!"
    if True:
        print "outras coisas..."

            
    
    """  
    
    sensor_optico = SensorOptico()
    
    def aguarda_giro(tempo):
        finaliza_tempo = True
        contador = tempo/1000
        while True:
            if not sensor_optico.registra_giro(tempo):
                print "girou!"
            else:
                print "nao girou"
#             contador = contador -1
#             print str(contador)
#             #print str(self.tempo_decorrido)+" de "+str(tempo/1000)
#             #self.tempo_decorrido = (tempo/1000)-(segundo/1000)
#             #3decorrido = (contador/1000)-(tempo/1000)
#             #print str(decorrido)
#             if contador == 0:
#                 print 'Finalizou no tempo decorrido de: '+ str(decorrido)
#                 retfinaliza_tempo = False
            
            
    aguarda_giro(30000)
    
    
#     print 62 * "="
#     print '======################### RELATORIO ####################======'
#     print 62 * "="
#     
#     
#     for registro in registro_dao.busca():
#         cartao = cartao_dao.busca(registro[4])
#         print str(registro[1]) +" "\
#         + str(registro[2]) +" "\
#         + str(registro[3]) +" "\
#         + str(cartao.numero) +" "\
#         + str(cartao.perfil.tipo.nome) +" "\
#         + str(TurnoDAO().busca(registro[5]))
#         print 62 * "-"
#     print 62 * "="

#     id = 3995148318
#         
#     def lista_cartoes():
# 
#         try:
#             lista = cartao_dao.busca()
#             lista.sort()
#             return lista
#         except SystemExit, KeyboardInterrupt:
#             raise
#         except Exception:
#             self.log.logger.error('Erro consultando ID.', exc_info=True)
#         finally:
#             pass
#         
#     cartoes = lista_cartoes()
#     
#     def pesquisa_id(lista, id):
#         resultado = None
#         for cartao in lista:
#             if cartao[1] == id:
#                 resultado = cartao
#                 return resultado
#                 break
#         return resultado
#       
#     cartao = pesquisa_id(cartoes, id)
#     if cartao is not None:
#         print cartao
#         print cartao[1]
#         print cartao[2]
#     else:
#         print 'n�o encontrado!'
#         
#     databd = cartao[3]
#     data_ultimo_acesso = datetime.datetime(
#         day=databd.day,
#         month=databd.month,
#         year=databd.year, 
#     ).strptime(databd.strftime('%d/%m/%Y'),'%d/%m/%Y')
#     
#     print data_ultimo_acesso.day
#     
#     def obtem_turno():
#         catraca = catraca_dao.busca_por_ip('10.5.2.253')
#         turnos = catraca.turnos
#         turnos.sort()
#         for turno in turnos:
#             hora_atual = datetime.datetime.strptime(datetime.datetime.now().strftime('%H:%M:%S'),'%H:%M:%S').time()
#             hora_inicio = datetime.datetime.strptime(str(turno[1]),'%H:%M:%S').time()
#             hora_fim = datetime.datetime.strptime(str(turno[2]),'%H:%M:%S').time()
#             if ((hora_atual >= hora_inicio) and (hora_atual <= hora_fim)):
#                 return turno
#                 break
#         return None
#         
#     print obtem_turno()
#     
#     print datetime.datetime.now()
#     print datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
#     print datetime.datetime.strptime(datetime.datetime.now().strftime('%H:%M:%S'),'%H:%M:%S').time()
#     print datetime.datetime.now().strftime("%H:%M:%S")
#     
#     def teste(boleano):
#         status = False
#         try:
#             if boleano:            
#                 print 'testando...'
#                 status = True
#                 #/0
#             else:
#                 print 'nao testou!'
#         except SystemExit, KeyboardInterrupt:
#             raise
#         except Exception, e:
#             status = False
#             print 'Erro: '+ str(e) 
#         finally:
#             print 'teste finalizado!'   
#         return status
# 
#     print teste(True)
        


        