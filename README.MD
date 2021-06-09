<h1 align="center">
Bangkit Team B21-CAP0279
</h1>
<p align="center">
Capstone for Bangkit 2021
</p>
<p align="center">
<img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-4.3.1-blue?logo=kotlin">
<img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-4.1.2-green?logo=android-studio">
<img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white">
<img alt="TensorFlow" src="https://badges.aleen42.com/src/tensorflow.svg">
<img alt="Firebase" src="https://img.shields.io/badge/firebase-%23039BE5.svg?logo=firebase"/>
</p>

# Member
1. Weslie Leonardo (A1221567) - Politeknik Caltex Riau
2. Winli (A1221572) - Politeknik Caltex Riau
3. Hafizh Rahmatdianto Yusuf (M0020064) - Institut Teknologi Bandung
4. Athaya Syaqra (M0020064) - Institut Teknologi Bandung
5. Gladys Shafira Amru (C0121297) - Universitas Telkom
6. Alfadin Hauqala Zaelani (C0121296 ) - Universitas Telkom

## Theme : Healthcare
### Title of Project : Emergency Personal Medical Record App

### Summary of Project
Based on Rencana Strategis (Renstra) Kementerian Kesehatan 2020-2024, one of the priority steps needed to improve the health information system is to integrate health data using digital innovation and the internet. With that in mind, we tried to solve this issue by integrating health data between healthcare stakeholders. We believe integration of health data will lead to better healthcare services for patients and better organization for healthcare providers. In order to do so, our team needs to define health data that needs to be integrated, which stakeholders need to be involved in, find the best way to integrate health data, define the scope of the integration, and determine the use of machine learning, cloud computing, and android development into the solution.

## Steps to replicate this project
### Machine Learning
1. Dataset ingestion (from Kaggle)
2. Feature exploration
3. Preprocessing (binary encoding, dividing data, check numbers of data, and scaling the data to prepare for the ML training)
4. Define deep learning model using TensorFlow (use 2 dense layers)
5. Hyperparameter tuning with the help of GridSearchCV from scikit-learn library and train the model
6. Save and load model to evaluate model performance

### Mobile Development
1. Design UI layout (optional: Figma)
2. Dependencies (see Technology used part)
3. Navigation
4. Connecting local database to UI (using ViewModel, Room, optional: Flow, Koin, Clean Architecture)
5. Implement external feature (accessing camera and gallery, using QR code and scanner)
6. Connecting to remote (using Firestore for database and Firebase storage for file)
7. Implement machine learning using TFLite

### Cloud Computing
1. Create a project on Google Cloud Platform
2. Set default region as asia-southeast2(Jakarta)
    > go to gcp console and write this command : $gcloud config set compute/region asia-southeast2
3. Create a project on Firebase
4. Create storage with records and profile folders
    - Cloud Storage Browser page
    - Create bucket
    - Name your bucket : "-----"
    - Location type : region
    - Choose where to store your data = asia-southeast2
    - Leave the default setting
    - Create
5. Create a firestore for the database with collection note, patient, record, staff
6. Input machine learning model in Firebase

## Technology used

- [Koin](https://github.com/InsertKoinIO/koin)
- [Coroutine Flow](https://developer.android.com/kotlin/flow)
- [Firebase](https://firebase.google.com)
- [TensorFlow](https://www.tensorflow.org/lite/guide/android)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Glide](https://github.com/bumptech/glide)
- [Lottie](https://github.com/airbnb/lottie-android)
- [QrGenerator](https://github.com/androidmads/QRGenerator)
- [Code Scanner](https://github.com/yuriy-budiyev/code-scanner)
- [Data2viz](https://github.com/data2viz/data2viz)
- [Google Cloud Platform](https://cloud.google.com/gcp)

## Project Resources
### Budget
Google Cloud Platform Subscription : **$200**

### Dataset:
- [Patient Treatment Classification](https://www.kaggle.com/saurabhshahane/patient-treatment-classification)

### Paper / Journals / articles:
- [Development of a Mobile Personal Health Record Application Designed for Emergency Care in Korea; Integrated Information from Multicenter Electronic Medical Records](https://www.mdpi.com/2076-3417/10/19/6711/pdf)

### Design Apps :
[Design](https://www.figma.com/file/qm2lQP86pCD1rpYIgKxif3/Emergency-Personal-Medical-Record-App?node-id=0%3A1&viewport=513%2C346%2C0.35253381729125977)
