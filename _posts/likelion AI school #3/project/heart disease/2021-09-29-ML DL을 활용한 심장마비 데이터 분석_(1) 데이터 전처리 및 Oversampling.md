---
title: "ML DL을 활용한 심장마비 데이터 분석_(1) 데이터 전처리 및 Oversampling(SMOTE)"
categories:
  - python
  - likelion
  - project
  - Data analysis
  - ML
  - DL
tags:
  - python
  - likelion
  - Data analysis
  - ML
  - DL
---
# 작성중

## Project Process 별 자료 (총 3편)


## 데이터 파악
- 데이터 column 별 의미
  * age : 연령
  * sex : 성별 (1-남성 / 0-여성)
  * cp : 가슴 통증 유형 (1-협심증, 2-비정형협심증, 3-비관절 통증, 4-무증상)
  * trestbps : 안정시 혈압
  * chol - 혈청 콜레스테롤 (mg/dl)
  * fbs - 공복 혈당 120 mg/dl 이상 (1-true / 0-false)
  * restecg - 안정시 심전도 결과 (0-2)
  * thalach - 달성 된 최대 심박수 
  * exang - 운동으로 인한 협심증 (1-yes / 0-no)
  * oldpeak - 휴식에 비해 운동에 의해 유발 된 ST 우울증
  * slp - 최고 운동 ST 세그먼트의 기울기 크기(1,2,3)
  * ca - 형광 투시로 채색 된 주요 혈관 (0-3)
  * thal - 달성 된 최대 심박수  
   ![image](https://user-images.githubusercontent.com/88296152/135204352-08e98cbc-e96d-4ea3-b07b-2b6a43247e4a.png)

- 결측치 및 Outlier 확인
  * 결측치는 없었음
  * Chol 항목에서 일부 Outlier가 보였음 → 500 넘는 행은 제거함
  ```python
  import pandas as pd
  import seaborn as sns
  
  chol = df.loc[:,['chol']]
  sns.boxplot(x = 'variable',y = 'value',data = chol.melt())
  
  df.drop(df[(df['chol'] > 500)].index,inplace=True)
  ```
  ![image](https://user-images.githubusercontent.com/88296152/135206554-3c50a3b1-07d6-427f-b1fc-1e39b7bf8e11.png)

## 데이터 Oversampling
- 데이터 수합된 데이터 양이 너무 적어 추가 Data 필요  
- 뿐만 아니라 Google AutoML을 돌리기 위해서는 최소 1,000개 이상의 Data 필요
- SMOTE 방식으로 Data Oversampling 진행
- 원본 데이터 중 30%는 Test Data 추출 후 잔여 Data로 Oversampling 진행  
  (SMOTE Data 1,808개 + Test Data 91개 = 총 Data 1,899개)
  ```python
  from sklearn.datasets import make_classification
  from sklearn.decomposition import PCA
  from imblearn.over_sampling import SMOTE

  # 모델설정
  sm = SMOTE(ratio='auto', kind='regular')

  for i in range(3):
    # train데이터를 넣어 3회 복제함
    X_resampled, y_resampled = sm.fit_sample(train_data,train_label)
    print('After OverSampling, the shape of train_X: {}'.format(X_resampled.shape))
    print('After OverSampling, the shape of train_y: {} '.format(y_resampled.shape))

    print("After OverSampling, counts of label '1': {}".format(sum(y_resampled==1)))
    print("After OverSampling, counts of label '0': {}\n".format(sum(y_resampled==0)))
    X_resampled = pd.DataFrame(X_resampled)
    y_resampled = pd.DataFrame(y_resampled)

    #복제된 샘플과 원본 데이터 셋 concat
    train_data = np.concatenate((train_data, X_resampled), axis=0)
    train_label = np.concatenate((train_label, y_resampled), axis=0)

  #Result 
  print("결과 샘플 수",train_data.shape)
  print("결과 샘플 수",train_label.shape)
  ```
  ![image](https://user-images.githubusercontent.com/88296152/137832605-40769c20-5281-4278-8916-dd66080518d2.png)
