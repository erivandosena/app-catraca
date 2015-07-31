#!/usr/bin/env python
# -*- coding: latin-1 -*-

import time
import threading
from threading import Thread
from catraca.logs import Logs
from catraca.dispositivos.aviso import Aviso
#from catraca.dispositivos.threadcatraca import ThreadCatraca
from catraca.dispositivos.leitorcartao import LeitorCartao


__author__ = "Erivando Sena" 
__copyright__ = "Copyright 2015, Unilab" 
__email__ = "erivandoramos@unilab.edu.br" 
__status__ = "Prototype" # Prototype | Development | Production 


class Mensagem(threading.Thread):
    
    log = Logs()
    
    #threadLock = threading.Lock()
    #threads = []
    
    def __init__(self):
        #super(Mensagem, self).__init__()
        threading.Thread.__init__(self)
        #self.threadID = 2
        self.name = 'Thread Mensagem.'
        #self.counter = 2
        self.iterations = 0
        self.daemon = True  # OK para o principal sair, mesmo se a inst�ncia ainda est� em execu��o
        self.paused = True  # come�ar uma pausa
        self.state = threading.Condition()

    def run(self):
        print "%s Rodando... " % self.name
        #self.threadLock.acquire()
        #self.print_time(self.name, self.counter, 2)
        # Free lock to release next thread
        #self.threadLock.release()
        
        #self.exibe_mensagens(True)
        
#         if LeitorCartao().ler():
#             self.lock.acquire()
#         else:
        self.reinicia() # cancelar pausa auto 
        
        while True:
            with self.state:
                if self.paused:
                    self.state.wait() # bloquear at� ser notificado
            # Fazer coisas
            self.exibe_mensagens(True)
            time.sleep(.1)
            self.iterations += 1
                
    def reinicia(self):
        with self.state:
            self.paused = False
            self.state.notify()  # desbloquear auto se espera

    def pausa(self):
        with self.state:
            self.paused = True  # fazer auto bloco e esperar
            
    def para(self):
        self.stopped = True
                  
#     def print_time(self,threadName,delay,counter):
#         while counter:
#         #while True:
#             time.sleep(delay)
#             print "%s: %s" % (threadName, time.ctime(time.time()))
#             counter -= 1

    def exibe_mensagens(self, valor):
        try:
            while valor:
                aviso = Aviso()
                aviso.exibir_datahora()
                aviso.exibir_local()
                aviso.exibir_desenvolvedor()
                #self.lock.acquire()
                #self.join()
                
        except SystemExit, KeyboardInterrupt:
            raise
        except Exception:
            self.log.logger.error('Erro exibindo mensagem no display', exc_info=True)
        finally:
            pass
    
    def inicia_mensagem(self):
        return self.exibe_mensagens(True)
        
    def finaliza_mensagem(self):
        return self.exibe_mensagens(False)
            