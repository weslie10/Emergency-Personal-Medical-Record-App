# -*- coding: utf-8 -*-
"""In Care Out Care Classification-DL.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1KyvGZP63Q25_zHOPfPVuIpK8rsu9wBHk
"""

!python --version

# Installing rectified adam

!pip install keras-rectified-adam
from keras_radam import RAdam

import pandas as pd 
import numpy as np 
import matplotlib.pyplot as plt 
import seaborn as sns
import tensorflow as tf

import random
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler, Normalizer, MinMaxScaler, RobustScaler
from sklearn.model_selection import RandomizedSearchCV, GridSearchCV
from sklearn.metrics import accuracy_score, classification_report, f1_score, confusion_matrix, precision_score, recall_score, f1_score

from keras.models import Sequential
from keras.layers import Dense
from keras.callbacks import ModelCheckpoint
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import cross_val_score, cross_val_predict
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import StratifiedKFold
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline

import pickle

# To ignore unwanted warnings
import warnings
warnings.filterwarnings('ignore')

# for styling
plt.style.use('seaborn-whitegrid')

"""# **Data Exploration**
**Upload the data**


"""

data = pd.read_csv('/content/data-ori.csv')
data.head(10)

"""**Feature exploration**"""

# Checking features
print(f"Dataset mengandung {data.shape[0]} baris dan {data.shape[1]} kolom")

num_features = [feat for feat in data if data[feat].dtype != object]
cat_features = [feat for feat in data if data[feat].dtype == object]

print(f"Jumlah fitur : {len(num_features+cat_features)}")
print(f"Jumlah fitur numerik : {len(num_features)}")
print(f"Jumlah fitur kategori : {len(cat_features)}\n")

data[num_features].describe()

# Checking empty data
data.isnull().sum().sort_values(ascending=False)

# # Checking outliers in data
# def find_outliers(x):
#   q1 = np.percentile(x,25)
#   q3 = np.percentile(x,75)
#   iqr = q3-q1
#   floor = q1 - 1.5*iqr
#   ceiling = q3 + 1.5*iqr
#   outliers_indices = list(x.index[(x < floor)|(x > ceiling)])
#   outliers_values = list(x[outliers_indices])

#   return outliers_indices, outliers_values

# data_ex = data.drop('SOURCE', axis=1)

# for (columnName, columnData) in (data_ex.iteritems()):
#   outliers_indices, outliers_values = find_outliers(data[columnName])
#   print(columnName)
#   print(np.sort(outliers_values))

"""#**Preprocessing Data**"""

def preprocess(df):
    df = df.copy()
    
    # Binary encoding 
    df['SEX'] = [0 if x == 'F' else 1 for x in df['SEX']]
    df['SOURCE'] = [1 if x == 'out' else 0 for x in df['SOURCE']]
    #df['SEX']= df.SEX.replace({'F':0,'M':1}, inplace=True)
    #df['SOURCE']= df.SOURCE.replace({'out':0,'in':1}, inplace=True) 

    # Dividing data
    y = df['SOURCE']
    X = df.drop('SOURCE', axis=1)

    # Checking numbers of the data 
    print(X.value_counts())
    print(y.value_counts())
    
    X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=0.6, shuffle=True, random_state=42)
    
    # Scaling data
    scaler = StandardScaler()
    scaler.fit(X_train)
    X_train = pd.DataFrame(scaler.transform(X_train), index=X_train.index, columns=X_train.columns)
    X_test = pd.DataFrame(scaler.transform(X_test), index=X_test.index, columns=X_test.columns) 
    
    # # Normalizing data
    # normalizer = Normalizer().fit(X_train)
    # X_train = pd.DataFrame(normalizer.transform(X_train), index=X_train.index, columns=X_train.columns)
    # X_test = pd.DataFrame(normalizer.transform(X_test), index=X_test.index, columns=X_test.columns)  

    # # Scaling data using maxminscaler
    # scaler = MinMaxScaler(feature_range=(0,1))
    # scaler.fit(X_train)
    # X_train = pd.DataFrame(scaler.transform(X_train), index=X_train.index, columns=X_train.columns)
    # X_test = pd.DataFrame(scaler.transform(X_test), index=X_test.index, columns=X_test.columns) 

    return X_train, X_test, y_train, y_test

X_train, X_test, y_train, y_test = preprocess(data)

y_train.value_counts()
# for y in y_train:
#   if y == 1:
#     print('true')
#   print('false')

print(X_train.shape)
print(X_test.shape)

# # Dropping less correlated features 
# X_train.drop(['MCH', 'MCHC','MCV'], axis=1, inplace=True)
# X_test.drop(['MCH', 'MCHC','MCV'], axis=1, inplace=True)

print(X_train.head(5))

"""#**Define and Training the Model**"""

# Define baseline model
def model_baseline():
  model = Sequential()
  model.add(Dense(10, input_dim=10, activation='linear'))
  model.add(Dense(5, activation='relu'))
  model.add(Dense(1, activation='sigmoid'))

  model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
  return model

# # Hyperparameter tuning using GridSearch
# optimizers = ['adam', 'RAdam']
# epoches = [50, 100, 150]
# batches = [2, 3, 5]
# learn_rate = [0.31, 0.33, 0.35, 0.37]
# momentum = [1.2, 1.23, 1.25, 1.27]
# activation = ['softmax', 'softplus', 'softsign', 'relu', 'tanh', 'sigmoid', 'hard_sigmoid', 'linear']
# parameter_grid = dict(activation=activation)

# model = KerasClassifier(build_fn=model_baseline, verbose=2)
# grid = GridSearchCV(estimator=model, param_grid=parameter_grid, n_jobs=-1, cv=3)
# grid_result = grid.fit(X_train,y_train)

# print("Best: %f using %s" % (grid_result.best_score_, grid_result.best_params_))
# means = grid_result.cv_results_['mean_test_score']
# stds = grid_result.cv_results_['std_test_score']
# params = grid_result.cv_results_['params']
# for mean, stdev, param in zip(means, stds, params):
# 	print("%f (%f) with: %r" % (mean, stdev, param))

# # Data training using pipeline
# estimators = []
# #estimators.append(('standardize', StandardScaler()))
# estimators.append(('mlp', KerasClassifier(build_fn=model_baseline, epochs=150, batch_size=2, verbose=2)))
# pipeline = Pipeline(estimators)
# kfold = StratifiedKFold(n_splits=5, shuffle=True)
# results = cross_val_score(pipeline, X_train, y_train, cv=kfold)
# print('\n')
# print("Standardized: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))

# Checkpoint definition
filepath="weights-improvement-{epoch:02d}-{val_accuracy:.2f}.hdf5"
checkpoint = ModelCheckpoint(filepath, monitor='val_accuracy', verbose=1, save_best_only=True, mode='max')
callbacks_list = [checkpoint]

# Fitting model to data
model = model_baseline()
history = model.fit(X_train, y_train, validation_split=0.33, epochs=150, batch_size=2, callbacks=callbacks_list, verbose=2)

# # Fitting model to data
# pipeline.fit(X_train, y_train)

# # Predicting data
# y_pred = pipeline.predict(X_test)

# Convert the model
final_model = tf.keras.models.load_model('/content/weights-improvement-95-0.74.h5')
converter = tf.lite.TFLiteConverter.from_keras_model(final_model)
tflite_model = converter.convert()

# Save the model to tflite
with open('final_model.tflite', 'wb') as f:
  f.write(tflite_model)

"""#**Evaluating Performance of the Model**"""

# Graph visualization
loss_train = history.history['loss']
loss_val = history.history['val_loss']
acc_train = history.history['accuracy']
epochs = range(0,150)
plt.plot(epochs, loss_train, 'g', label='Training loss')
plt.plot(epochs, loss_val, 'r', label='Validation loss')
plt.plot(epochs, acc_train, 'b', label='Training accuracy')
plt.title('Acc and loss training')
plt.xlabel('Epochs')
plt.ylabel('Acc/Loss')
plt.legend()
plt.show()

# Defining evaluation matrix
def evaluation(y, y_hat, title = 'Confusion Matrix'):
    cm = confusion_matrix(y, y_hat)
    precision = precision_score(y, y_hat)
    recall = recall_score(y, y_hat)
    accuracy = accuracy_score(y,y_hat)
    f1 = f1_score(y,y_hat)
    print('Recall: ', recall)
    print('Accuracy: ', accuracy)
    print('Precision: ', precision)
    print('F1: ', f1)
    sns.heatmap(cm,  cmap= 'PuBu', annot=True, fmt='g', annot_kws=    {'size':20})
    plt.xlabel('predicted', fontsize=18)
    plt.ylabel('actual', fontsize=18)
    plt.title(title, fontsize=18)
    
    plt.show();

# Predicting based on trained model
y_predicted = model.predict_classes(X_test)
evaluation(y_predicted, y_test)

# Testing
# hm = 34.5 # HAEMATOCRIT
# hb = 12.5 # HAEMOGLOBINS
# er = 4.17 # ERYTHROCYTE
# leu = 15.3 # LEUCOCYTE
# tro = 122 # THROMBOCYTE
# mch = 27.6 # MCH
# mchc = 33.3 # MCHC 
# mcv = 82.7 # MCV
# age = 66 # AGE
# sex = 1 # SEX

# hm = 35.1 # HAEMATOCRIT
# hb = 11.8 # HAEMOGLOBINS
# er = 4.65 # ERYTHROCYTE
# leu = 6.3 # LEUCOCYTE
# tro = 310 # THROMBOCYTE
# mch = 25.4 # MCH
# mchc = 33.6 # MCHC 
# mcv = 75.5 # MCV
# age = 1 # AGE
# sex = 0 # SEX

#pred_incare = [[34.5 , 12.5 , 4.17 , 15.3 , 122 , 27.6 , 33.3 , 82.7 , 66 , 1]]
pred_outcare = [[35.1 , 11.8 , 4.65 , 6.3 , 310 , 25.4 , 33.6 , 75.5 , 1 , 0]]
pred_incare = [[31.7 , 10.4 , 4.91 , 9.7 , 348 , 21.2 , 32.8 , 64.6 , 21 , 1]]
rawat_inap = [[40.0 , 15.0 , 4.0 , 8.0 , 300 , 24.0 , 33.0 , 90.0 , 21 , 1]]
uploaded_model = tf.keras.models.load_model('/content/weights-improvement-29-0.74.hdf5')
prediction_DL = model.predict_classes(rawat_inap)

#score1 = model.score(X_test, y_test)
print(prediction_DL[0][0])
if prediction_DL[0][0] == 1:
    pred = "out care patient"
else:
    pred = "in care patient"
print('Prediksi :',pred)
#print("Test score: {0:.2f} %".format(100 * score1))