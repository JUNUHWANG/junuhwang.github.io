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
---
# 내용 작성중

## Project Process 별 자료 (총 3편)

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
    
