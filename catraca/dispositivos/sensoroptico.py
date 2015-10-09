#!/usr/bin/env python
# -*- coding: latin-1 -*-


import subprocess
import threading
from multiprocessing import Process
from threading import Thread
from time import sleep
from catraca.logs import Logs
from catraca.pinos import PinoControle
from catraca.dispositivos.solenoide import Solenoide
from catraca.util import Util


__author__ = "Erivando Sena" 
__copyright__ = "Copyright 2015, Unilab" 
__email__ = "erivandoramos@unilab.edu.br" 
__status__ = "Prototype"  # Prototype | Development | Production 


class SensorOptico(object):

    log = Logs()
    util = Util()
    rpi = PinoControle()
    sensor_1 = rpi.ler(6)['gpio']
    sensor_2 = rpi.ler(13)['gpio']
    solenoide = Solenoide()
    tempo_decorrido = 0
    tempo_decorrente = 0
    finaliza_giro = False
    

    def __init__(self):
        super(SensorOptico, self).__init__()

    def ler_sensor(self, sensor):
        if sensor == 1:
            return self.rpi.estado(self.sensor_1)
        elif sensor == 2:
            return self.rpi.estado(self.sensor_2)
        
    def registra_giro(self, tempo, catraca):
        codigo_giro_completo = ''
        giro = None
        self.tempo_decorrente = 0
        try:
            ##############################################################
            ## INICIA CRONOMETRO REGRESSIVO PARA O GIRO EM MILISSEGUNDOS
            ##############################################################
            while self.tempo_decorrente < tempo:
                #print self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6)
                if self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6) == False:
                    break
                #self.tempo_decorrente += 1.6
#                 print tempo_decorrente/1000
            #for segundo in range(tempo, -1, -1):
                #self.tempo_decorrido =  tempo/1000 - segundo/1000
                self.tempo_decorrido =  self.tempo_decorrente /1000
                self.log.logger.debug(str(self.tempo_decorrido) + ' seg. restantes para expirar o tempo de giro.')
                ##############################################################
                ## CATRACA SEM MOVIMENTO DE GIROS HORARIO OU ANTIHORARIO
                ##############################################################
                if self.obtem_codigo_sensores() == '00':
                    self.log.logger.debug('Catraca em repouso, codigo sensores: '+ self.obtem_codigo_sensores())
                ##############################################################
                ## INICIANDO VERIFICA SE HOUVE ALGUM GIRO HORARIO OU ANTIHORARIO
                ##############################################################
                elif self.obtem_codigo_sensores() == '10' or self.obtem_codigo_sensores() == '01':# or self.obtem_codigo_sensores() == '11':
                    ##############################################################
                    ## CAPTURA O TIPO DE GIRO EXECUTADO
                    ##############################################################
                    if giro is None:
                        giro = self.obtem_direcao_giro()
                    ##############################################################
                    ## VERIFICA SE O GIRO EXECUTADO CONVEM COM O HABILIRADO
                    ##############################################################
                    if self.obtem_giro_iniciado(giro) == catraca.operacao:
                        self.log.logger.info('Girou a catraca no sentido '+giro)
                        ##############################################################
                        ## VERIFICA SE O GIRO FOI HORARIO OU ANTIHORARIO
                        ##############################################################
                        while self.obtem_codigo_sensores() == '10' or self.obtem_codigo_sensores() == '01':
                            self.log.logger.debug('Continuou o giro '+giro+', codigo sensores: '+ self.obtem_codigo_sensores())
#                             tempo_decorrente += 1.6
#                             if self.tempo_decorrido == tempo/1000:
#                                 sself.log.logger.info('Tempo expirado em '+ str(self.tempo_decorrido)+' segundo(s) sem giro.')
#                                 elf.finaliza_giro = False
#                                 return self.finaliza_giro
                            #print self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6)
                            if not self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6):
                                break
                        #cronometro_beep = 0
                        ##############################################################
                        ## VERIFICA SE A CATRACA SE ENCONTRA EM MEIO GIRO
                        ##############################################################
                        self.util.cronometro = 0
                        while self.obtem_codigo_sensores() == '11':
                            self.log.logger.debug('No meio do giro '+giro+', codigo sensores: '+ self.obtem_codigo_sensores())
#                             tempo_decorrente += 1.6
#                             if self.tempo_decorrido == tempo/1000:
#                                 self.log.logger.info('Tempo expirado em '+ str(self.tempo_decorrido)+' segundo(s) sem giro.')
#                                 self.finaliza_giro = False
#                                 return self.finaliza_giro
                            #print self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6)
                            if self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6) == False:
                                break
                            ##############################################################
                            ## ALERTA CASO A CATRACA PARE NO MEIO DO GIRO MAIS DE 10 SEG
                            ##############################################################
                            self.util.emite_beep(860, 1, 1, 10) #10 seg.
                        codigo_giro_completo = self.obtem_codigo_sensores()
                        ##############################################################
                        ## FINALIZANDO VERIFICA SE O GIRO FOI HORARIO OU ANTIHORARIO
                        ##############################################################
                        while self.obtem_codigo_sensores() == '10' or self.obtem_codigo_sensores() == '01':
                            self.log.logger.debug('Finalizando o giro '+giro+', codigo sensores: '+ self.obtem_codigo_sensores())
#                             tempo_decorrente += 1.6
#                             if self.tempo_decorrido == tempo/1000:
#                                 self.log.logger.info('Tempo expirado em '+ str(self.tempo_decorrido)+' segundo(s) sem giro.')
#                                 self.finaliza_giro = False
#                                 return self.finaliza_giro
                            #print self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6)
                            if self.cronometro_tempo(self.tempo_decorrido, tempo, 1.6) == False:
                                break
                        codigo_giro_completo += self.obtem_codigo_sensores()
                        ##############################################################
                        ## VERIFICA SE O GIRO FOI COMPLETADO CORRETAMENTE
                        ##############################################################
                        if giro == 'antihorario':
                            if codigo_giro_completo == '1000': 
                                self.log.logger.info('Giro '+giro+' finalizado em '+ str(self.tempo_decorrido)+' segundo(s) no codigo: '+ self.obtem_codigo_sensores())
                                self.finaliza_giro = True
                                return self.finaliza_giro
                        if giro == 'horario':
                            if codigo_giro_completo == '0100':
                                self.log.logger.info('Giro '+giro+' finalizado em '+ str(self.tempo_decorrido)+' segundo(s) no codigo: '+ self.obtem_codigo_sensores())
                                self.finaliza_giro = True
                                return self.finaliza_giro
                    ##############################################################
                    ## FINALIZA GIRO INCORRETO
                    ##############################################################
                    else:
                        self.log.logger.info('Nao girou '+giro+ ', operacao cancelada.')
                        self.finaliza_giro = False
                        return self.finaliza_giro
            ##############################################################
            ## FINALIZA GIRO COM TEMPO EXPIRADO
            ##############################################################
            #if self.tempo_decorrido == tempo/1000:
            else:
                self.log.logger.info('Tempo expirado em '+ str(self.tempo_decorrido)+' segundo(s) sem giro.')
                elf.finaliza_giro = False
                return elf.finaliza_giro
        except SystemExit, KeyboardInterrupt:
            raise
        except Exception:
            self.log.logger.error('Erro lendo sensores opticos.', exc_info=True)
        finally:
            return self.finaliza_giro

    def obtem_codigo_sensores(self):
        return str(self.ler_sensor(1)) + '' + str(self.ler_sensor(2))
    
    def obtem_direcao_giro(self):
        opcoes = {
                   '10' : 'horario',
                   '01' : 'antihorario',
                   '11' : 'incompleto',
                   '00' : 'repouso',
        }
        return opcoes.get(self.obtem_codigo_sensores(), None)
    
    def obtem_giro_iniciado(self, modo_operacao):
        opcoes = {
                   'horario' : 1,
                   'antihorario' : 2,
        }
        return opcoes.get(modo_operacao, None)
    
    def obtem_final_giro(self,tempo_decorrido, tempo):
        if tempo_decorrido == tempo/1000:
            self.log.logger.info('Tempo expirado em '+ str(tempo_decorrido)+' segundo(s) sem giro.')
            self.finaliza_giro = False
            return self.finaliza_giro
        
    def cronometro_tempo(self, tempo_decorrido, tempo, milissegundos):
        self.tempo_decorrente += milissegundos
        print self.tempo_decorrente
        if tempo_decorrido == tempo/1000:
            self.log.logger.info('Tempo expirado em '+ str(tempo_decorrido)+' segundo(s) sem giro.')
            self.finaliza_giro = False
            return self.finaliza_giro
        else:
            return True
        