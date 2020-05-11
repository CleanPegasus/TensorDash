import json
import time
from firebase_data import SendDataToFirebase

SendData = SendDataToFirebase()

class Torchdash(object):
    def __init__(self, ModelName = 'Sample Model', email = None, password = None):

        self.start_time = time.time()    
        self.ModelName = ModelName
        self.email = email
        self.password = password

        self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)


    def sendLoss(self, epoch = None, loss = None, acc = None, val_loss = None, val_acc = None, total_epochs = None):

        if(time.time() - self.start_time > 3000):
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)

        if(epoch == 0):
            SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)

        if(epoch == total_epochs - 1):
            SendData.updateCompletedStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        
        loss = float("{0:.6f}".format(loss))
        if acc != None:
            acc = float("{0:.6f}".format(acc))

        if val_loss != None:
            val_loss = float("{0:.6f}".format(loss))

        if val_acc != None:
            val_acc = float("{0:.6f}".format(val_acc))
        
        params = [epoch, loss, acc, val_loss, val_acc]
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = params, ModelName = self.ModelName)

    def sendCrash(self):
        if(time.time() - self.start_time > 3000):
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)