import json
import tensorflow as tf
import time

from firebasedata import SendDataToFirebase

SendData = SendDataToFirebase()
class Tensordash(tf.keras.callbacks.Callback):

    """
    Uses custom callbacks in keras and tf.keras to send model metrics to firebase after every epoch
    """

    def __init__(self, ModelName = 'Sample_model', email = None, password =None):

        self.start_time = time.time()    
        self.ModelName = ModelName
        self.email = email
        self.password = password
        self.epoch_num = 0

        self.key, self.auth_token = SendData.signin(email = self.email, password = self.password) #get key and authentication token for givenn email ID

    def on_train_begin(self, logs = {}):
        """
        Initializes the model on training begining to firebase and updates the status as RUNNING
        """
        self.losses = []
        self.accuracy = []
        self.val_losses = []
        self.val_accuracy = []
        self.num_epochs = []

        SendData.model_init(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        
    def on_epoch_end(self, epoch, logs = {}):
        """
        Sends data to firebase after every epoch
        """
        if(time.time() - self.start_time > 3000):
            #gets authentication token after every 50 minuites
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)

        self.losses.append(logs.get('loss'))
        self.val_losses.append(logs.get('val_loss'))
        if(logs.get('acc') != None):
            self.accuracy.append(logs.get('acc'))
        else:
            self.accuracy.append(logs.get('accuracy'))
        if(logs.get('val_acc') != None):
            self.val_accuracy.append(logs.get('val_acc'))
        else:
            self.val_accuracy.append(logs.get('val_accuracy'))
        self.num_epochs.append(epoch)

        self.loss = float("{0:.6f}".format(self.losses[-1]))

        if self.accuracy[-1] == None:
            self.acc = None
        else:
            self.acc = float("{0:.6f}".format(self.accuracy[-1]))

        if self.val_losses[-1] == None:
            self.val_loss = None
        else:
            self.val_loss = float("{0:.6f}".format(self.val_losses[-1]))

        if self.val_accuracy[-1] == None:
            self.val_acc = None
        else:
            self.val_acc = float("{0:.6f}".format(self.val_accuracy[-1]))

        values = [epoch, self.loss, self.acc, self.val_loss, self.val_acc]
        self.epoch_num = epoch + 1
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = values, ModelName = self.ModelName)

    def on_train_end(self, epoch, logs = {}):
        """
        Updates model status as COMPLETED on trainning end
        """
        if(time.time() - self.start_time > 3000):
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)
            
        SendData.updateCompletedStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)

    def sendCrash(self):
        """
        Updates model status as CRASHED if the model crashes
        """
        if(time.time() - self.start_time > 3000):
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)


class Customdash(object):
    """
    If you are using a custom model with a different framework that does not support callbacks 
    or using numpy to make a model, you can use Customdash to send data.
    Refer examples to see how to use
    """
    def __init__(self, ModelName = 'Sample Model', email = None, password = None):

        self.start_time = time.time()    
        self.ModelName = ModelName
        self.email = email
        self.password = password
        self.epoch = 0

        self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)

    def sendLoss(self, epoch = None, loss = None, acc = None, val_loss = None, val_acc = None, total_epochs = None):

        if(time.time() - self.start_time > 3000):
            #gets authentication token if the time exceeds 50 mins
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password) 
        if(epoch == 0):
            # Initializes the model and updates RUNNING status
            SendData.model_init(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
            SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)

        if(epoch == total_epochs - 1):
            # Updates model status as COMPLETED when the model is trained
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
        """
        Updates model status as CRASHED on model crashing
        """
        if(time.time() - self.start_time > 3000):
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)