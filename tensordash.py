from firebase.firebase import FirebaseApplication
import keras

class FirebaseError(Exception):
    pass

class SendDataToFirebase(object):

    def __init__(self, key = None):

        try:

            firebase = FirebaseApplication('https://cofeeshop-tensorflow.firebaseio.com/')

        except:

            raise FirebaseError("Could Not connect to firebase")

    def sendMessage(self, key = None, params = None, ModelName = 'Sample Model'):

        epoch, loss, acc = params

        firebase = FirebaseApplication('https://cofeeshop-tensorflow.firebaseio.com/')

        result = firebase.put(key, '{}/Epoch {}'.format(ModelName, epoch + 1) , {'Loss' : loss, 'Accuracy' : acc})

            


#result = firebase.put(sample_key, 'model/Epoch {}'.format(epoch) , {'Loss' : 0.2, 'Accuracy' : 0.70})

SendData = SendDataToFirebase()


class Tensordash(keras.callbacks.Callback):

    def __init__(self, key = 'None', epoch_num = 1, ModelName = 'Sample_model'):

        
        self.ModelName = ModelName
        self.key = key
        self.epoch_num = epoch_num
    
    def on_train_begin(self, logs = {}):

        self.losses = []
        self.accuracy = []
        self.val_losses = []
        self.val_accuracy = []
        self.num_epochs = []

    def on_epoch_end(self, epoch, logs = {}):

        self.losses.append(logs.get('loss'))
        self.accuracy.append(logs.get('accuracy'))
        self.val_losses.append(logs.get('val_loss'))
        self.val_accuracy.append(logs.get('val_accuracy'))
        self.num_epochs.append(epoch)

        if(epoch % self.epoch_num == 0):

            self.loss = float("{0:.6f}".format(self.losses[-1]))

            if self.accuracy[-1] == None:
                self.acc = "Not Specified"
            else:
                self.acc = float("{0:.6f}".format(self.accuracy[-1]))

            if self.val_losses[-1] == None:
                self.val_loss = "Not Specified"
            else:
                self.val_loss = float("{0:.6f}".format(self.val_losses[-1]))

            if self.val_accuracy[-1] == None:
                self.val_acc = "Not Specified"
            else:
                self.val_acc = float("{0:.6f}".format(self.val_accuracy[-1]))
    
            values = [epoch, self.loss, self.acc]

            SendData.sendMessage(key = self.key, params = values, ModelName = self.ModelName)