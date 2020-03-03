import tensorflow as tf
from tensordash.tensordash import Customdash
import numpy as np

tf.keras.backend.set_floatx('float64')

print(tf.__version__)

mnist = tf.keras.datasets.mnist

(x_train, y_train),(x_test, y_test) = mnist.load_data()
x_train, x_test = x_train / 255.0, x_test / 255.0

def create_model():
  return tf.keras.models.Sequential([
    tf.keras.layers.Flatten(input_shape=(28, 28)),
    tf.keras.layers.Dense(512, activation='relu'),
    tf.keras.layers.Dropout(0.2),
    tf.keras.layers.Dense(10, activation='softmax')
  ])


train_dataset = tf.data.Dataset.from_tensor_slices((x_train, y_train))
test_dataset = tf.data.Dataset.from_tensor_slices((x_test, y_test))

train_dataset = train_dataset.shuffle(60000).batch(64)
test_dataset = test_dataset.batch(64)


loss_object = tf.keras.losses.SparseCategoricalCrossentropy()
optimizer = tf.keras.optimizers.Adam()

train_loss = tf.keras.metrics.Mean('train_loss', dtype=tf.float64)
train_accuracy = tf.keras.metrics.SparseCategoricalAccuracy('train_accuracy')
test_loss = tf.keras.metrics.Mean('test_loss', dtype=tf.float64)
test_accuracy = tf.keras.metrics.SparseCategoricalAccuracy('test_accuracy')

def train_step(model, optimizer, x_train, y_train):
  with tf.GradientTape() as tape:
    predictions = model(x_train, training=True)
    loss = loss_object(y_train, predictions)
  grads = tape.gradient(loss, model.trainable_variables)
  optimizer.apply_gradients(zip(grads, model.trainable_variables))

  loss = train_loss(loss)
  accuracy = train_accuracy(y_train, predictions)


def test_step(model, x_test, y_test):
  predictions = model(x_test)
  loss = loss_object(y_test, predictions)

  val_loss = test_loss(loss)
  val_acc = test_accuracy(y_test, predictions)


model = create_model()

histories = Customdash(ModelName = 'Tensorflow Model', email = '<YOUR_EMAIL_ID>', password = '<YOUR_PASSWORD>')

num_epochs = 5

try:

  for epoch in range(num_epochs):
    for (x_train, y_train) in train_dataset:
      train_step(model, optimizer, x_train, y_train)
      

    for (x_test, y_test) in test_dataset:
      test_step(model, x_test, y_test)

    histories.sendLoss(epoch=epoch, loss=train_loss.result().numpy(), acc=train_accuracy.result().numpy(),
                      val_loss = test_loss.result().numpy(), val_acc = test_accuracy.result().numpy(), total_epochs = num_epochs)

    train_loss.reset_states()
    test_loss.reset_states()
    train_accuracy.reset_states()
    test_accuracy.reset_states()

except:
    histories.sendCrash()