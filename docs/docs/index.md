# TensorDash

<img src = 'img/cover_image.jpeg'>

TensorDash is an application that lets you remotely monitor your deep learning model's metrics and notifies you when your model training is completed or crashed.

## Why Tensordash?

1. Watch your model train in real-time
2. Remotely get details on the training and validation metrics
3. Get notified when your model has completed trainng or when it has crashed.
4. Get detailed graphs on your model’s metrics.

## Installation

### Installing the Python Package

### Installing the Python Package ###

There are two ways to install tensordash:

- **Install tensordash from PyPI (recommended):**

Note: These installation steps assume that you are on a Linux or Mac environment.
If you are on Windows, you will need to remove `sudo` to run the commands below.

```sh
sudo pip install tensor-dash
```

If you are using a virtualenv, you may want to avoid using sudo:

```sh
pip install tensor-dash
```

- **Alternatively: install tensordash from the GitHub source:**

First, clone TensorDash using `git`:

```sh
git clone https://github.com/CleanPegasus/TensorDash.git
```

 Then, `cd` to the TensorDash folder and run the install command:
```sh
cd TensorDash
sudo python setup.py install
```

### Installing the Android App

Install the android app from the play store.<br>
[<img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" height="30%" width="30%">](https://play.google.com/store/apps/details?id=tech.tensordash.tensordash)
