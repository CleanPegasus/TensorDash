# How to use

1. If you are using the app for he first time, **sign up** by clicking on the "create an account" button.
2. After signing up, **sign in** to your account.

## Keras

3. In your python code, import Tensordash library
There are multiple ways to Link your Account , The Following are displayed.

#### Specify only Model Name : 

```python
from tensordash.tensordash import Tensordash
histories = Tensordash(ModelName = '<YOUR_MODEL_NAME>')
```
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```
#### Specify Model Name and Email address : 

```python
from tensordash.tensordash import Tensordash
histories = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
```bash
Enter Tensordash Password : ********
```

#### Specify Model Name, Email address and password : 

```python
from tensordash.tensordash import Tensordash
histories = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
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

## Tensorflow

3. In your python code, import Tensordash library

*** To use Tensordash on pytorch, you have to pass the metrics as parameters to the function manually.***
<br>
***The function takes:<br>
1. loss<br>
2. epoch<br>
3. total_epochs<br>
4. accuracy(optional)<br>
5. val_loss(optional)<br>
6. val_acc(optional),  as the parameters ***

```python
from tensordash.tensordash import Customdash
```

There are multiple ways to Link your Account , The Following are displayed.

#### Specify only Model Name : 

```python
histories = Customdash(ModelName = '<YOUR_MODEL_NAME>')
```
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```
#### Specify Model Name and Email address : 

```python
histories = Customdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
```bash
Enter Tensordash Password : ********
```

#### Specify Model Name, Email address and password : 

```python
histories = Customdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
```

***In the app, if you have multiple models you would be able to identify your model by YOUR_MODEL_NAME, so this name has to be unique.***

4. Now you can monitor your model values and status using **crash analysis**. Simply use a try-catch block as shown below.

```python
try:
    
	for epoch in range(num_epochs):
		epoch_loss_avg = tf.keras.metrics.Mean()
		epoch_accuracy = tf.keras.metrics.SparseCategoricalAccuracy()

		for x, y in train_dataset:

			loss_value, grads = grad(model, x, y)
			optimizer.apply_gradients(zip(grads, model.trainable_variables))

			epoch_loss_avg(loss_value)
			epoch_accuracy(y, model(x, training=True))

		train_loss_results.append(epoch_loss_avg.result())
		train_accuracy_results.append(epoch_accuracy.result())

		histories.sendLoss(loss = epoch_loss_avg.result(), accuracy = epoch_accuracy.result(), epoch = epoch, total_epochs = epochs)

except:
    histories.sendCrash()

```


### OR

Alternatively, if you do not want to use **crash analysis** then you can just monitor by just adding histories object to callback


```python
for epoch in range(num_epochs):
	epoch_loss_avg = tf.keras.metrics.Mean()
	epoch_accuracy = tf.keras.metrics.SparseCategoricalAccuracy()

	for x, y in train_dataset:

		loss_value, grads = grad(model, x, y)
		optimizer.apply_gradients(zip(grads, model.trainable_variables))

		epoch_loss_avg(loss_value)
		epoch_accuracy(y, model(x, training=True))

	train_loss_results.append(epoch_loss_avg.result())
	train_accuracy_results.append(epoch_accuracy.result())

	histories.sendLoss(loss = epoch_loss_avg.result(), accuracy = epoch_accuracy.result(), epoch = epoch, total_epochs = epochs)

```

## Pytorch

3. In your python code, import Tensordash library

```python
from tensordash.torchdash import Torchdash
```

*** To use Tensordash on pytorch, you have to pass the metrics as parameters to the function manually.***
<br>
***The function takes:<br>
1. loss<br>
2. epoch<br>
3. total_epochs<br>
4. accuracy(optional)<br>
5. val_loss(optional)<br>
6. val_acc(optional),  as the parameters ***


There are multiple ways to Link your Account , The Following are displayed.

#### Specify only Model Name : 

```python
histories = Torchdash(ModelName = '<YOUR_MODEL_NAME>')
```
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```
#### Specify Model Name and Email address : 

```python
histories = Torchdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
```bash
Enter Tensordash Password : ********
```

#### Specify Model Name, Email address and password : 

```python
histories = Torchdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
```

***In the app, if you have multiple models you would be able to identify your model by YOUR_MODEL_NAME, so this name has to be unique.***

4. Now you can monitor your model values and status using **crash analysis**. Simply use a try-catch block as shown below.

```python
try:
    for epoch in range(epochs):
        losses = []
        for data in trainset:
            X, y = data
            net.zero_grad()
            output = net(X.view(data_shape))
            loss = F.nll_loss(output, y)
            loss.backward()
            optimizer.step()
        losses = np.asarray(losses)
        histories.sendLoss(loss = np.mean(losses), epoch = epoch, total_epochs = epochs)

except:
    histories.sendCrash()

```


### OR

Alternatively, if you do not want to use **crash analysis** then you can just monitor by just adding histories object to callback


```python
for epoch in range(epochs):
	losses = []
	for data in trainset:
		X, y = data
		net.zero_grad()
		output = net(X.view(-1,784))
		loss = F.nll_loss(output, y)
		loss.backward()
		optimizer.step()
	losses = np.asarray(losses)
	histories.sendLoss(loss = np.mean(losses), epoch = epoch, total_epochs = epochs)

```









## Fast.ai

3. In your python code, import Tensordash library
There are multiple ways to Link your Account , The Following are displayed.

#### Specify only Model Name : 

```python
from tensordash.fastdash import Fastdash

learn = cnn_learner(data, models.resnet18, metrics=accuracy)
my_cb = Fastdash(learn, ModelName = '<YOUR_MODEL_NAME>')
```
```bash
Enter Email : ...........
Enter Tensordash Password : ********
```
#### Specify Model Name and Email address : 

```python
from tensordash.fastdash import Fastdash
my_cb = Fastdash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>')
```
```bash
Enter Tensordash Password : ********
```

#### Specify Model Name, Email address and password : 

```python
from tensordash.fastdash import Fastdash
my_cb = Tensordash(
	ModelName = '<YOUR_MODEL_NAME>',
	email = '<YOUR_EMAIL_ID>', 
	password = '<YOUR PASSWORD>')
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
