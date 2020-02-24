import requests
import json
import getpass

class FirebaseError(Exception):
    pass
class SendDataToFirebase(object):

    def __init__(self, key = None):
        response = None

    def sendMessage(self, key = None, auth_token = None, params = None, ModelName = 'Sample Model'):
        epoch, loss = params
        loss = float("{0:.6f}".format(loss))

        data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + '}'

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

class Torchdash(object):
    def __init__(self, ModelName = 'Sample Model', email = None, password = None):
        if(email == 'None'):
            email = input("Enter Email :")
        if(email != 'None' and password == 'None'):
            password = getpass.getpass("Enter Tensordash Password :")
            
        self.ModelName = ModelName
        self.email = email
        self.password = password

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

    def sendLoss(self, epoch = None, loss = None, total_epochs = None):
        if(epoch == 0):
            SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = [-1, 0], ModelName = self.ModelName)
            SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        if(epoch == total_epochs - 1):
            SendData.updateCompletedStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        params = [epoch, loss]
        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = params, ModelName = self.ModelName)

    def sendCrash(self):
        SendData.crashAnalytics(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)

