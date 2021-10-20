---
title: "ML DL을 활용한 심장마비 데이터 분석_(3) DL분석 및 Sequential 함수와 Google AutoML 결과 비교"
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
  - DL
---
# 작성중

## Project Process 별 자료 (총 3편)


## 분석 내용
- 심장마비 데이터를 이용하여 DL 분석 진행
- check point 기능 사용 전/후 결과 비교
- 원본 Data와 Oversampling Data 결과비교
- 원본 Data와 Oversampling Data로 측정한 Google AutoML 결과값 비교

## DL 분석 과정
- Keras 내 Sequantial 함수 사용
- 초기 모델 설정
 * Initializer 함수로 he 함수 사용
 * relu 함수 사용
 * L2 정규화 함수 사용, 학습률 0.001
 * hidden layer 1~3 층, perceptron 8 ~ 256 사이로 설정 후 최적값이 나오도록 설정
 * Optimizer로 Adam 함수 사용
- 초기모델 설정 후 베이지안 최적화를 Tuner로 사용하여 최적 파라미터 탐색
 * batch size : 100 / epoch : 20으로 설정 (설정 변경에 따라 소요 시간 및 결과값 변동)
- Check point를 사용하여 Tuner로 검색하는 도중 Val accuracy가 가장 낮은 값 저장
- Oversampling Data로 측정한 Google Auto ML 과 원본 Data로 측정한 Sequantial 함수 결과 비교

## Google AutoML
- Google AutoML에 데이터 업로드 후 하기와 같은 옵션을 선택해야 함  
  (소요 시간 및 데이터 구분 등등)
  ![image](https://user-images.githubusercontent.com/88296152/138014767-4b011349-c5c8-48dd-b59a-bc93c4fb203a.png)
- 데이터 내 Validate / Test / Train Data가 있다면 열 하나에 별도 표기 후 옵션에서 해당 열 명 선택하여 분석 진행
  (반드시 대분자로 입력해야 함. 소문자로 입력 시 인식 못함)
  ![image](https://user-images.githubusercontent.com/88296152/138016017-cd2ec0a2-ec53-4679-9620-cf2ca19ec797.png)

## 결과
- Check Point 사용 전 후 결과 값 비교
  * 최종 Accuracy 및 Cross-entropy 결과 값 차이가 있음
  * Accuracy가 0.03 상승, Cross-entropy 가 약 1.7 정도 감소를 보여 유의미한 결과로 판단됨
  ![image](https://user-images.githubusercontent.com/88296152/138017033-c95d6c50-d86a-4b18-9a98-0350a6f32f02.png)

- Sequential 함수 결과값 비교 (원본 Data VS Oversampling Data)
  * ML 결과와 달리, 원본 데이터의 Accuracy가 약 0.03 높게, Cross-entropy 가 0.8 정도로 낮게 나옴
  ![image](https://user-images.githubusercontent.com/88296152/138017275-7a13e6ee-41dc-41d1-8a41-02f5bb0108a6.png)
  
- Google AutoML 결과와 원본 Data로 분석한 Sequantial 함수 결과 비교
  * Google AutoML의 경우 최소 Data 가 1,000개 이상인 관계로 부득이하게 Oversampling을 진행하여 분석 진행
  * Accuracy가 둘 다 81.3% 로 큰 차이를 보이지 않았음
  ![image](https://user-images.githubusercontent.com/88296152/138017975-d073a719-9906-4e9b-bb13-b272dd752e73.png)

## 주의사항
- Keras Tuner 및 DL 모델 학습 진행 시, 반드시 Check Point를 사용하여 진행할 것  
  (학습 결과 값이 중간 값에 비해 모델 성능이 떨어지는 결과가 발생함)
- Batch size 및 Epoch 설정에 따라 결과 값 및 소요시간이 달라짐. 최적값 찾는 것이 중요  
  (최적 값 찾는 방법은 추가적으로 공부 필요)
- 학습의 경우 Colab에서 진행을 하면 좋으나 중간에 멎는 경우가 종종 생김. Google AutoML 사용 가능시 사용  
  (Data 수량 및 Batch size 및 Epoch 수, hidden layer과 perceptron 수 등 복합적인 변수가 영향을 미침)

## 결론
- Data 수가 적은 경우 최대한 추가 Data를 확보하는 방향으로 선택하는 것이 좋음
- 다만, 방법이 없을 경우 SMOTE도 한 가지 방법이 될 수 있음  
  (Oversampling Data를 사용한 Google AutoML 분석 결과와 원본 Data를 사용한 Sequantial 함수 분석 결과의 정확도가 유사함)
- ML과 DL 분석 결과를 비교하였을 때 정확도는 ML이 높았으나, DL 성능도 못지 않게 좋다고 판단됨

## 코드 특이사항
- Sequential 함수 설계 및 Tuner 설정
  ```python
  import kerastuner as kt
  
  ## Sequential 함수 설계
  
  def build_hyper_model(hp):
    
    model = keras.Sequential()
    model.add(layers.Dense(input_dim=13, units=16, activation='relu', kernel_initializer=initializers.he_normal(), kernel_regularizer=regularizers.l2(0.001))) 
    
    # hidden layer 조정 (1~3 중 최적값 선택)
    
    for i in range(hp.Int('num_layers', min_value=1, max_value=3)): 
        
        # perceptrons 수 조정 (32~512 사이에서 최적값 선택)
        
        hp_units = hp.Int('units_' + str(i), min_value=8, max_value=256, step=32)
        hp_activations = hp.Choice('activation_' + str(i), values=['relu', 'elu'])
        
        model.add(layers.Dense(units=hp_units, activation=hp_activations, 
                               kernel_initializer=initializers.he_normal()))

    model.add(layers.Dense(2, activation='softmax'))
    
    # optimizer의 학습률 조정(0.01, 0.001 또는 0.0001에서 최적 값 선택)
    
    hp_learning_rate = hp.Choice('learning_rate', values = [1e-2, 1e-3, 1e-4]) 
    
    model.compile(optimizer = keras.optimizers.Adam(learning_rate = hp_learning_rate),
                loss = keras.losses.CategoricalCrossentropy(),
                metrics = ['accuracy']) # tf.keras.metrics.CategoricalAccuracy()
    
    return model
  
  ## Tuner 선택 (BayesianOptimization, RandomSearch, Hyperband 3가지 중 하나 선택)
  ## 위 Data의 경우, BayesianOptimization 결과 값이 가장 잘 나왔음
  
  tuner = kt.BayesianOptimization(build_hyper_model,
                                objective = 'val_accuracy', # Hyper-params tuning을 위한 목적함수 설정 (metric to minimize or maximize)
                                max_trials = 10, # 서로 다른 Hyper-params 조합으로 시도할 총 Trial 횟수 설정
                                directory = 'test_prac_dir', # Path to the working directory
                                project_name = 'heart_hyper_1') # Name to use as directory name for files saved by this Tuner
    ##==================================================================================
    # from kerastuner.tuners import RandomSearch
    # tuner = RandomSearch(
    #     build_model, # HyperModel
    #     objective='val_accuracy', #  최적화할 하이퍼모델
    #     max_trials=10,
    #     executions_per_trial=3, # 각 모델별 학습 회수
    #     directory='/content/sample_data', # 사용된 parameter 저장할 폴더
    #     project_name='RandomSearch_tune_res') # 사용된 parameter 저장할 폴더

    # #==================================================================================
    # from kerastuner.tuners import Hyperband
    # tuner = kt.Hyperband(
    #         build_model, # HyperModel
    #         objective ='val_accuracy', #  최적화할 하이퍼모델
    #         max_epochs =20, # 각 모델별 학습 회수
    #         factor = 3,    # 한 번에 훈련할 모델 수 결정 변수
    #         directory ='/content/sample_data', # 사용된 parameter 저장할 폴더
    #         project_name ='Hyperband_tune_res') # 사용된 parameter 저장할 폴더

    tuner.search_space_summary()
    
    tuner.search(train_data_scaled, 
             train_label, 
             batch_size=100, 
             epochs=20, 
            validation_split=0.2)
            
    tuner.results_summary(num_trials=3)
    
    ## Accuracy 높은 상위 3개 모델 확인 후 저장
    
    top3_models = tuner.get_best_hyperparameters(num_trials=3)

    for idx, model in enumerate(top3_models):
        print('Model performance rank :', idx)
        print(model.values)
        print()

    # Check the best trial's hyper-params

    best_hps = top3_models[0]

    print("""
    The hyperparameter search is complete. 
    * Optimal # of layers : {}
    * Optimal value of the learning-rate : {}""".format(best_hps.get('num_layers'), best_hps.get('learning_rate')))

    for layer_num in range(best_hps.get('num_layers')):
        print('Layer {} - # of Perceptrons :'.format(layer_num), best_hps.get('units_' + str(layer_num)))
        print('Layer {} - Applied activation function :'.format(layer_num), best_hps.get('activation_' + str(layer_num)))
    ```

- Check Point 설정
  * Callback 함수를 설정하여 Tuner search 진행 시, search 함수 내 추가  
    (참고: https://www.tensorflow.org/tutorials/keras/keras_tuner?hl=ko)  

  ```python
  
  # 모델 체크포인트 파일(중간 저장 모델)을 저장할 경로 설정 
  checkpoint_path = '/content/saved_model/my_model.h5'
  #saved_model/my_model
  #/content/saved_model/my_model.h5

  # "ModelCheckpoint" 콜백함수 객체 생성
  callback_checkpoint = tf.keras.callbacks.ModelCheckpoint(filepath=checkpoint_path, 
                                                           monitor='val_accuracy', 
                                                           save_best_only=True, 
                                                           verbose=0)
  
  # fit 함수 내 callback 함수 적용 방법
  
  model = tuner.hypermodel.build(best_hps)
  model.fit(train_data_scaled, 
            train_label, 
            batch_size = 100, 
            epochs = 20, 
            validation_split=0.2,
            verbose = 1,
            callbacks=[ callback_checkpoint ]
            )
  
  ## 저장한 Hyper Parameter 호출 및 평가 방법
    # Validation loss를 기준으로 한 (저장된) 모델 성능
    # saved_model/my_model
    # /content/saved_model/my_model.h5
    
    from keras.models import load_model
    model = load_model('/content/saved_model/my_model.h5')
    result = model.evaluate(test_data_scaled, test_label)
    print('loss (cross-entropy) :', result[0])
    print('test accuracy :', result[1])
  
  ```
