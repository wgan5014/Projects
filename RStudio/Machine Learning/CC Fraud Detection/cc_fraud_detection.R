library(caret)
library(randomForest)
library(data.table) # supports subset, group, update, join ops for datasets imported as tables
library(caTools)
library(pROC) # for ROC curve
library(class)
library(dplyr)

cc_data <- read.csv("./creditcard.csv") # put dataset here
cc_data = cc_data %>%
  mutate(
    Amount = scale(Amount)
  )
# no missing values so that's usually good
sum(is.na(cc_data))
# no time-values are -ve
sum(cc_data$Time < 0)

# drop the dependent variable
# examine collinearity btwn independent variables
cor_mat = cc_data %>%
  select(-Class) %>%
  as.matrix() %>%
  cor()
# checking collinearity
# exclude the 1's b/c that occurs when comparing cor with X(i) & X(i)
sum(cor_mat > 0.5 & cor_mat < 1)
# we have 0 independent variables which are strongly correlated w/e.o.

# examine the head of the data
head(cc_data)

# remove the time-column by indexing
newData = cc_data[,-c(1)]

# we see that the data is skewed towards the not-fraud occurrences
table(cc_data$Class)

# each of the columns V1, V2...are principal components.
# 'time' is the amt. of time elapsed since the first transaction
names(cc_data)

set.seed(123)
sample = sample.split(newData$Class, SplitRatio = 0.75) # training:test 75:25
data_training = subset(newData, sample == TRUE)
data_test = subset(newData, sample == FALSE)


# logistic regression
# Class vector regress onto remaining columns, binomial() for 1-0 fit; fraud/not-fraud
# even tho classes are skewed, this won't affect logistic regression as separation hasn't occurred
# i.e. not class = 1 for all linear combination of predictors.
# Also from before, there's no multicollinearity btwn predictors. Independence is assumed
# using the caret package:
# set 5 CVs
# include out of bag perf
log_mod = train(
  as.factor(Class)~.,
  data=cc_data,
  family = "binomial",
  method="glm",
  trControl = trainControl(
    method="cv",
    number=5,
    verboseIter=F
  )
)
plot(log_mod)

log_mod$results

lr.predict <- predict(log_mod,data=data_test, probability = TRUE) 

auc.gbm = roc(
	data_test$Class, 
	lr.predict, 
	plot = TRUE, 
	col = "blue"
) # ROC curve to determine performance. ROC = receiving operating characteristic
# Ideally, this should have 0 false-postiive rate, and 100 true-positive-rate

# decision tree
# classification using tree-model
# use 10 CVs --> out-of-bag perf
tree_model <- train(
  as.factor(Class)~.,
  data=cc_data,
  method="rpart",
  trControl = trainControl(
    method="cv",
    number=10,
    verboseIter=F
  )
)

tree_model
tree_model$results

# predict the probability of each given value resulting in 1/0: fraud/not-fraud
probability <- predict(tree_model, cc_data, type = 'prob')

# k nearest neighbours
# even though the classes are skewed, this won't affect knn
# this makes it an ideal classification method
# this might nuke the computer lol
fitCtrl = trainControl(
	method="repeatedcv",
	number = 5,
	repeats = 100,
	verboseIter=F
)
knn = train(
	as.factor(cc_data$Class)~.,
	data=cc_data,
	method="knn",
	trControl=fitCtrl
)
knn
confusionMatrix(knn)

# neural network
# library(neuralnet) # package
# train neural networks, act.fct not used so linear.output = FALSE
# act.fct used to smooth out covariates/neurons & their weights
ANN_model = neuralnet(Class~.,data_training,linear.output=FALSE)
plot(ANN_model)

# test_data is covariate - test the neural network
# Artificial Neural Network; ANN model is nnwork obj. used, data_test is covariate
# every neuron is a model producing output from data_training set
predANN = compute(ANN_model,data_test)

# resultANN is the matrix holding the net.result matrix in predANN
resultANN = predANN$net.result

# reassign resultANN to be the ifelse classification based on matrix values
# values are 1-0, values in net.result matrix above 0.5 = 1, rest = 0
resultANN = ifelse(resultANN>0.5,1,0)
resultANN


