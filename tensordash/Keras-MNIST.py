

import numpy as np
import matplotlib.pyplot as plt
import tensorflow.keras
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.layers import Flatten
from tensorflow.keras.layers import Conv2D
from tensorflow.keras.layers import MaxPooling2D
from tensorflow.keras.layers import Dropout
from tensorflow.keras.models import Model
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.callbacks import LambdaCallback
import random
from tensordash.tensordash import Tensordash



np.random.seed(0)


(X_train, y_train), (X_test, y_test) = mnist.load_data()


print(X_train.shape)
print(X_test.shape)
print(y_test.shape)

assert(X_train.shape[0] == y_train.shape[0]), "The number of images is not equal to the number of labels."
assert(X_test.shape[0] == y_test.shape[0]), "The number of images is not equal to the number of labels."
assert(X_train.shape[1:] == (28, 28)), "The dimensions of the images are not 28*28."
assert(X_test.shape[1:] == (28, 28)), "The dimensions of the images are not 28*28."


num_of_samples = []

cols = 5
num_classes = 10


y_train = to_categorical(y_train, 10)
y_test = to_categorical(y_test, 10)

print(X_train.shape)


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
    # model.add(Dense(500, activation = 'relu'))
    model.add(Dropout(0.5))
    model.add(Dense(num_classes, activation = 'softmax'))
    model.compile(Adam(lr = 0.01), loss = 'categorical_crossentropy', metrics = ['accuracy'])
    return model

model = lenet_model()


histories = Tensordash(ModelName = 'New Test Model', email = 'l777arunkumar@gmail.com', password = 'grand design')

try:

    model.fit(X_train, y_train, epochs = 10, validation_split = 0.1, batch_size = 5000, verbose = 1, shuffle = True, callbacks = [histories])

except:

    histories.sendCrash()
