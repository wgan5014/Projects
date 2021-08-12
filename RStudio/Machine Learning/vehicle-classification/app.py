from flask import Flask, request, send_file, send_from_directory
from flask.templating import render_template
from skimage import io, transform
import torch
#import cv2
#from tensorflow.keras.models import load_model
#from tensorflow.keras.utils import normalize
import numpy as np
import os
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms, utils
from torch.autograd import Variable
from torch.utils.data.sampler import SubsetRandomSampler
from torch import nn
from model_PyTorch_NeuralNet import ConvNet # import all transform, tensor & pre-processing classes

labels = {
    0:'articulated_truck',
    1:'background',
    2:'bicycle',
    3:'bus',
    4:'car',
    5:'motorcycle',
    6:'non-motorized_vehicle',
    7:'pedestrian',
    8:'pickup_truck',
    9:'single_unit_truck',
    10:'work_van'
}

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = "uploads"

transform = transforms.Compose([transforms.Resize((100,100)), transforms.ToTensor()])
device = torch.device("cpu")
model = ConvNet(11)
PATH = './model.ckpt'
model.load_state_dict(torch.load(PATH)) # load trained parameters
model.eval() # set in eval mode for prediction


@app.route('/')
def home():
    return render_template("index.html")

def predict_img(pil_img, test_transforms, device, model):
	img_tensor = test_transforms(pil_img).float()
	img_tensor = img_tensor.unsqueeze_(0)
	inp = Variable(img_tensor)
	inp = inp.to(device)
	out = model(inp)
	idx = out.data.cpu().numpy().argmax()
	return idx

@app.route('/classify',methods=['GET','POST'])
def classify():
    if request.method == 'POST':
    	f = request.files['entryfile']
    	if f.filename == '':
    		flash('no file supplied')
    	filename = './' + app.config['UPLOAD_FOLDER'] + "/" + f.filename.strip()
    	f.save(filename)
    	img = io.imread(filename) # read image
    	pil_img = transforms.ToPILImage()(img) # convert to PIL image
    	idx = predict_img(pil_img, transform, device, model)
    	
    	os.remove(filename)
    	'''
    	Take in an image file, classify it using model, and return the result to the user
    	'''
    	return render_template('index.html',prediction='''The image you supplied has been classified as a {}'''.format(labels[idx]))

if __name__ == "__main__":
    app.run(debug=True)

