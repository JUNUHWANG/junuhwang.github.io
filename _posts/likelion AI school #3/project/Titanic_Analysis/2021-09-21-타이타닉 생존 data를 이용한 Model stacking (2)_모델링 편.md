---
title: "타이타닉 생존 data를 이용한 Model stacking (2)_데이터 모델링편"
categories:
  - python
  - likelion
  - project
  - Data analysis
  - Modeling
tags:
  - python
  - likelion
  - ML
  - Data analysis
  - Modeling
  - Titanic data
---
# 내용 작성중

## Project Process 별 자료 (총 3편)

## 데이터 모델링 전 사전 작업
- Y값 설정 : 생존여부를 Y값으로 설정하고 나머지는 X값으로 이용할 예정
- Categorical / Numerical 항목 분류하여 일부 모델에서는 StandardScaler 적용 후 모델 fit
- 사용할 모델을 선정하고, 해당 모델의 특징 및 적용에 필요한 데이터 종류 확인 필요

## 사용할 모델의 특징
- Logistic Regression
  * Y값(종속변수)가 이항형일 때 사용
  * Y값이 발생할 확률에 대해 추정하고 결정하는 모델
  * 데이터의 정규분포도에 따라 결과 값에 영향을 받으므로, Standardscaler을 사용하여 fit 진행
  * C(Cost-function) 값을 Hyperparameter로 받음으로써 모델의 적합 여부에 영향을 미침
  * Cost-fuction은, Sigmoid function 에서 나오는 Cross-entropy function으로,  
    예측값의 분포와 실제값의 분포를 비교하여 그 차이로 성능 지표로 활용
    
- KNN
  * k값을 hyper-parameters로 받음
  * 가장 가까운 k개의 데이터를 살펴, 가장 많이 속한 클러스터로 분류해주는 알고리즘
  * k 값은 홀수로 설정
  * 짝수로 설정 시 양측 동점이 발생하여 판별을 하지 못하는 경우가 발생함
 
- Grandient Boosting
  * 여러 개의 'weaker learner'를 묶어 강력한 model을 만드는 ensemble기법
  * 학습을 수행하되, 앞에서 학습한 분류기가 예측이 틀린 데이터에 대해서 올바르게 예측할 수 있도록 다음 분류기에게 가중치(weight)를 부여하면서 학습과 예측 진행한 모델
  * MSE 혹은 Cross-entropy 값(Y)과 Parameter theta(X)와 비교하여 Y가 최소가 될 때의 X를 찾음(미분 사용)
  * Training Data만 사용할 경우 Overfitting 이 발생할 가능성이 높아 기존 data에서 split 진행하여 일부는 test data로 사용

- Light BGM
  * XGBoost와 유사하나 시간 단축 및 사용 메모리 감소을 위하여 개발된 모델
  * XGBoost의 경우, 정확하나 다량의 Hyper-parameter가 존재하며
    GridSearch 및 tuning 진행 시 다량의 메모리 및 시간 소요
  * XGBoost의 경우, 모든 node를 계산하여 진행(level-wise tree growth)하나  
    해당 모델은 일부 node에 대해서 분석 진행(leaf--wise tree growth)

- Random Forest
  * 여러 개의 Decision Tree (n - estimater를)를 기반으로 Random Forest가 만들어짐
  * 개별적으로 학습 수행 후 최종적으로 모든 분류기가 voting을 통해 예측 결정
  * 사용 이유: Parameter의 많은 수정이 불필요하고 단순함
  * 트리개수를 뜻하는 n-estimater를 hyper-parameter로 사용함(보통 50개에서 1000개를 사용)

- SVM
  * 범주를 나누는 데에 최대의 Margin을 갖는 경계면을 찾는 알고리즘
  * 데이터가 선형적으로 분리가 되지 않을 경우, 비선형 Mapping을 이용하여 고차원 공간으로 변환하여 분석
  * Parameter은 C와 gamma 값을 가짐
  * C 는 경계면의 굴곡을 결정하며, 작을수록 완만해지고 Margin 값이 커짐
  * gamma 또한 경계면의 굴곡을 결정하며, 작을수록 완만해지고 reach 값이 커짐
  * 굴곡이 완만할수록 경계면 근처에 있는 데이터가 경계면에 영향을 미치는 영향력은 감소함
  * gamma 값이 클수록 Over-fitting이 발생할 가능성이 높아짐
  
## 적용
- 연속형(Numerical), 범주형 항목(Categorical) Scaling 진행
  * 하기와 같이 연속형 변수는 StandardScaler로, 범주형 변수는 Onthotencoder로 Scaling 진행
  ![image](https://user-images.githubusercontent.com/88296152/134264556-192a9ef5-2661-4387-99d2-9702d232794f.png)
  
- Logistic Regression 
  * Scaler을 적용한 데이터를 로지스틱 함수에 적용 후 Data fit 진행
  ![image](https://user-images.githubusercontent.com/88296152/134266365-148aaa5f-9f44-43d6-9f6e-dbc37ffd608e.png)
  * GridSearch 진행 결과 C의 값은 5, L2 정규화를 진행했을 때에 가장 좋은 모델이 나오는 것을 확인(AUC : 0.817)
  ![image](https://user-images.githubusercontent.com/88296152/134266469-817924bb-4b31-4e18-b9ff-17fcb04dc87f.png)
  * Hyperparameter 항목에 C=5를 넣고 다시 학습 시켜 최종 모델 생성
  ![image](https://user-images.githubusercontent.com/88296152/134266882-2df026df-7e3b-450a-8a6b-7cd587a1bc76.png)

- KNN
  * Logistic Regression 진행한 내용과 동일하게 진행
  ![image](https://user-images.githubusercontent.com/88296152/134267229-8d341eed-fd74-427c-a641-6db7cbaa2ba1.png)
  * GridSearch 진행 결과 K의 값은 7로 진행했을 때에 가장 좋은 모델이 나오는 것을 확인(AUC : 0.763)
  ![image](https://user-images.githubusercontent.com/88296152/134267345-36faf89e-523e-49a7-8d3f-331924e7a112.png)
  * Hyperparameter 항목에 K=7를 넣고 다시 학습 시켜 최종 모델 생성
  ![image](https://user-images.githubusercontent.com/88296152/134267436-3cef16ea-3a48-4283-b204-3eec9fa1ca7c.png)
  
- Grandient Boosting
  * 


