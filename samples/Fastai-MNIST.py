import fastai
from fastai.vision import *
from tensordash.fastdash import Fastdash

mnist = untar_data(URLs.MNIST_TINY)
tfms = get_transforms(do_flip=False)

data = (ImageList.from_folder(mnist)
        .split_by_folder()          
        .label_from_folder()
        .transform(tfms, size=32)
        .databunch()
        .normalize(imagenet_stats))

learn = cnn_learner(data, models.resnet18, metrics=accuracy)
my_cb = Fastdash(learn, ModelName = 'Fastai Model', email = 'newid@sample.com', password = '12345678')
try:
    learn.fit(10,1e-2, callbacks = my_cb)
except:
    my_cb.sendCrash()
