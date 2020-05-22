import json
import time
import requests
import getpass

class FirebaseError(Exception):
    pass
class SendDataToFirebase(object):
    """
    Upload data to firebase realtime database using requests
    Uploads model metrics and notification data
    """

    def __init__(self, key = None):
        response = None

    def signin(self, email = None, password = None):
        """
        Sign in to service account using email ID and password and returns key and authentication token
        """

        if(email == None):
            email = input("Enter Email: ")
        if(email != None and password == None):
            password = getpass.getpass("Enter Tensordash Password: ")
            
        headers = {'Content-Type': 'application/json',}
        params = (('key', 'AIzaSyDU4zqFpa92Jf64nYdgzT8u2oJfENn-2f8'),)
        val = {
            "email" : email,
            "password": password,
            "returnSecureToken": "true"
        }
        data = str(val)

        try:
            response = requests.post('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword', headers=headers, params=params, data=data)
            output = response.json()
            key = output['localId']
            token = output['idToken']

            auth_token = (('auth', token),)

        except:
            raise FirebaseError("Authentication Failed. Kindly create an account on the companion app")

        return key, auth_token

    def sendMessage(self, key = None, auth_token = None, params = None, ModelName = 'Sample Model'):
        """
        Sends model metrics to firebase
        """
        epoch, loss, acc, val_loss, val_acc = params

        if(acc == None and val_loss == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + '}'
        elif(acc == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Validation Loss":' + str(val_loss) + '}'
        elif(val_loss == None):
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Accuracy" :' + str(acc) + '}'
        else:
            data = '{"Epoch":' +  str(epoch+1) + ', "Loss" :' + str(loss) + ', "Accuracy" :' + str(acc) + ', "Validation Loss":' + str(val_loss) + ', "Validation Accuracy" :' + str(val_acc) + '}'

        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data=data)

    def model_init(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        """
        Initializes the model on firebase
        """
        data = '{' + ModelName + ':' +  '"null"' + '}'
        response = requests.put('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}.json'.format(key), params = auth_token, data = data)

    def updateRunningStatus(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        """
        Updates the model status to RUNNING
        """
        data = '{"Status" : "RUNNING"}'
        response = requests.put('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)

        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Running"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

    def updateCompletedStatus(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        """
        Updates Model status to COMPLETED
        """
        data = '{"Status" : "COMPLETED"}'
        response = requests.patch('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)

        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Completed"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

    def crashAnalytics(self, key = None, auth_token = None, ModelName = 'Sample Model'):
        """
        Updates model status to CRASHED
        """
        data = '{"Status" : "CRASHED"}'
        response = requests.patch('https://cofeeshop-tensorflow.firebaseio.com/user_data/{}/{}.json'.format(key, ModelName), params = auth_token, data = data)

        notif_data = '{"Key":' + '"' + str(key) + '"' + ', "Status" : "Crashed"}'
        response = requests.post('https://cofeeshop-tensorflow.firebaseio.com/notification.json', params = auth_token, data = notif_data)

SendData = SendDataToFirebase()

class Torchdash(object):
    """
    Sends model metrics to firebase after every epoch
    """
    def __init__(self, ModelName = 'Sample Model', email = None, password = None):
        """
        Get key and auth_token for the given email ID
        """
        self.start_time = time.time()    
        self.ModelName = ModelName
        self.email = email
        self.password = password

        self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)


    def sendLoss(self, epoch = None, loss = None, acc = None, val_loss = None, val_acc = None, total_epochs = None):
        """
        Sends the specified metrics to firebase
        """        
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