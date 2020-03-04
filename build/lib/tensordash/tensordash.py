import requests
import json
import keras
import getpass

class FirebaseError(Exception):
    pass
class SendDataToFirebase(object):

    def __init__(self, key = None):
        response = None

    def sendMessage(self, key = None, auth_token = None, params = None, ModelName = 'Sample Model'):
        epoch, loss, acc, val_loss, val_acc = params

        if(acc == None and val_loss == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + '}'
        elif(acc == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Validation Loss":' + str(val_loss) + '}'
        elif(val_loss == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Accuracy" :' + str(acc) + '}'
        else:
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Accuracy" :' + str(acc) + ', "Validation Loss":' + str(val_loss) + ', "Validation Accuracy" :' + str(val_acc) + '}'

        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json?'.format(key, ModelName), params = auth_token, data=data)

    def updateRunningStatus(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        data = '{"Status" : "RUNNING"}'
        response = requests.put('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)

        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Running"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

    def updateCompletedStatus(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        data = '{"Status" : "COMPLETED"}'
        response = requests.patch('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)


        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Completed"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

    def crashAnalytics(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        data = '{"Status" : "CRASHED"}'
        response = requests.patch('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)


        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Crashed"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

SendData = SendDataToFirebase()
class Tensordash(keras.callbacks.Callback):

    def __init__(self, ModelName = 'Sample_model', email = None, password =None):
        # Get Email and Password If Not Entered Initially
        if(email == None):
            email = input("Enter Email :")
        if(email != None and password == None):
            password = getpass.getpass("Enter Tensordash Password :")
            
        self.ModelName = ModelName
        self.email = email
        self.password = password
        self.epoch = 0

        headers = {'Content-Type': 'application/json',}
        params = (('key', 'AIzaSyDU4zqFpa92Jf64nYdgzT8u2oJfENn-2f8'),)
        val = {
            "email" : self.email,
            "password": self.password,
            "returnSecureToken": "true"
        }
        data = str(val)

        try:
            response = requests.post('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword', headers=headers, params=params, data=data)
            output = response.json()
            self.key = output['localId']
            self.token = output['idToken']

            self.auth_token = (('auth', self.token),)

        except:
            raise FirebaseError("Authentication Failed. Kindly create an account on the companion app")

    def on_train_begin(self, logs = {}):
        self.losses = []
        self.accuracy = []
        self.val_losses = []
        self.val_accuracy = []
        self.num_epochs = []

        SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = (-1, 0, 0, 0, 0), ModelName = self.ModelName)
        
    def on_epoch_end(self, epoch, logs = {}):

        self.losses.append(logs.get('loss'))
        if(logs.get('acc') != None):
            self.accuracy.append(logs.get('acc'))
        else:
            self.accuracy.append(logs.get('accuracy'))
        self.val_losses.append(logs.get('val_loss'))
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
        self.epoch = epoch
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = values, ModelName = self.ModelName)

    def on_train_end(self, epoch, logs = {}):
        SendData.updateCompletedStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)

    def sendCrash(self):
        if(self.epoch == 0):
            SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = [-1, 0, 0, 0, 0], ModelName = self.ModelName)
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)


class Customdash(object):
    def __init__(self, ModelName = 'Sample Model', email = None, password = None):
        if(email == None):
            email = input("Enter Email :")
        if(email != None and password == None):
            password = getpass.getpass("Enter Tensordash Password :")
            
        self.ModelName = ModelName
        self.email = email
        self.password = password

        self.epoch = 0

        headers = {'Content-Type': 'application/json',}
        params = (('key', 'AIzaSyDU4zqFpa92Jf64nYdgzT8u2oJfENn-2f8'),)
        val = {
            "email" : self.email,
            "password": self.password,
            "returnSecureToken": "true"
        }
        data = str(val)

        try:
            response = requests.post('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword', headers=headers, params=params, data=data)
            output = response.json()
            self.key = output['localId']
            self.token = output['idToken']

            self.auth_token = (('auth', self.token),)

        except:
            raise FirebaseError("Authentication Failed. Kindly create an account on the companion app")

    def sendLoss(self, epoch = None, loss = None, acc = None, val_loss = None, val_acc = None, total_epochs = None):

        if(epoch == 0):
            SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
            SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = [-1, 0, 0, 0, 0], ModelName = self.ModelName)

        if(epoch == total_epochs - 1):
            SendData.updateCompletedStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        
        loss = float("{0:.6f}".format(loss))
        if acc != None:
            acc = float("{0:.6f}".format(acc))

        if val_loss != None:
            val_loss = float("{0:.6f}".format(loss))

        if val_acc != None:
            val_acc = float("{0:.6f}".format(val_acc))

        self.epoch = epoch
        params = [epoch, loss, acc, val_loss, val_acc]
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = params, ModelName = self.ModelName)

    def sendCrash(self):
        if(self.epoch == 0):
            SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = [-1, 0, 0, 0, 0], ModelName = self.ModelName)
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)