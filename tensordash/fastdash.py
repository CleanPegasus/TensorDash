import json
import fastai
from fastai.torch_core import Any, Tensor, MetricsList, ifnone
from fastai.basic_train import LearnerCallback, Learner
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

class Fastdash(LearnerCallback):
    """
    Uses callbacks to in torchdash to send data to firebasee
    """
    def __init__(self, learn:Learner, filename: str = 'history', append: bool = False, ModelName = 'Sample_model', email = 'None', password ='None'):

        super().__init__(learn)
        self.start_time = time.time()

        self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)

    def on_train_begin(self, **kwargs: Any) -> None:
        """
        Initializes the model on training begining to firebase and updates the status as RUNNING
        """
        SendData.model_init(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        SendData.updateRunningStatus(key = self.key, auth_token = self.auth_token, ModelName = self.ModelName)
        
    def on_epoch_end(self, epoch: int, smooth_loss: Tensor, last_metrics: MetricsList, **kwargs: Any) -> bool:
        """
        Sends data to firebase after every epoch
        """
        if(time.time() - self.start_time > 3000):
            #gets authentication token after every 50 minuites
            self.start_time = time.time()
            self.key, self.auth_token = SendData.signin(email = self.email, password = self.password)
        
        last_metrics = ifnone(last_metrics, [])
        self.stats = [str(stat) if isinstance(stat, int) else '#na#' if stat is None else f'{stat:.6f}'
                 for name, stat in zip(self.learn.recorder.names, [epoch, smooth_loss] + last_metrics)]

        SendData.sendMessage(key = self.key, auth_token = self.auth_token, params = self.stats, ModelName = self.ModelName)


    def on_train_end(self, **kwargs: Any) -> None:
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

