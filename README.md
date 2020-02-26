# TensorDash
<img src="readme_resources/cover_image.jpeg">
TensorDash is an application that lets you remotely monitor your deep learning model's metrics and notifies you when your model training is completed or crashed.

## Why Tensordash?
1. Watch your model train in real-time
2. Remotely get details on the training and validation metrics
3. Get notified when your model has completed trainng or when it has crashed.
4. Get detailed graphs on your model’s metrics.

## Installation ##

### Installing the Python Package ###

`pip install tensor-dash`

### Installing the Android App ###

Install the android app from the play store.<br>
[<img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" height="30%" width="30%">](https://play.google.com/store/apps/details?id=tech.tensordash.tensordash)

## How to use for TensorFlow and Keras ##

1. If you are using the app for the first time, **sign up** by clicking on the "create an account" button.
2. After signing up, **sign in** to your account.
3. In your python code, import Tensordash library

You can use multiple ways to upload your metrics. 

#### a) Specify Model Name, Email address and password 
```python
from tensordash.tensordash import Tensordash
histories = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
```
**OR**
#### b) Specify Model Name and Email address  
```python
from tensordash.tensordash import Tensordash
histories = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
Then, enter the password in the terminal.
```bash
Enter Tensordash Password : ********
```
**OR**
#### c) Specify only Model Name 
```python
from tensordash.tensordash import Tensordash
histories = Tensordash(ModelName = '<YOUR_MODEL_NAME>')
```
Then, enter the email id and password in the terminal.
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```

***In the app, if you have multiple models you would be able to identify your model by YOUR_MODEL_NAME, so this name has to be unique.***

4. Now you can monitor your model values and status using **crash analysis**. Simply use a try-catch block as shown below.

```python
try:
    model.fit(
	X_train, 
	y_train, 
	epochs = epochs, 
	validation_data = validation_data, 
	batch_size = batch_size, 
	callbacks = [histories])

except:
    histories.sendCrash()
```

**OR**

Alternatively, if you do not want to use **crash analysis** then you can just monitor by just adding histories object to callback


```python

model.fit(
	X_train, 
	y_train, 
	epochs = epochs, 
	validation_data = validation_data, 
	batch_size = batch_size, 
	callbacks = [histories])
```


## How to use for Fast.ai ##

1. If you are using the app for the first time, **sign up** by clicking on the "create an account" button.
2. After signing up, **sign in** to your account.
3. In your python code, import Tensordash library
There are multiple ways to Link your Account , The Following are displayed.

#### a) Specify Model Name, Email address and password : 
```python
from tensordash.fastdash import Fastdash
my_cb = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
```
**OR**
#### b) Specify Model Name and Email address : 
```python
from tensordash.fastdash import Fastdash
my_cb = Fastdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
Then, enter the password in the terminal.
```bash
Enter Tensordash Password : ********
```
**OR**
#### c) Specify only Model Name : 
```python
from tensordash.fastdash import Fastdash

learn = cnn_learner(data, models.resnet18, metrics=accuracy)
my_cb = Fastdash(learn, ModelName = '<YOUR_MODEL_NAME>')
```
Then, enter the email id and password in the terminal.
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```


4. Now you can monitor your model values and status using **crash analysis**. Simply use a try-catch block as shown below.

```python
try:
    learn.fit(epochs, learning_rate, callbacks = my_cb)
except:
    my_cb.sendCrash()
```


### OR

Alternatively, if you do not want to use **crash analysis** then you can just monitor by just adding my_cb object to callback


```python

learn.fit(epochs, learning_rate, callbacks = my_cb)
```
