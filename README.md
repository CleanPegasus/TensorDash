# TensorDash
TensorDash is an application that lets you remotely monitor your deep learning model's metrics and notifies you when your model training is completed or crashed.

## Installation

Install the python package "tensordash" on your computer by using

`pip install tensor-dash`

Install the android app from the play store.
> (link to the app)


## How to use

1. If you are using the app for he first time, **sign up** by clicking on the "create an account" button.
2. After signing up, **sign in** to your account.
3. In your python code, import TensorDash library
```python
from tensordash import TensorDash
histories = TensorDash(
	email = '[YOUR_EMAIL_ID]', 
	password = '[YOUR PASSWORD]', 
	ModelName = '[YOUR_MODEL_NAME]')
```
*In the app, if you have multiple models you would be able to identify your model by YOUR_MODEL_NAME, so this name has to be unique.*

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


### OR

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
