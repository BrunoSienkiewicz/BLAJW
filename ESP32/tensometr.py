from machine import Pin
from hx711 import hx711
from time import sleep
class Tensometr(hx711):
    def __init__(self, pin_out_nr, pin_in_nr):
        super().__init__(pin_out_nr, pin_in_nr)
        self.offset = 0
    
    def tare(self):
        self.offset = self.read()

    def raw_weight(self):
        return self.read() - self.offset

if __name__=="__main__":
    scales = Tensometr(pin_out_nr=16, pin_in_nr=4)

    while True: # tu sygnał 'koniec przepisu'
        while input() != 'press':    # tu input od guzika
            pass
        scales.tare()
        while input() != 'stop':     # tu sygnał bth
            print(scales.raw_weight())
            sleep(2)    # krócej
