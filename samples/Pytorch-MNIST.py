from tensordash.torchdash import Torchdash

import torch
import torchvision
from torchvision import transforms, datasets
import torch.nn as nn
import torch.nn.functional as F
import numpy as np

train = datasets.MNIST('', train=True, download=True,
                       transform=transforms.Compose([
                           transforms.ToTensor()
                       ]))

test = datasets.MNIST('', train=False, download=True,
                       transform=transforms.Compose([
                           transforms.ToTensor()
                       ]))


trainset = torch.utils.data.DataLoader(train, batch_size=10, shuffle=True)
testset = torch.utils.data.DataLoader(test, batch_size=10, shuffle=False)


class Net(nn.Module):
    def __init__(self):
        super().__init__()
        self.fc1 = nn.Linear(28*28, 64)
        self.fc2 = nn.Linear(64, 64)
        self.fc3 = nn.Linear(64, 64)
        self.fc4 = nn.Linear(64, 10)

    def forward(self, x):
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = F.relu(self.fc3(x))
        x = self.fc4(x)
        return F.log_softmax(x, dim=1)

net = Net()

import torch.optim as optim

loss_function = nn.CrossEntropyLoss()
optimizer = optim.Adam(net.parameters(), lr=0.001)

my_cb = Torchdash(ModelName='Pytorch sample Model', email = 'l777arunkumar@gmail.com', password= 'grand design')

epochs = 10


try:
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
        my_cb.sendLoss(loss = np.mean(losses), epoch = epoch, total_epochs = epochs)

except:
    my_cb.sendCrash()
