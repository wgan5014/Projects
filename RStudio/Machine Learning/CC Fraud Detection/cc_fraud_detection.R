library(caret)
library(ranger)
library(data.table)
library(caTools)
library(pROC)
library(class)
library(rpart)
library(rpart.plot)
library(neuralnet)

cc_data <- read.csv("./creditcard.csv") # dataset
cc_data$Amount = scale(cc_data$Amount) # normalize
newData = cc_data[,-c(1)] # "trim" off tail

# set seed, make training & testing data
set.seed(123)
sample = sample.split(newData$Class, SplitRatio = 0.8) # 80% training, 20% testing
data_training = subset(newData, sample == TRUE) # take only values where the sample vector is true
data_test = subset(newData, sample == FALSE) # convser of above

# decision tree
tree_model <- rpart(Class~., cc_data, method = "class") # 'Class' variable is dependent on all other variables
predicted_val <- predict(tree_model, cc_data, type = "class")
probability <- predict(tree_model, cc_data, type = "prob") # plot probability per class
rpart.plot(tree_model) # plot decision tree

# neural network
ANN_model = neuralnet(Class~., data_training, linear.output = FALSE)
plot(ANN_model) # plot neural net - V1->V28 due to PCA [reduce dimensionality of data. This was done to protect sensitive information]
predANN=compute(ANN_model, test_data)
resultANN=predANN$net.result
resultANN=ifelse(resultANN > 0.5,1,0)

# logistic regression
log_mod = glm(Class~., data_test, family = binomial())
plot(log_mod) # plot logistic model residuals, quant-quant plot
lr.predict <- predict(log_mod,data_training, probability = TRUE) # predict training
auc.gbm = roc(data_test$Class, lr.predict, plot = TRUE, col = "blue") # roc-curve to assess performance
