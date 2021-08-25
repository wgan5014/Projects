from tensorflow.keras.models import Sequential
#from tensorflow.keras.layers import Dense, Conv2D, MaxPool2D, Flatten, Dropout 
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.optimizers import Adam, SGD
from tensorflow.keras import layers
from tensorflow.keras.applications import MobileNetV2
import os, cv2, numpy as np
from tensorflow.keras.losses import SparseCategoricalCrossentropy as SCCE
import random
from tensorflow.keras.utils import normalize
from matplotlib import pyplot as plt

'''
1. prepare data
2. create CNN and train it
	- create convolutional base
	- add dense layers
	- compile & train() 
	- set epochs, learning rate, optimizer
	- create a list of labels, of which the ls[i] corresponds with the ith label in the directory structure:
		* ls = ['articulated_truck', 'bus'...]
		* ls[0] is a label which corresponds with all the images in "./train/articulated_truck"
		* we then train the model to recognise almost all images in ls[0], and thus, corresponding directory of ./train...
		* to be articulated trucks
	- labels:
		- index i in ls[i] is the classification -- corresponds to a directory
		- each label; class is an integer in [0,10]  -> 11 labels
		
	- evaluate the performance of the model
		* evaluation_classification.py -- from MIO-TCD-Classification-Code dir
	- repeat
	- save the model which had the best accuracy w/out overfitting to 'model.pkl'
	
3. deploy it
	- app.py
	
'''

'''
A function which converts randomly selected images in each 
label directory as a multi-dim numpy array

this is done for ease of data retrieval

This function can potentially pick 2 of the same indices, however we assume the likelihood of 
this is extremely low.

'''

def read_train_and_test_data():
	
	imgdirs = sorted(os.listdir('./train'))

	trainset = []
	testset = []

	for i in range(len(imgdirs)):
		imgnames = os.listdir('./train/{}'.format(imgdirs[i])) # imgnames is all the images in a dir
		for j in range(500):
			img_fname = './train/' + imgdirs[i] + "/" + imgnames[random.randint(0, len(imgnames) - 1)]
			img = foo(img_fname)
			if j < 375:
				trainset.append([img, i])
			else:
				testset.append([img, i])

	# xtest[i] is an image, and the label corresponds to ytrain[i]. (img, label) ==> xtest has images, ytest has labels.
	return (np.array(trainset), np.array(testset))
	
def foo(fname):
	img_arr = cv2.cvtColor(cv2.imread(fname), cv2.COLOR_BGR2RGB)
	return cv2.resize(img_arr, (224,224))

def sharpen(dset, kernel, startidx, endidx):
	for i in range(startidx, endidx):
		dset[i] = cv2.filter2D(dset[i], -1, kernel)
	return dset
	
def augment(dataset):
	datagen = ImageDataGenerator(
		featurewise_center = False,  # input mean to 0 over the dataset
		samplewise_center = False,  # sample mean to 0
		featurewise_std_normalization = False,  # div inputs by std of the dataset
		samplewise_std_normalization = False,  # div each input by its std
		zca_whitening = False,  # ZCA whitening
		rotation_range = 45,  # randomly rotate img in ranges 0-45
		zoom_range = 0.2, # randomly zoom image 
		width_shift_range = 0.1,  # randomly shift img horizontally (fraction of total width)
		height_shift_range = 0.1,  # randomly shift img vertically (fraction of total height)
		horizontal_flip = True,  # randomly flip img -- hor & vert
		vertical_flip = False
	)  # randomly flip imgs
	datagen.fit(dataset)

def train_model(xtrain, ytrain, xtest, ytest):
	bmod = MobileNetV2(
		input_shape=(224,224,3),
		include_top=False,
		weights='imagenet'
	)
	bmod.trainable = False
	opt = Adam(learning_rate = 0.01)
	#opt = SGD(learning_rate=0.01,decay=1e-6,momentum=0.9,nesterov=True)
	model = Sequential(
		[
			bmod,
			layers.GlobalAveragePooling2D(),
			layers.Dropout(0.2),
			layers.Dense(11, activation='softmax')
		]
	)

	model.compile(optimizer=opt, loss=SCCE(from_logits = True), metrics=['accuracy'])

	results = model.fit(xtrain, ytrain, batch_size=26, epochs=11,validation_data=(xtest, ytest))

	model.save("new_adam_classing_model")

	return results

def split(dataset):
	xset = []
	yset = []
	for feature, label in dataset:
		xset.append(feature)
		yset.append(label)
	return (np.array(xset), np.array(yset))



# read and split data
trainingset, testingset = read_train_and_test_data()
xtrain, ytrain = split(trainingset)
xtest, ytest = split(testingset)

# normalize the data
xtrain = normalize(xtrain, axis=1)
xtest = normalize(xtest, axis=1)

# sharpen data
xtrain = sharpen( xtrain, np.array([ [0,-1,0], [-1,5,-1], [0,-1,0] ]), 0, len(xtrain)//2 )
xtest = sharpen( xtest, np.array([ [0,-1,0], [-1,5,-1], [0,-1,0] ]), 0, len(xtest)//2 )

# augment training data
augment(xtrain)

print("Data loaded in, normalized, resized, sharpened, and split.\n Ready for training now.")

try:
	print("Training model now...\n")
	history = train_model(xtrain, ytrain, xtest, ytest)
except Exception as e:
	with open('error.txt','w') as f:
		print(e, file=f)
		print("An error occurred while training/saving the model!")
else:
	print("no error. script finished")
	print("Model has been trained and valiated. The trained model is written in the file 'model.pb'")
'''
### graph performance ###
acc = history.history['accuracy']
val_acc = history.history['val_accuracy']
loss = history.history['loss']
val_loss = history.history['val_loss']

epochs_range = range(11)

plt.figure(figsize=(15, 15))
plt.subplot(2, 2, 1)
plt.plot(epochs_range, acc, label='Training Accuracy')
plt.plot(epochs_range, val_acc, label='Validation Accuracy')
plt.legend(loc='lower right')
plt.title('Training and Validation Accuracy')

plt.subplot(2, 2, 2)
plt.plot(epochs_range, loss, label='Training Loss')
plt.plot(epochs_range, val_loss, label='Validation Loss')
plt.legend(loc='upper right')
plt.title('Training and Validation Loss')
plt.show()
'''





