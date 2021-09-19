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
