---
title: "타이타닉 생존 data를 이용한 Model stacking (1)_데이터 전처리편"
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
## Project Process 별 자료 (총 3편)

<a href="https://junuhwang.github.io/python/likelion/project/data%20analysis/modeling/타이타닉-생존-data를-이용한-Model-stacking-_결과-요약/"> 타이타닉 생존 data를 이용한 Model stacking _결과 요약</a>  

<a href="https://junuhwang.github.io/python/likelion/project/data%20analysis/modeling/타이타닉-생존-data를-이용한-Model-stacking-(1)_데이터-전처리편/"> 타이타닉 생존 data를 이용한 Model stacking (1)_데이터 전처리편 </a>  

<a href="https://junuhwang.github.io/python/likelion/project/data%20analysis/modeling/타이타닉-생존-data를-이용한-Model-stacking-(2)_모델링-편/"> 타이타닉 생존 data를 이용한 Model stacking (2)_데이터 모델링편 </a>  

## 데이터 분석 목적
- 승객의 생존 여부 외 추가 데이터를 이용하여 어떤 변수가 생존에 영향을 미치는지 알아보기 위함
- 멋쟁이 사자처럼 강의 내 팀 프로젝트 진행 내용

## 데이터 파악
- 데이터 column 별 의미
    - **PassengerId** : 승객 별 고유 ID
    - **Survived** : 생존 여부(0 = 사먕, 1 = 생존) -> Y 값을 사용 예정
    - **pclass** : 객실 등급 (1 = 1st, 2 = 2nd, 3 = 3rd)
    - **sibsp** : 함께 탑승한 본인 제외 형제자매배우자 수
    - **name** : 승객 이름
    - **sex** : 성별
    - **age** : 나이
    - **parch** : 함께 탑승한 본인 제외 부모 및 자녀의 수
    - **ticket** : 티켓 구매 번호
    - **fare** : 티켓 가격
    - **cabin** : 승무원 여부
    - **embarked** : 승선한 항구 이름 (C = Cherbourg, Q = Queenstown, S = Southampton)

## 데이터 확인 및 분류 항목 추가
- 객실 등급에 따른 티켓 가격 차이에 따른 정확한 티켓 가격 산정 후 추가
    * **pclass**와 **fare**를 비교한 결과 동일 객실 등급에도 가격 차이가 많이 발생한 것으로 확인
    * **pclass**와 **ticket**, **fare** 를 Groupby로 묶어서 비교한 결과 동일 티켓 번호 확인
    * **ticket counts** 열을 추가하여 동일한 **ticket** 항목의 수 집계
    * **fare**을 **ticket counts**로 나누어 정확한 티켓 가격 집계  
      (**Fare per ticket** 열 추가)
    ![image](https://user-images.githubusercontent.com/88296152/133918723-39af2669-a7ba-4d4e-9ef7-4b37ff64cdc0.png)
 
- 단체 항목 추가
    * 지인 / 가족과 함께 온 경우 그룹으로 확인하여 추가
    * 지인의 경우, **ticket**에 있는 티켓의 번호가 동일한 사람들을 지인으로 추정함  
      (**ticket counts** 항목이 1 이상이면 지인으로 추정)
    * 가족의 경우, **sibsp**와 **parch**가 1 이상일 경우 그룹으로 산정함
    * **group** 열을 추가하고, **ticket counts**와 **sibsp**와 **parch** 합이 1 이상일 경우 그룹으로 온 것으로 판단  
      (혼자 왔을 경우, 위 항목들은 0으로 집계됨.)
    ![image](https://user-images.githubusercontent.com/88296152/133919591-3d108846-a872-4424-acdc-91e8ea1d5e47.png)

## 결측치 Data 추가
- 결측치 존재 여부 확인
    * **age**, **cabin**, **embarked** 3가지 항목에서 결측치 확인
    * **age** 열의 경우, 결측치가 전체의 20% 이므로 잔여 데이터로 추정 가능할 것으로 판단
    * **cabin** 열의 경우, 결측치가 전체이 77%를 차지, 잔여 데이터로 추정이 불가능할 것으로 판단 -> 제거 진행
    * **embarked** 열의 경우, 2개의 data만 결측된 것으로 확인 -> 상관 계수 확인 후 결정 예정
    ![image](https://user-images.githubusercontent.com/88296152/133918871-4b44806c-a2b0-4582-8ef1-e6543b15ce4c.png)

- **age** 결측치 채우기
    * 이름 사이에 있는 계급 호칭들이 나이와 연관이 있다고 판단함  
      (군 고위직 및 미혼 기혼 여부, 고령자 및 어린 아이에만 쓰는 호칭은 나이 추정에 영향이 있다고 판단)
    ![image](https://user-images.githubusercontent.com/88296152/133919929-e91b7a7c-cdef-4b97-827d-f01caa616306.png)
    
    * **name** 항목 내 계급 호칭만 별로 분리작업 진행
    ![image](https://user-images.githubusercontent.com/88296152/133920101-1085da3f-06cd-4522-893b-2b8fcea07ae8.png)
    ![image](https://user-images.githubusercontent.com/88296152/133920131-f72dc4e2-335f-45a8-8829-146c4cd9dbca.png)
    
    * 계급 호칭에 따라 군인 여부(**Military**) / 귀족 여부(**Nobility**) / 나이에 따른 호칭(**est_age**)에 대한 열을 추가하고 분류 작업 진행  
    ![image](https://user-images.githubusercontent.com/88296152/133920029-3e2f088a-6f94-43e9-b8bd-b86d9fd59fb0.png)
    
    * 위 Data를 이용하여 **age**열 결측치 추가를 위한 회귀분석 진행  
      (8개 모델 중 MSE가 가장 낮은 모델을 이용하여 결측치 추가)
    ![image](https://user-images.githubusercontent.com/88296152/133920643-b29cbe45-e5c6-4191-93fd-bf59e6b8ed04.png)

    * 분석 결과 선형회귀 모형이 MSE가 가장 낮게 나와 해당 모델을 이용하여 결측치 추가
    ![image](https://user-images.githubusercontent.com/88296152/133920748-fc19630c-edd1-4a2f-8ce7-f6707ee62c3f.png)


## 데이터 전치리 과정 중 느낀 점
- 팀 프로젝트 기간 중 주말 제외한 3일 중 2일을 데이터 전처리에 사용함
- 최종 모델 구현하기 전 데이터 전처리 작업이 중요하고 많은 시간이 소요되는 것을 깨달음
- 시간이 부족하여 팀 내 논의했던 다양한 방법을 시도하지 못하여 아쉬움이 남았음

## 데이터 전처리 진행 시 제안 사항
- **Age** 결측값 추정 시 여러 방법에 대해 논의를 진행함
- 하기 **Age** 관련 상관계수를 확인해보면 계급 호칭에 따른 나이 분류가 영향이 큰 것을 확인할 수 있음(약 0.45)
  ![image](https://user-images.githubusercontent.com/88296152/133920349-29a233e7-6c7b-4b92-94f1-e6213ad03c83.png)
- 결측값 항목의 Data를 확인해보면 일부 계급 호칭만 포함되어 있음
- 해당 계급 호칭이 들어간 Data만 이용하여 나이를 추정하면 정확도를 높힐 수 있을 것으로 판단함
