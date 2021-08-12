import os
import torch
import pandas as pd
from skimage import io, transform
import numpy as np
import matplotlib.pyplot as plt
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms, utils
from torch.autograd import Variable
from torch.utils.data.sampler import SubsetRandomSampler
from torch import nn

labels = {
    'articulated_truck': 0,
    'background': 1,
    'bicycle': 2,
    'bus': 3,
    'car': 4,
    'motorcycle': 5,
    'non-motorized_vehicle': 6,
    'pedestrian': 7,
    'pickup_truck': 8,
    'single_unit_truck': 9,
    'work_van': 10
}

validation_split = 0.2
shuffle_dataset = True
random_seed = 42
num_epochs = 2
num_classes = 11
batch_size = 100
learning_rate = 0.001


class Rescale(object):
    def __init__(self, output_size):
        assert isinstance(output_size, (int, tuple))
        self.output_size = output_size
    '''
    The object can be called like a method
    Parameters in this case, is sample, a dictionary of images and their respective labels
    E.g. 
    x = Rescale((1,1))
    x({'img':io.imread(<path>), 'label':420})
    '''
    def __call__(self, sample):
        image = sample['image']
        ht, w = image.shape[:2]
        # if is a list, check through the edges & identify which ones are smaller/larger, and adjust them
        if isinstance(self.output_size, int):
            if ht > w:
                new_ht, new_w = self.output_size * ht / w, self.output_size
            else:
                new_ht, new_w = self.output_size, self.output_size * w / ht
        else:
            new_ht, new_w = self.output_size

        new_ht, new_w = int(new_ht), int(new_w)
        # resize images
        img = transform.resize(image, (new_ht, new_w))
        # return resized image vectors and labels
        return {'image': img, 'label': sample['label']}

'''
convert PIL.image or numpy n-dim arr. to a tensor
from H*W*Channel --> Channels*W*H
'''
class ToTensor(object):

    def __call__(self, sample):
        image = sample['image']

        # swap color axis because
        # numpy image: H x W x C
        # torch image: C X H X W
        image = image.transpose((2, 0, 1))
        return {'image': torch.from_numpy(image), 'label': sample['label']}

'''
The DS class will grab the labels from the gt_train.csv file,
and the corresponding image

Then, it reads each training img. and adds it to dictionary
'sample', which contains key: image, and value: label (img. class)


'''
class DS(Dataset):
    def __init__(self, csv_file, root_dir, transform = None):
        self.transform = transform
        self.csv_ds = pd.read_csv(csv_file, dtype='str')
        self.root_dir = root_dir
    
    def __len__(self):
        return len(self.csv_ds)
    
    def __getitem__(self, idx):
        img_name = str(self.csv_ds.iloc[idx][0])
        img_path = os.path.join(self.root_dir, 'train',str(self.csv_ds.iloc[idx][1]), str(self.csv_ds.iloc[idx][0]) + '.jpg')
        image = io.imread(img_path)
        label = labels[self.csv_ds.iloc[idx][1]]
        sample = {'image': image, 'label': label}
        
        if self.transform:
            sample = self.transform(sample)
        return sample
'''
Creates a convolution nnetwrk IAW pytorch API
'''
class ConvNet(nn.Module):
    def __init__(self, num_classes=10):
        super(ConvNet, self).__init__() # extends the nn.Module class -- use `super` kword
        
        # layer 1: convolutional -> batch normalization (stdize inputs) -> activate node -> down-sample img. & dimensionality
        # stride mods the amount of movemt over the img. 
        # here, stride=1 indicates moving pixel-by-pixel over the image, instead of skipping pixels
        # 3 in-channels to corresp. to 3 colour channels
        self.layer1 = nn.Sequential(
            nn.Conv2d(3, 16, kernel_size=5, stride=1, padding=2),
            nn.BatchNorm2d(16),
            nn.ReLU(),
            nn.MaxPool2d(kernel_size=2, stride=2))

	# layer 2: layer 1, except with differing in/out channels
	# 16 in-channels to correspond to the 16 out-channels from the conv. layer in layer1
        self.layer2 = nn.Sequential(
            nn.Conv2d(16, 32, kernel_size=5, stride=1, padding=2),
            nn.BatchNorm2d(32),
            nn.ReLU(),
            nn.MaxPool2d(kernel_size=2, stride=2))
        
        self.fc = nn.Linear(20000, num_classes+1)

    # forward propagation layer:
    # maps an input tensor to an output tensor
    def forward(self, x):
        out = self.layer1(x)
        out = self.layer2(out)
        out = out.reshape(out.size(0), -1)
        out = self.fc(out)
        return out
        
# rest is commented out, so we're able to import classes w/out executing the testing code
'''
tds = DS(csv_file='./gt_train.csv', root_dir='./',
                                    transform = transforms.Compose([Rescale((100, 100)), ToTensor()]) )
dataset_size = len(tds)

indices = list(range(dataset_size))

split = int(np.floor(validation_split * dataset_size))

if shuffle_dataset:
    np.random.seed(random_seed)
    np.random.shuffle(indices)

train_indices, val_indices = indices[split:], indices[:split]


train_sampler = SubsetRandomSampler(train_indices)
validation_sampler = SubsetRandomSampler(val_indices)

train_loader = torch.utils.data.DataLoader(tds, batch_size=batch_size, 
                                           sampler=train_sampler)
validation_loader = torch.utils.data.DataLoader(tds, batch_size=batch_size,

                                                
                                                sampler=validation_sampler)

model = ConvNet(num_classes)
# Loss and optimizer
criterion = nn.CrossEntropyLoss()
optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)

# Train the model
total_step = len(train_loader)
for epoch in range(num_epochs):

    print(len(train_loader))
    for i, x in enumerate(train_loader):
        images = Variable(x['image'])
        labels = Variable(x['label'])
        
        outputs = model(images.float())
        
        loss = criterion(outputs, labels)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()
            
        if (i+1) % 100 == 0:
            print('Epoch [{}/{}], Step [{}/{}], Loss: {:.4f}' 
                   .format(epoch+1, num_epochs, i+1, total_step, loss.item()))

# save the model
torch.save(model.state_dict(), 'model.ckpt')

'''
