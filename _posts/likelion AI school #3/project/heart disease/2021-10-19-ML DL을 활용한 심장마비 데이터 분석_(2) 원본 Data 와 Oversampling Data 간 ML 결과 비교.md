---
title: "ML DL을 활용한 심장마비 데이터 분석_(2) 원본 Data 와 Oversampling Data 간 ML 결과 비교"
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


## 분석 내용
- DL 분석 전 ML 분석 시 정확도 확인
- Oversampling 자료와 원본 자료 간의 정확도 차이 확인

## ML 분석
- Train Data 와 Validation Data 비율을 7:3으로 나누어 Train 시킴
- Accracy 가 높은 상위 15개 모델 추려서 확인
- 좌측은 Oversampling data, 우측은 원본 Data를 이용하여 Modeling 한 결과 내용임
- 표의 경우, Train 후 Validation Data로만 분석한 결과로 Oversampling한 결과가 월등히 높게 나오는 것이 확인
- 상위 5개 모델에 대해 Test Data를 넣고 Accuracy를 재측정한 결과(표 밑의 결과), 데이터 간 유사하게 나오는 것으로 확인
- Validation Data로 검증한 Accuracy 값이 높게 나온 모델이 Test Data로 재검증한 결과와 동일하게 나오지 않음  
  (좌측 : Oversampling Data / 우측 : 원본 Data)
  ![image](https://user-images.githubusercontent.com/88296152/137853406-606982bd-479a-494c-9a47-065f8a304361.png)
- 각 Data 별 상위 3개 모델을 추려서 앙상블 진행 : ROC 결과 값이 0.97 ~ 0.98 정도로 데이터 구별없이 유사하게 도출됨
  ![image](https://user-images.githubusercontent.com/88296152/137858925-716226c4-7041-4127-9d3a-84158a96dded.png)

## 코드 특이사항
- Pycaret 내 compare_models 함수 사용
  * 강의 내에서는 모델 별로 학습을 시키는 것을 배웠으나, 웹 검색 결과 위와 같은 기능이 있어 처음 사용함
  * n 뒤에 숫자를 지정함으로써 Accuracy가 높은 상위 모델 개수만큼 출력함
- compare_models 사용 후 test data로 Accuracy 측정 진행 후 시각화
  ```python
  from pycaret.classification import *
  
  model = setup(data=df_oversampling, 
              target='output', 
              train_size=0.7,
              session_id=123,
              )
  
  top = compare_models(n_select = 15)
  
  # Test data가 91개인 관계로 range 내 숫자를 91로 설정
  def Accuracy_check(model_number):
    count = 0
    predictions = predict_model(top[model_number], data = test_dataset)
    for i in range(91):
      if predictions['output'][i] == predictions['Label'][i]:
        count = count + 1
    Acc = round(int(count)/90*100,1)
    return Acc
  ```
- 모델 앙상블
  * 제일 결과가 좋은 모델을 meta model로 선정 후 모델 리스트 내에 있는 모델을 선정하여 정확도를 높힘
  * 자동 교차 검정 기능을 포함
  * fold 수는 10으로 기본 설정
  ``` python
  # 위의 코드 이어서 진행
  stack_model = setup(data = df_oversampling, target = 'output')
  
  stacker = stack_models(estimator_list = top[1:3], meta_model = top[0])
  
  # 성능 지표 확인
  # 설정된 Hyper-params의 값, AUC/Confusion matrix 등 성능 지표, Feature importance 등을 바로 확인할 수 있음
  evaluate_model(top[0]) 
  
  # 표 시각화
  model_ROC = top[0]
  plot_model(model_ROC, plot = 'auc')
  
## 결론
- 실행할 떄마다 모델의 순서가 바뀌는 경우가 발생하나 대부분 일치함
- Oversampling data와 원본 data로 학습한 모델의 Accuracy가 크게 차이 나지 않음
- 앙상블 결과 데이터 차이 상관없이 Accuracy가 유사한 것으로 확인됨
