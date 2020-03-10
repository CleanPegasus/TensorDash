

import numpy as np
import matplotlib.pyplot as plt
import keras
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense
from keras.optimizers import Adam
from keras.layers import Flatten
from keras.layers import Conv2D
from keras.layers import MaxPooling2D
from keras.layers import Dropout
from keras.models import Model
from keras.utils import to_categorical
from keras.callbacks import LambdaCallback
import random
from tensordash.tensordash import Tensordash

np.random.seed(0)
(X_train, y_train), (X_test, y_test) = mnist.load_data()

num_of_samples = []

num_classes = 10


y_train = to_categorical(y_train, 10)
y_test = to_categorical(y_test, 10)

X_train = X_train/255
X_test = X_test/255


#num_pixels = 784

X_train = X_train.reshape(60000, 28, 28, 1)
X_test = X_test.reshape(10000, 28, 28, 1)



def lenet_model():
    model = Sequential()
    model.add(Conv2D(30, (5,5), input_shape = (28, 28, 1), activation='relu'))
    model.add(MaxPooling2D(pool_size = (2, 2)))
    model.add(Conv2D(15, (3, 3), activation = 'relu'))
    model.add(MaxPooling2D(pool_size = (2,2)))
    model.add(Flatten())
    model.add(Dense(50, activation = 'relu'))
    model.add(Dropout(0.5))
    model.add(Dense(num_classes, activation = 'softmax'))
    model.compile(Adam(lr = 0.01), loss = 'categorical_crossentropy', metrics = ['accuracy'])
    return model

model = lenet_model()


histories = Tensordash(ModelName = '<YOUR_MODEL_NAME>', email = '<YOUR_EMAIL_ID>', password= '<YOUR_PASSWORD>')

try:

    model.fit(X_train, y_train, epochs = 10, validation_split = 0.1, batch_size = 5000, verbose = 1, shuffle = True, callbacks = [histories])

except:

    histories.sendCrash()
