import json
import fastai
from fastai.torch_core import Any, Tensor, MetricsList, ifnone
from fastai.basic_train import LearnerCallback, Learner
import time


from firebasedata import SendDataToFirebase
 
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

