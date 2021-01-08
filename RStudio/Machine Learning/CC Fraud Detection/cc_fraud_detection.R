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
cc_data$Amount = scale(cc_data$Amount)
newData = cc_data[,-c(1)]

# set seed, make training & testing data
set.seed(123)
sample = sample.split(newData$Class, SplitRatio = 0.8)
data_training = subset(newData, sample == TRUE)
data_test = subset(newData, sample == FALSE)

# decision tree
tree_model <- rpart(Class~., cc_data, method = "class")
predicted_val <- predict(tree_model, cc_data, type = "class")
probability <- predict(tree_model, cc_data, type = "prob")
rpart.plot(tree_model)

# k nearest neighbours
nn3 <- knn(Class ~ ., data_training, data_test, k = 3)
table(data_test[,"Class"], nn3)

# neural network
ANN_model = neuralnet(Class~., data_training, linear.output = FALSE)
plot(ANN_model)
predANN=compute(ANN_model, test_data)
resultANN=predANN$net.result
resultANN=ifelse(resultANN > 0.5,1,0)

# logistic regression
log_mod = glm(Class~., data_test, family = binomial())
plot(log_mod)
lr.predict <- predict(log_mod,data_training, probability = TRUE)
auc.gbm = roc(data_test$Class, lr.predict, plot = TRUE, col = "blue")
