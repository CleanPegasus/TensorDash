# TensorDash
TensorDash is an application that lets you remotely monitor your deep learning model's metrics and notifies you when your model training is completed or crashed.

## Installation

Install the python package "tensordash" on your computer by using
> $ **pip install tensordash**

Install the android app from the play store.
> (link to the app)

## How to use

1. If you are using the app for he first time, **sign up** by clicking on the "create an account" button.
2. After signing up, **sign in** to your account.
3. In your python code, **from tensordash import TensorDash**.
4. Add this line to your python code.

> histories = Tensordash(email = '[YOUR_EMAIL_ID]', password = '[YOUR PASSWORD], ModelName = '[YOUR_MODEL_NAME]')

5. While training your model, add the histories object to the callbacks.


> model.fit(X_train, y_train, epochs = epochs, validation_data =                             validation_data, batch_size = batch_size, callbacks = [histories])


6. If you need **crash analysis**, add the model in an exception handling.

```python
try:
    model.fit(X_train, y_train, epochs = epochs, validation_data                         = validation_data, batch_size = batch_size, callbacks = [histories])

except:
    histories.sendCrash()
```
