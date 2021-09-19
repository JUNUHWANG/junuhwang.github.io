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

## 데이터 확인 및 항목 추가
- 결측치 존재 여부 확인
    * **age**, **cabin**, **embarked** 3가지 항목에서 결측치 확인
    * **age** 열의 경우, 결측치가 전체의 20% 이므로 잔여 데이터로 추정 가능할 것으로 판단
    * **cabin** 열의 경우, 결측치가 전체이 77%를 차지, 잔여 데이터로 추정이 불가능할 것으로 판단 -> 제거 진행
    * **embarked** 열의 경우, 2개의 data만 결측된 것으로 확인 -> 상관 계수 확인 후 결정 예정
    ![image](https://user-images.githubusercontent.com/88296152/133918461-05de9674-abd0-4e96-99ea-163879760b2d.png)

- 객실 등급에 따른 티켓 가격 차이
    * **pclass**와 **fare**를 비교한 결과 동일 객실 등급에도 가격 차이가 많이 발생한 것으로 확인
    * **pclass**와 **ticket**, **fare** 를 Groupby로 묶어서 비교한 결과 동일 티켓 번호 확인
    * **ticket counts** 열을 추가하여 동일한 **ticket** 항목의 수 집계
    * **fare**을 **ticket counts**로 나누어 정확한 티켓 가격 집계  
      (**Fare per ticket** 열 추가)
    ![image](https://user-images.githubusercontent.com/88296152/133918723-39af2669-a7ba-4d4e-9ef7-4b37ff64cdc0.png)
 
- 단체 항목 추가
    * 가족 / 지인과 함께 온 경우 그룹으로 확인하여 추가
    * 지인의 경우, **ticket**가 동일한 사람들을 지인으로 추정함 
